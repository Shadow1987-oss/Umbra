package gg.umbra.event.listener;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPostRenderTick;
import gg.umbra.notification.NotificationType;
import org.lwjgl.opengl.GL11;

public class NotificationAlertEventListener
implements EventListener {
    private boolean errorReported = false;
    private static int obfuscationState;

    public static void setObfuscationState(int state) {
        obfuscationState = state;
    }

    public static int getObfuscationState() {
        return obfuscationState;
    }

    public static int getObfuscationConstant() {
        int state = NotificationAlertEventListener.getObfuscationState();
        return 0;
    }


    static {
        NotificationAlertEventListener.setObfuscationState(77);
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onPostRenderTick(EventPostRenderTick eventPostRenderTick) {
        int errorCode;
        if (!this.errorReported && (errorCode = GL11.glGetError()) != 0) {
            Umbra.INSTANCE.getNotificationManager().show("GL Error " + errorCode, "Please contact support and report this error code", NotificationType.ALERT, 10000L);
            this.errorReported = true;
        }
    }
}

