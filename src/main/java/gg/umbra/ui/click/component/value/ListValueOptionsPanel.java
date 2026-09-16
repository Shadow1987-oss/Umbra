package gg.umbra.ui.click.component.value;

import gg.umbra.input.MouseInput;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.animation.ColorAnimation;
import gg.umbra.ui.click.component.FlowLayoutComponent;
import gg.umbra.ui.click.component.PanelComponent;
import gg.umbra.ui.click.component.SelectableTextRowComponent;
import gg.umbra.ui.click.component.SpacerComponent;
import gg.umbra.ui.click.component.value.ListValueAddEntryInputComponent;
import gg.umbra.ui.click.component.value.RemoveItemFilterListEntryHandler;
import gg.umbra.ui.click.component.value.RemoveOptionalLimitEntryHandler;
import gg.umbra.ui.font.SmoothFontRenderer;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.ItemIconRenderer;
import gg.umbra.value.ItemSuggestionProvider;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.ListValue;
import gg.umbra.value.ListValueSuggestionProvider;
import gg.umbra.value.OptionalLimitEntry;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.ItemStack;
import java.awt.Color;
import java.util.List;

public class ListValueOptionsPanel
extends PanelComponent {
    private static final double SUGGESTION_ROW_HEIGHT = 18.0;
    private static final int SUGGESTION_MAX_ROWS = 8;
    private static final Color SUGGESTION_BACKGROUND = new Color(28, 28, 30, 245);
    private static final Color SUGGESTION_HOVER = new Color(255, 255, 255, 30);
    private static final Color SUGGESTION_TEXT = new Color(225, 225, 225);
    private static final Color SUGGESTION_SUBTEXT = new Color(135, 135, 135);
    private final FlowLayoutComponent entriesLayout;
    private final ListValue listValue;
    private final boolean blockedList;
    private final boolean sidecarStyle;
    private ListValueAddEntryInputComponent addEntryInput;
    private boolean wasSuggestionsActive = false;
    private static final Color ENTRY_BACKGROUND = new Color(37, 36, 37);


    public ListValueOptionsPanel(ListValue listValue, boolean blockedList, double width, double height, boolean sidecarStyle) {
        super(width, height);
        this.listValue = listValue;
        this.blockedList = blockedList;
        this.sidecarStyle = sidecarStyle;
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        if (this.sidecarStyle) {
            this.setShowDisabledOverlay(false);
        }
        if (this.sidecarStyle) {
            this.addChildren(new SpacerComponent(0.0, 4.0));
        }
        this.addEntryInput = new ListValueAddEntryInputComponent(this, blockedList, "Add entry...", listValue);
        this.addEntryInput.setSuggestionProvider(listValue.getSuggestionProvider());
        this.addEntryInput.setUseExplicitWidth(true);
        this.addEntryInput.o(width);
        if (this.sidecarStyle) {
            this.addEntryInput.setBorderThickness(0.75f);
            this.addEntryInput.setCornerRadius(4.0f);
            this.addEntryInput.setBorderAnimation(ColorAnimation.Y(ListValueOptionsPanel.J.k));
            this.addEntryInput.setBackgroundColorOrNull(null);
            this.addEntryInput.setTextColor(ListValueOptionsPanel.J.Z);
            this.addEntryInput.setPlaceholderColor(ListValueOptionsPanel.J.h);
            this.addEntryInput.getActionButton().o(10.0);
            this.addEntryInput.getActionButton().Y(10.0);
            this.addEntryInput.getActionButton().setIconWidth(6.0);
            this.addEntryInput.getActionButton().setIconHeight(6.0);
            this.addEntryInput.setVerticalInset(0.0f);
            this.addEntryInput.setUseExplicitHeight(true);
            this.addEntryInput.Y(16.0);
        }
        this.addChildren(this.addEntryInput);
        if (this.sidecarStyle) {
            this.addChildren(new SpacerComponent(0.0, 5.0));
        }
        this.entriesLayout = new FlowLayoutComponent(width);
        this.entriesLayout.t(height - 25.0);
        if (this.sidecarStyle) {
            this.entriesLayout.setShowDisabledOverlay(false);
        }
        this.addChildren(this.entriesLayout);
    }

    private boolean isSuggestionDropdownActive() {
        if (this.addEntryInput == null || !this.addEntryInput.isFocused()) {
            return false;
        }
        ListValueSuggestionProvider suggestionProvider = this.addEntryInput.getSuggestionProvider();
        return suggestionProvider != null && suggestionProvider.getSuggestions() != null && !suggestionProvider.getSuggestions().isEmpty();
    }

    @Override
    public void H() {
        boolean active = this.isSuggestionDropdownActive();
        if (active != this.wasSuggestionsActive) {
            this.wasSuggestionsActive = active;
            this.entriesLayout.setVisible(!active);
        }
        super.H();
        if (active) {
            this.drawSuggestionOverlay();
        }
    }

    private void drawSuggestionOverlay() {
        ListValueSuggestionProvider suggestionProvider = this.addEntryInput.getSuggestionProvider();
        List<String> suggestions = suggestionProvider.getSuggestions();
        int rows = Math.min(suggestions.size(), SUGGESTION_MAX_ROWS);
        if (rows <= 0) {
            return;
        }
        double panelX = this.G$src$D$1b2f02a();
        double panelWidth = this.A();
        double dropdownTop = this.addEntryInput.n() + this.addEntryInput.L();
        double dropdownHeight = (double)rows * SUGGESTION_ROW_HEIGHT + 2.0;
        double mouseX = MouseInput.getMouseX();
        double mouseY = MouseInput.getInvertedMouseY();
        GuiRenderPrimitives.C(panelX + 2.0, dropdownTop, panelWidth - 4.0, dropdownHeight, SUGGESTION_BACKGROUND);
        for (int i = 0; i < rows; ++i) {
            double rowTop = dropdownTop + 2.0 + (double)i * SUGGESTION_ROW_HEIGHT;
            boolean hovered = mouseX >= panelX + 2.0 && mouseX <= panelX + panelWidth - 2.0 && mouseY >= rowTop && mouseY <= rowTop + SUGGESTION_ROW_HEIGHT;
            if (hovered) {
                GuiRenderPrimitives.C(panelX + 3.0, rowTop + 0.5, panelWidth - 6.0, SUGGESTION_ROW_HEIGHT - 1.0, SUGGESTION_HOVER);
            }
            String displayName = suggestions.get(i);
            ItemStack icon = ItemSuggestionProvider.getItemStack(displayName);
            if (icon != null) {
                ItemIconRenderer.renderItemStack(icon, (float)(panelX + 6.0), (float)(rowTop + 2.0), 14, 14);
            }
            SmoothFontRenderer textRenderer = this.getFontRenderer(0.9);
            String registryName = ItemSuggestionProvider.getRegistryName(displayName);
            double available = panelWidth - 30.0;
            String nameText = displayName;
            if (registryName != null && !registryName.isEmpty()) {
                double nameWidth = textRenderer.N(nameText);
                double regWidth = this.getFontRenderer(0.7).N(registryName);
                if (nameWidth + regWidth + 10.0 > available) {
                    int maxChars = Math.max(1, (int)((available - regWidth - 10.0) / Math.max(1.0, textRenderer.N("W"))));
                    if (nameText.length() > maxChars) {
                        nameText = nameText.substring(0, maxChars) + "...";
                    }
                }
            }
            textRenderer.d(nameText, panelX + 24.0, rowTop + SUGGESTION_ROW_HEIGHT / 2.0 - textRenderer.d("A") / 2.0, SUGGESTION_TEXT);
            if (registryName != null && !registryName.isEmpty()) {
                SmoothFontRenderer smallRenderer = this.getFontRenderer(0.7);
                String registrySuffix = "(" + registryName + ")";
                double registryX = panelX + panelWidth - 8.0 - smallRenderer.N(registrySuffix);
                smallRenderer.d(registrySuffix, registryX, rowTop + SUGGESTION_ROW_HEIGHT / 2.0 - smallRenderer.d("A") / 2.0, SUGGESTION_SUBTEXT);
            }
        }
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        if (!this.isSuggestionDropdownActive()) {
            return;
        }
        List<String> suggestions = this.addEntryInput.getSuggestionProvider().getSuggestions();
        int rows = Math.min(suggestions.size(), SUGGESTION_MAX_ROWS);
        double dropdownTop = this.addEntryInput.n() + this.addEntryInput.L();
        double clickY = MouseInput.getInvertedMouseY();
        for (int i = 0; i < rows; ++i) {
            double rowTop = dropdownTop + 2.0 + (double)i * SUGGESTION_ROW_HEIGHT;
            if (clickY < rowTop || clickY > rowTop + SUGGESTION_ROW_HEIGHT) continue;
            this.addEntryInput.setText(suggestions.get(i));
            this.addEntryInput.submit();
            return;
        }
    }

    public ListValueOptionsPanel(ListValue listValue, boolean bl, double d, double d2) {
        this(listValue, bl, d, d2, false);
    }

    public void refreshEntries() {
        block5: {
            block4: {
                this.entriesLayout.removeMarkedChildren();
                if (!(this.listValue instanceof OptionalItemFilter)) break block4;
                for (OptionalLimitEntry optionalLimitEntry : (List<OptionalLimitEntry>)((OptionalItemFilter)this.listValue).getValue()) {
                    SelectableTextRowComponent selectableTextRowComponent = new SelectableTextRowComponent(this.blockedList ? ListValueOptionsPanel.J.d : ListValueOptionsPanel.J.B, optionalLimitEntry.getValue()).setDeleteActionListener(new RemoveOptionalLimitEntryHandler(this, optionalLimitEntry)).setSelectionTarget(optionalLimitEntry);
                    selectableTextRowComponent.setUseExplicitWidth(true);
                    selectableTextRowComponent.o(this.A());
                    if (this.sidecarStyle) {
                        selectableTextRowComponent.setUseExplicitHeight(true);
                        selectableTextRowComponent.Y(20.0);
                        selectableTextRowComponent.setIndicatorSize(4);
                        selectableTextRowComponent.setIndicatorOffsetY(0.5f);
                        selectableTextRowComponent.setBackgroundColor(ENTRY_BACKGROUND);
                    }
                    this.entriesLayout.h(selectableTextRowComponent, new Object[0]);
                }
                break block5;
            }
            if (!(this.listValue instanceof ItemFilterList)) break block5;
            for (ItemLimitData itemLimitData : (List<ItemLimitData>)((ItemFilterList)this.listValue).getValue()) {
                String string = itemLimitData.getName() + (itemLimitData.getMetadata() < 0 ? "" : ":" + itemLimitData.getMetadata());
                SelectableTextRowComponent selectableTextRowComponent = new SelectableTextRowComponent(this.blockedList ? ListValueOptionsPanel.J.d : ListValueOptionsPanel.J.B, string).setDeleteActionListener(new RemoveItemFilterListEntryHandler(this, itemLimitData)).setSelectionTarget(itemLimitData);
                selectableTextRowComponent.setUseExplicitWidth(true);
                selectableTextRowComponent.o(this.A());
                if (this.sidecarStyle) {
                    selectableTextRowComponent.setUseExplicitHeight(true);
                    selectableTextRowComponent.Y(20.0);
                    selectableTextRowComponent.setIndicatorSize(4);
                    selectableTextRowComponent.setIndicatorOffsetY(0.5f);
                    selectableTextRowComponent.setBackgroundColor(ENTRY_BACKGROUND);
                }
                this.entriesLayout.h(selectableTextRowComponent, new Object[0]);
            }
        }
    }

    public ListValueOptionsPanel(ListValue listValue, boolean bl) {
        this(listValue, bl, 110.0, 110.0, false);
    }

    public static ListValue getListValueCompat(ListValueOptionsPanel panel) {
        return panel.listValue;
    }
}
