package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class Util
extends Wrapper {
    public static long glfwGetCurrentContext() {
        return Util.umbraInstance.getMappingsMapperCompat().Rf.glfwGetCurrentContext();
    }

    public Util(Object wrappedObject) {
        super(wrappedObject);
    }

    public static void glfwPollEvents() {
        Util.umbraInstance.getMappingsMapperCompat().Rf.glfwPollEvents();
    }
}
