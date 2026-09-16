package gg.umbra.notification;

public interface NotificationContent {
    double getHeight();

    double getWidth();

    void render(double x, double y);
}
