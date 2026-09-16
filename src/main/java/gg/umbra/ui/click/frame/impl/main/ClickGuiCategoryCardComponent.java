package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.Umbra;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.ui.click.animation.ColorAnimation;
import gg.umbra.ui.click.component.IconGlyphComponent;
import gg.umbra.ui.click.component.SimpleTextLabelComponent;
import gg.umbra.ui.click.component.gui.InteractiveComponent;
import gg.umbra.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class ClickGuiCategoryCardComponent
extends InteractiveComponent {
    private static final double CARD_HEIGHT = 44.0;
    private static final float CORNER_RADIUS = 5.0f;
    private static final double ICON_CHIP_SIZE = 24.0;
    private static final double ICON_SIZE = 11.0;
    private final Category category;
    private final ColorAnimation hoverAnimation;
    private final IconGlyphComponent leadingIcon;
    private final SimpleTextLabelComponent nameLabel;
    private final SimpleTextLabelComponent subtitleLabel;
    private final IconGlyphComponent chevronIcon;

    public ClickGuiCategoryCardComponent(Category category) {
        this.category = category;
        this.Y(CARD_HEIGHT);
        this.setShowDisabledOverlay(false);
        this.setPropagateMouseEvents(true);
        this.hoverAnimation = new ColorAnimation(0.15, ClickGuiCategoryCardComponent.J.t, ClickGuiCategoryCardComponent.J.E);
        Color catColor = new Color(category.getColor());
        this.leadingIcon = new IconGlyphComponent(category.getIconKey(), (float)ICON_SIZE, (float)ICON_SIZE, catColor);
        this.leadingIcon.setSnapToPixels(true);
        this.nameLabel = new SimpleTextLabelComponent(category.getName(), 0.8, ClickGuiCategoryCardComponent.J.A);
        this.nameLabel.setOffsetX(0.0f);
        this.nameLabel.setOffsetY(0.0f);
        this.nameLabel.setExtraHeight(0);
        this.subtitleLabel = new SimpleTextLabelComponent("", 0.6, ClickGuiCategoryCardComponent.J.Z);
        this.subtitleLabel.setOffsetX(0.0f);
        this.subtitleLabel.setOffsetY(0.0f);
        this.subtitleLabel.setExtraHeight(0);
        this.subtitleLabel.setTextSupplier(() -> ClickGuiCategoryCardComponent.buildSubtitle(this));
        this.chevronIcon = new IconGlyphComponent("expandarrow", 1.5f, 3.0f, ClickGuiCategoryCardComponent.J.W);
        this.chevronIcon.setSnapToPixels(true);
        this.addChildren(this.leadingIcon, this.nameLabel, this.subtitleLabel, this.chevronIcon);
        if (category.getDescription() != null && !category.getDescription().isEmpty()) {
            this.w(category.getDescription());
        }
    }

    private static String buildSubtitle(ClickGuiCategoryCardComponent card) {
        int total = 0;
        int on = 0;
        if (card.category == Category.FAVORITES) {
            for (HackModule mod : Umbra.INSTANCE.getModuleProfileMetadataCodec().getSelectedModules()) {
                ++total;
                if (mod.isEnabled()) {
                    ++on;
                }
            }
        } else {
            for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
                if (mod.getCategory() != card.category) continue;
                ++total;
                if (mod.isEnabled()) {
                    ++on;
                }
            }
        }
        return total + " modules \u00b7 " + on + " on";
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public void H() {
        boolean hovered = this.w$src$Z$e457mb();
        this.hoverAnimation.u(hovered);
        Color catColor = new Color(this.category.getColor());
        this.leadingIcon.setColor(catColor);
        double x = this.G$src$D$1b2f02a();
        double y = this.n();
        double w = this.A();
        double h = this.L();
        GuiRenderPrimitives.B(x, y, w, h, ClickGuiCategoryCardComponent.J.H, CORNER_RADIUS);
        Color hoverColor = this.hoverAnimation.getInterpolatedColor();
        if (hoverColor != null && hoverColor.getAlpha() > 0) {
            GuiRenderPrimitives.B(x, y, w, h, hoverColor, CORNER_RADIUS);
        }
        GuiRenderPrimitives.B(x + 1.0, y + 3.0, 2.0, h - 6.0, catColor, 1.0f);
        double chipX = x + 9.0;
        double chipY = y + (h - ICON_CHIP_SIZE) / 2.0;
        GuiRenderPrimitives.B(chipX, chipY, ICON_CHIP_SIZE, ICON_CHIP_SIZE, ClickGuiCategoryCardComponent.J.s, 5.0f);
        this.leadingIcon.K(chipX + (ICON_CHIP_SIZE - ICON_SIZE) / 2.0);
        this.leadingIcon.S(chipY + (ICON_CHIP_SIZE - ICON_SIZE) / 2.0);
        double chevronX = x + w - 4.0 - 1.5;
        this.chevronIcon.K(chevronX);
        this.chevronIcon.S(y + (h - 3.0) / 2.0);
        double textLeft = chipX + ICON_CHIP_SIZE + 8.0;
        double textRight = chevronX - 8.0;
        double textWidth = Math.max(0.0, textRight - textLeft);
        this.nameLabel.K(textLeft);
        this.nameLabel.S(y);
        this.nameLabel.Y(h / 2.0);
        this.nameLabel.o(textWidth);
        this.subtitleLabel.K(textLeft);
        this.subtitleLabel.S(y + h / 2.0);
        this.subtitleLabel.Y(h / 2.0);
        this.subtitleLabel.o(textWidth);
    }
}
