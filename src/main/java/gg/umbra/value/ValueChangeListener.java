package gg.umbra.value;

import gg.umbra.value.Value;

public interface ValueChangeListener<V extends Value<?, V>> {
    public void onValueChanged(V value);
}
