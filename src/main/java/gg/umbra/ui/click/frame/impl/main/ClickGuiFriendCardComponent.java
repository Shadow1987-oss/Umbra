package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.utils.RectData;
import gg.umbra.friend.FriendEntry;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.MouseButton;
import gg.umbra.ui.click.MousePosition;
import gg.umbra.ui.click.animation.ColorAnimation;
import gg.umbra.ui.click.component.GlyphIconComponent;
import gg.umbra.ui.click.component.gui.InteractiveComponent;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.utils.StringUtils;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.RenderUtils;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ClickGuiFriendCardComponent
extends InteractiveComponent {
    private static final Color MARKER_COLOR = new Color(62, 61, 62);
    private final FriendEntry friend;
    private final ColorAnimation hoverAnimation;
    private final ColorAnimation trashHoverAnimation;
    private final RectData trashHitbox = new RectData(0.0, 0.0, 0.0, 0.0);
    private final GlyphIconComponent trashIcon;
    private boolean selected;
    private boolean trashHovered;
    private String displayName;
    private String truncatedName;
    private double truncatedNameWidth;
    @Nullable
    private Runnable selectAction;
    @Nullable
    private Runnable removeAction;

    public FriendEntry getFriend() {
        return this.friend;
    }

    public void setSelectAction(@Nullable Runnable runnable) {
        this.selectAction = runnable;
    }

    public void setRemoveAction(@Nullable Runnable runnable) {
        this.removeAction = runnable;
    }

    public void setSelected(boolean bl) {
        this.selected = bl;
    }

    public boolean isSelected() {
        return this.selected;
    }

    private static String computeDisplayName(FriendEntry friendEntry) {
        String string = friendEntry.getDisplayName();
        if (string == null || string.trim().isEmpty()) {
            string = friendEntry.getName();
        }
        return string == null ? "" : string;
    }

    public ClickGuiFriendCardComponent(FriendEntry friendEntry) {
        this.friend = friendEntry;
        this.displayName = ClickGuiFriendCardComponent.computeDisplayName(friendEntry);
        this.hoverAnimation = new ColorAnimation(0.15, J.t, J.z);
        this.trashHoverAnimation = new ColorAnimation(0.15, J.t, J.E);
        this.truncatedName = "";
        this.setPropagateMouseEvents(true);
        this.trashIcon = new GlyphIconComponent("newtrash", 6.0, 6.0, 16.0, 20.0, J.W, J.f, null);
        this.Y(20.0);
        this.setShowDisabledOverlay(false);
        this.addChildren(this.trashIcon);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (guiMouseEvent.getAction() == MouseButton.LEFT_CLICK && !this.getClickCooldown().isCoolingDown()) {
            if (this.trashHovered && this.removeAction != null) {
                this.removeAction.run();
                this.getClickCooldown().setActive(true);
                return;
            }
            if (this.selectAction != null) {
                this.selectAction.run();
            }
            this.getClickCooldown().setActive(true);
            return;
        }
        super.g(guiMouseEvent);
    }

    private void updateTruncatedName(SmoothFontRenderer smoothFontRenderer, double d) {
        String string = StringUtils.l(this.displayName).trim();
        if (string.isEmpty() || d <= 0.0) {
            this.truncatedName = "";
            this.truncatedNameWidth = 0.0;
            return;
        }
        double d2 = smoothFontRenderer.N(string);
        if (d2 <= d) {
            this.truncatedName = string;
            this.truncatedNameWidth = d2;
            return;
        }
        String string2 = "...";
        double d3 = smoothFontRenderer.N(string2);
        if (d3 > d) {
            this.truncatedName = "";
            this.truncatedNameWidth = 0.0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string.charAt(i));
            double d4 = smoothFontRenderer.N(stringBuilder.toString()) + d3;
            if (!(d4 > d)) continue;
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            break;
        }
        this.truncatedName = stringBuilder.append(string2).toString();
        this.truncatedNameWidth = smoothFontRenderer.N(this.truncatedName);
    }

    @Override
    public void H() {
        double d = this.G$src$D$1b2f02a();
        double d2 = this.n();
        double d3 = this.A();
        double d4 = this.L();
        double d5 = d2 + d4 / 2.0;
        MousePosition mousePosition = RenderUtils.h();
        Color color = J.m;
        if (this.selected) {
            color = J.H;
        } else if (this.w$src$Z$e457mb()) {
            color = J.R;
        }
        GuiRenderPrimitives.B(d, d2, d3, d4, color, 3.0f);
        Color color2 = this.hoverAnimation.getInterpolatedColor();
        if (color2.getAlpha() > 0) {
            GuiRenderPrimitives.B(d, d2, d3, d4, color2, 3.0f);
        }
        double d6 = d + 8.0;
        double d7 = d5 - 3.0 - 0.5;
        GuiRenderPrimitives.m((float)d6, (float)d7, 6.0f, 1.0f, 0.5f, this.selected ? J.f : MARKER_COLOR);
        double d8 = d + d3 - 6.0 - 6.0;
        double d9 = d5 - 3.0;
        this.trashHitbox.M(d8 - 6.0);
        this.trashHitbox.O(d9 - 8.0);
        this.trashHitbox.A(16.0);
        this.trashHitbox.U(22.0);
        this.trashHovered = this.trashHitbox.Z(mousePosition);
        this.hoverAnimation.u(this.w$src$Z$e457mb() && !this.selected);
        this.trashHoverAnimation.u(this.trashHovered);
        Color color3 = this.trashHoverAnimation.getInterpolatedColor();
        if (color3.getAlpha() > 0) {
            GuiRenderPrimitives.p(this.trashHitbox.o(), this.trashHitbox.W(), this.trashHitbox.e(), this.trashHitbox.R(), color3, false, 2.0f, 1.0f, 0.0f, J.u, 6);
        }
        Color color4 = J.W;
        if (this.selected || this.trashHovered) {
            color4 = J.f;
        }
        this.trashIcon.setNormalColor(color4);
        this.trashIcon.K(d8);
        this.trashIcon.S(d9);
        this.trashIcon.c();
        double d10 = d6 + 6.0 + 6.0;
        double d11 = d8 - 8.0;
        double d12 = Math.max(0.0, d11 - d10);
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.75);
        this.updateTruncatedName(smoothFontRenderer, d12);
        double d13 = d5 - smoothFontRenderer.d("A") / 2.0;
        Color color5 = J.A;
        if (this.selected) {
            color5 = Color.WHITE;
        }
        smoothFontRenderer.d(this.truncatedName, d10, d13 - 0.5, color5);
    }
}
