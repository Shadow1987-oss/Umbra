package gg.umbra.value;

import gg.umbra.value.Value;

public interface ValueChangeValidator<V extends Value<T, V>, T> {
    public boolean isValidChange(V value, T previousValue, T newValue);
}
