package tatracker.commons.core;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

/**
 * Throws AWTException if SystemTray is not supported on the current platform.
 */
public class Notification {
    private static Notification singleton;
    private final TrayIcon trayIcon;

    private Notification() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("NotifyIcon.png");
        trayIcon = new TrayIcon(image, "TrayIcon");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("TAT Tracker TEMP");
        tray.add(trayIcon);
    }

    private void notify(String caption, String message, TrayIcon.MessageType type) throws AWTException {
        trayIcon.displayMessage(caption, message, type);
    }

    public static void sendNotification(String caption, String message, TrayIcon.MessageType type) throws AWTException {
        Notification.getInstance().notify(caption, message, type);
    }

    public static Notification getInstance() throws AWTException {
        if (singleton == null) {
            singleton = new Notification();
        }

        return singleton;
    }
}
