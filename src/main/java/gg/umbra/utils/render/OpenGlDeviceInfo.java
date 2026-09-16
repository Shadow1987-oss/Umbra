package gg.umbra.utils.render;

import gg.umbra.Umbra;
import gg.umbra.utils.render.GpuVendor;
import org.lwjgl.opengl.GL11;

public class OpenGlDeviceInfo {
    public static String vendorName;
    public static GpuVendor gpuVendor;
    private static int legacyValue;
    public static String rendererName;
    public static String versionName;

    public static int getLegacyConstant() {
        int ignoredLegacyValue = OpenGlDeviceInfo.getLegacyValue();
        return 112;
    }

    public static int getLegacyValue() {
        return legacyValue;
    }

    public static void collectDeviceInfo() {
        try {
            String renderer = GL11.glGetString((int)7937);
            String vendor = GL11.glGetString((int)7936);
            String version = GL11.glGetString((int)7938);
            vendorName = vendor != null ? vendor : "Unknown Vendor";
            rendererName = renderer != null ? renderer : "Unknown GPU";
            versionName = version != null ? version : "Unknown Version";
            gpuVendor = OpenGlDeviceInfo.detectGpuVendor(vendor);
        }
        catch (Exception exception) {
            Umbra.debugLog("Error getting OpenGL: " + exception.getMessage());
        }
    }

    public static void setLegacyValue(int value) {
        legacyValue = value;
    }

    static {
        OpenGlDeviceInfo.setLegacyValue(0);
        gpuVendor = GpuVendor.UNKNOWN;
        vendorName = null;
        versionName = null;
        rendererName = null;
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    private static GpuVendor detectGpuVendor(String vendorName) {
        if (vendorName == null) {
            return GpuVendor.UNKNOWN;
        }
        switch (vendorName) {
            case "NVIDIA Corporation": {
                return GpuVendor.NVIDIA;
            }
            case "Intel": 
            case "Intel Open Source Technology Center": {
                return GpuVendor.INTEL;
            }
            case "AMD": 
            case "ATI Technologies Inc.": {
                return GpuVendor.AMD;
            }
        }
        return GpuVendor.UNKNOWN;
    }

    public static void appendDeviceInfo(StringBuilder output) {
        OpenGlDeviceInfo.collectDeviceInfo();
        output.append("GPU Vendor: ").append(gpuVendor.name()).append(" (").append(vendorName).append(")\n");
        output.append("GPU Renderer: ").append(rendererName).append('\n');
        output.append("OpenGL Version: ").append(versionName).append('\n');
    }

    private static void logCapabilities() {
        try {
            Umbra.debugLog("MAX_TEXTURE_SIZE - " + GL11.glGetInteger((int)3379));
            Umbra.debugLog("MAX_TEXTURE_UNITS - " + GL11.glGetInteger((int)34930));
            Umbra.debugLog("MAX_VERTEX_ATTRIBS - " + GL11.glGetInteger((int)34921));
            Umbra.debugLog("MAX_COLOR_ATTACHMENTS - " + GL11.glGetInteger((int)36063));
            Umbra.debugLog("MAX_VIEWPORT_WIDTH - " + GL11.glGetInteger((int)3386));
            Umbra.debugLog("MAX_VERTEX_UNIFORM_COMPONENTS - " + GL11.glGetInteger((int)35658));
            Umbra.debugLog("MAX_FRAGMENT_UNIFORM_COMPONENTS - " + GL11.glGetInteger((int)35657));
        }
        catch (Exception exception) {
            Umbra.debugLog("Failed to collect GPU capabilities: " + exception.getMessage());
        }
    }

    public static void logDeviceInfo() {
        Umbra.debugLog("===== Graphics Information =====");
        Umbra.debugLog("Vendor: " + gpuVendor.name() + " (" + vendorName + ")");
        Umbra.debugLog("Device Name: " + rendererName);
        Umbra.debugLog("Driver Version: " + versionName);
        Umbra.debugLog("---GPU Capabilities---");
        OpenGlDeviceInfo.logCapabilities();
        Umbra.debugLog("================================");
    }
}
