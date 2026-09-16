package gg.umbra.value;

import gg.umbra.value.Value;
import gg.umbra.value.ValueAccessor;

public class DirectValueAccessor<K, T extends Value<K, T>>
extends ValueAccessor {
    public void setValue(Object value) {
        this.getSourceValue().setDirectValue(value);
    }

    public Object getValue() {
        return this.getSourceValue().getDirectValue();
    }

    public DirectValueAccessor(Value<K, T> value) {
        super(value);
    }

}
