package gg.umbra.value;

import com.google.gson.JsonObject;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptionSetting
extends ConditionalValue<ModeSelection, OptionSetting> {
    private static GuiComponent[] legacyGuiState;
    private static final String JSON_VALUE_PROPERTY;
    private final Map<Value<?, ?>, ArrayList<ModeSelection>> activeModesByDependentValue = new HashMap();
    private final String displayName;
    private final ModeSelection[] modes;

    public static void setLegacyGuiState(GuiComponent[] state) {
        legacyGuiState = state;
    }

    public static OptionSetting create(Object owner, String name, ModeSelection defaultMode, ModeSelection ... modes) {
        return OptionSetting.create(owner, name, name, defaultMode, modes);
    }

    public static OptionSetting createConfigured(Object owner, String name, String displayName, String description, ModeSelection defaultMode, int legacyIndex, ModeSelection[] modes) {
        OptionSetting modeValue = new OptionSetting(owner, name, displayName, defaultMode, modes);
        modeValue.setDescription(description);
        for (ModeSelection mode : modes) {
            mode.attachToMode(modeValue);
        }
        return modeValue;
    }

    @Override
    public String getDisplayValue() {
        return ((ModeSelection)this.getValue()).toString();
    }

    public int getSelectedIndex() {
        for (int index = 0; index < this.getModes().length; ++index) {
            if (!((ModeSelection)this.getValue()).equals(this.getModes()[index])) continue;
            return index;
        }
        return 0;
    }

    public static GuiComponent[] getLegacyGuiState() {
        return legacyGuiState;
    }

    @Override
    public boolean hasActiveDependentBranch() {
        return true;
    }

    public static OptionSetting createWithDescriptionAndLegacyIndex(Object owner, String name, String description, ModeSelection defaultMode, int legacyIndex, ModeSelection ... modes) {
        return OptionSetting.createConfigured(owner, name, name, description, defaultMode, legacyIndex, modes);
    }

    public void addActiveMode(Value dependentValue, ModeOption modeOption) {
        this.addDependentValues(dependentValue);
        if (!this.activeModesByDependentValue.containsKey(dependentValue)) {
            this.activeModesByDependentValue.put(dependentValue, new ArrayList());
        }
        ArrayList<ModeSelection> activeModes = this.activeModesByDependentValue.get(dependentValue);
        activeModes.add(modeOption);
    }

    static {
        OptionSetting.setLegacyGuiState(null);
        JSON_VALUE_PROPERTY = "value";
    }

    public OptionSetting copyDefinition() {
        return new OptionSetting(null, this.getId(), this.getName(), (ModeSelection)this.getValue(), this.getModes());
    }

    @Override
    public OptionSetting copyValueDefinition() {
        return this.copyDefinition();
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    public void setValue(ModeSelection newMode) {
        if (((ModeSelection)this.getValue()).equals(newMode)) {
            return;
        }
        if (this.getOwner() != null && newMode instanceof SubHackValue && this.getValue() instanceof SubHackValue) {
            this.switchSubModule((SubHackValue)this.getValue(), (SubHackValue)newMode);
        }
        super.setValue(newMode);
    }

    public ModeSelection[] getModes() {
        return this.modes;
    }

    @Override
    public JsonObject toJson(boolean includeValue) {
        JsonObject jsonObject = this.toJson();
        if (this.getValue() != null) {
            jsonObject.addProperty(JSON_VALUE_PROPERTY, ((ModeSelection)this.getValue()).getSerializedName());
        }
        return jsonObject;
    }

    @Override
    public boolean isDependentValueActive(Value dependentValue) {
        if (this.activeModesByDependentValue.containsKey(dependentValue)) {
            ArrayList<ModeSelection> activeModes = this.activeModesByDependentValue.get(dependentValue);
            return activeModes.contains(this.getValue());
        }
        return false;
    }

    private void switchSubModule(SubHackValue previousMode, SubHackValue nextMode) {
        if (this.isPersistenceSuppressed()) {
            return;
        }
        Object parent = ((SubHack)previousMode.getInstance()).getParent();
        ((HackModule)parent).p(previousMode, nextMode);
    }

    public static OptionSetting create(Object owner, String name, String description, ModeSelection defaultMode, ModeSelection ... modes) {
        return OptionSetting.createConfigured(owner, name, name, description, defaultMode, 1, modes);
    }

    public static OptionSetting create(Object owner, String name, String displayName, String description, ModeSelection defaultMode, ModeSelection ... modes) {
        return OptionSetting.createConfigured(owner, name, displayName, description, defaultMode, 1, modes);
    }

    public void setSelectedIndex(int index) {
        this.setValue(this.getModes()[index]);
    }

    public OptionSetting(Object owner, String name, String displayName, ModeSelection defaultMode, ModeSelection[] modes) {
        super(owner, name, defaultMode);
        this.displayName = displayName;
        this.modes = modes;
        if (owner instanceof HackModule) {
            HackModule mod = (HackModule)owner;
            for (ModeSelection mode : modes) {
                if (!(mode instanceof SubHackValue)) continue;
                SubHackValue subModuleMode = (SubHackValue)mode;
                for (Value<?, ?> dependentValue : ((HackModule)subModuleMode.getInstance()).getAllValues()) {
                    mod.addValue(dependentValue);
                    this.addActiveMode(dependentValue, subModuleMode);
                }
            }
        }
    }

    @Override
    public void parse(String serializedMode) {
        OptionSetting owningOptionSetting = ((ModeSelection)this.getValue()).getMode();
        if (owningOptionSetting == null) {
            return;
        }
        ModeSelection parsedMode = ModeSelection.findBySerializedName(owningOptionSetting, serializedMode);
        if (parsedMode == null) {
            return;
        }
        this.setValue(parsedMode);
    }

    public void addModeDependentValues(ModeOption modeOption, Value ... values) {
        this.addDependentValues(values);
        for (Value dependentValue : values) {
            this.addActiveMode(dependentValue, modeOption);
        }
    }

}
