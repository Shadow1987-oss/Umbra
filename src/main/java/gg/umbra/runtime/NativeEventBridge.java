package gg.umbra.runtime;

import gg.umbra.Umbra;

public class NativeEventBridge {
    public static void reg(Class<?> eventClass, int eventId) {
    }

    public static void call(Object event) {
        if (Umbra.INSTANCE == null) {
            return;
        }
    }

}

