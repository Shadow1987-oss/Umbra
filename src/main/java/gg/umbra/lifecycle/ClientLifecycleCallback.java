package gg.umbra.lifecycle;

public interface ClientLifecycleCallback {
    void log(String message);

    void close();
}
