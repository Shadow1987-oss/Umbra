package gg.umbra.notification;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.notification.FriendNotificationSettings;
import gg.umbra.notification.INotification;
import gg.umbra.notification.Notification;
import gg.umbra.notification.NotificationGroup;
import gg.umbra.notification.NotificationSounds;
import gg.umbra.notification.NotificationType;
import gg.umbra.notification.SoundClip;
import gg.umbra.notification.TextNotificationContent;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.MouseButton;
import gg.umbra.utils.MathUtil;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.value.ToggleSetting;
import gg.umbra.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class NotificationManager
implements EventListener {
    private final Map<NotificationGroup, ToggleSetting> groupSettings;
    private final ArrayBlockingQueue<INotification> notifications = new ArrayBlockingQueue<INotification>(20);
    private long lastRenderTime;
    private static int controlFlowState;
    private final Map<NotificationType, SoundClip> notificationSounds = new HashMap<NotificationType, SoundClip>();


    private boolean isAllowedByFriendSettings(INotification notification) {
        return true;
    }

    public void show(String title, String message, NotificationType type, long durationMillis, boolean force) {
        this.enqueue(new Notification(type, title, new TextNotificationContent(message), 0.0, 0.0, durationMillis), force);
    }

    public ArrayBlockingQueue<INotification> getNotifications() {
        return this.notifications;
    }

    private boolean isGroupEnabled(NotificationGroup group) {
        if (!this.groupSettings.containsKey(group)) {
            return true;
        }
        return this.groupSettings.get(group).getEffectiveValue();
    }

    static {
        if (NotificationManager.initializeControlFlowState() == 0) {
            NotificationManager.setControlFlowState(82);
        }
    }

    public void renderNotifications() {
        int unusedControlFlowState = NotificationManager.getControlFlowState();
        if (Minecraft.theWorld().isNull() || Minecraft.thePlayer().isNull()) {
            return;
        }
        int previousAlphaTestFunction = OpenGlBackendHolder.backend.getIntegerState(3009);
        float previousAlphaTestReference = OpenGlBackendHolder.backend.getFloat(3010);
        OpenGlBackendHolder.backend.setAlphaFunction(516, 0.0f);
        OpenGlBackendHolder.backend.pushMatrix();
        /* Timebomb here (disabled): full-screen black overlay after 2027-03-14 (epoch ms 1805274154878L)
        if (System.currentTimeMillis() > 1805274154878L) {
            float playerYawMagnitude = (float)Math.abs(Minecraft.thePlayer().z());
            Color color = new Color(0, 0, 0, (int)Math.min(240.0f, playerYawMagnitude));
            GuiRenderPrimitives.y(0.0f, 0.0f, Minecraft.J(), Minecraft.h(), color);
        }
        */
        double nextTargetY = -14.0;
        long now = System.currentTimeMillis();
        long elapsedMillis = now - this.lastRenderTime;
        this.lastRenderTime = now;
        ArrayList<INotification> expiredNotifications = new ArrayList<INotification>();
        for (INotification notification : this.notifications) {
            nextTargetY -= notification.getHeight() + 2.0;
            notification.setTargetY(nextTargetY);
            int horizontalStep = (int)(Math.abs(notification.getTargetX() - notification.getCurrentX()) * 0.3);
            int verticalStep = (int)(Math.abs(notification.getTargetY() - notification.getCurrentY()) * 0.3);
            double currentX = NotificationManager.interpolateToward(
                    notification.getTargetX(), notification.getCurrentX(), elapsedMillis, horizontalStep);
            double currentY = NotificationManager.interpolateToward(
                    notification.getTargetY(), notification.getCurrentY(), elapsedMillis, verticalStep);
            notification.setCurrentX(currentX);
            notification.setCurrentY(currentY);
            notification.render();
            if (!notification.shouldRemove()) continue;
            expiredNotifications.add(notification);
        }
        for (INotification notification : expiredNotifications) {
            this.notifications.remove(notification);
        }
        OpenGlBackendHolder.backend.setAlphaFunction(previousAlphaTestFunction, previousAlphaTestReference);
        OpenGlBackendHolder.backend.popMatrix();
    }

    public static int initializeControlFlowState() {
        int currentState = NotificationManager.getControlFlowState();
        if (currentState == 0) {
            return 112;
        }
        return 0;
    }

    public void show(INotification notification) {
        this.enqueue(notification, false);
    }

    public void showInfo(String title, String message, long durationMillis) {
        this.show(title, message, NotificationType.INFO, durationMillis);
    }

    public static void setControlFlowState(int state) {
        controlFlowState = state;
    }

    public void show(String title, String message, NotificationType type, long durationMillis) {
        this.show(title, message, type, durationMillis, false);
    }

    @Listen
    public void onMouseClick(GuiMouseEvent event) {
        if (!event.getAction().equals(MouseButton.LEFT_CLICK)) {
            return;
        }
        for (INotification notification : this.notifications) {
            notification.handleClick(event.getX(), event.getY());
        }
    }

    public static double interpolateToward(double target, double current, long elapsedMillis, double maxStep) {
        if (target == current) {
            return target;
        }
        double scaledStep = Math.max(maxStep * (double)Math.max(1L, elapsedMillis) / 16.666666666666668, 0.1);
        return current + MathUtil.clamp(target - current, -scaledStep, scaledStep);
    }

    public static int getControlFlowState() {
        return controlFlowState;
    }

    public void enqueue(INotification notification, boolean force) {
        if (!this.isAllowedByFriendSettings(notification)) {
            return;
        }
        if (!Umbra.INSTANCE.getPublicProfileSettings().notifications.getEffectiveValue().booleanValue() && !force) {
            return;
        }
        if (!this.isGroupEnabled(notification.getType().getGroup())) {
            return;
        }
        if (this.notificationSounds.containsKey(notification.getType())) {
            Umbra.INSTANCE.getNotificationSoundPlayer().queue(this.notificationSounds.get(notification.getType()));
        }
        double initialY = notification.getHeight() + 16.0;
        for (INotification queuedNotification : this.notifications) {
            initialY += queuedNotification.getHeight() + 2.0;
        }
        notification.setCurrentY(-initialY);
        notification.setCurrentX(0.0);
        notification.setTargetX(-notification.getWidth());
        if (this.notifications.remainingCapacity() == 0) {
            this.notifications.remove();
        }
        if (!this.notifications.contains(notification)) {
            this.notifications.add(notification);
        }
    }

    public NotificationManager() {
        this.groupSettings = new HashMap<NotificationGroup, ToggleSetting>();
        this.notificationSounds.put(NotificationType.FRIENDS_NEW_CHAT, NotificationSounds.MESSAGE_RECEIVED);
        this.notificationSounds.put(NotificationType.FRIENDS_PARTY_INVITE, NotificationSounds.PARTY_INVITE);
        this.groupSettings.put(NotificationGroup.FRIENDS, Umbra.INSTANCE.getPublicProfileSettings().friendNotifications);
    }
}

