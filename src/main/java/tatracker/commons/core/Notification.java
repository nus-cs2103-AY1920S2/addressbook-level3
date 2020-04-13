// @@author Eclmist
package tatracker.commons.core;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class that handles the displaying of System Notifications.
 */
public class Notification {
    public static final String APPICON_PATH = "images/icon.png";
    private static Notification singleton;
    private final TrayIcon trayIcon;

    private Notification() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        Image trayImage;

        try {
            trayImage = ImageIO.read(getClass().getClassLoader().getResource(APPICON_PATH));
        } catch (IOException e) {
            System.err.println("Unable to load Application Icon for the System Tray!");
            trayImage = Toolkit.getDefaultToolkit().createImage("placeholder-icon.png");
        }

        trayIcon = new TrayIcon(trayImage, "TrayIcon");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("TA Tracker");
        tray.add(trayIcon);
    }

    private static Notification getInstance() throws AWTException {
        if (singleton == null) {
            singleton = new Notification();
        }

        return singleton;
    }

    private void notify(String caption, String message, TrayIcon.MessageType type) {
        trayIcon.displayMessage(caption, message, type);
    }

    /**
     * Triggers a OS-level system notification.
     *
     * @param caption The title displayed in the notification
     * @param message The message displayed in the notification
     * @param type    the type of notification (error, info, warning, etc.)
     * @throws AWTException if {@code SystemTray} is not supported on the current platform.
     */
    public static void sendNotification(String caption, String message, TrayIcon.MessageType type) throws AWTException {
        Notification.getInstance().notify(caption, message, type);
    }

    /**
     * Immediately destroys the system tray icon
     */
    public static void dispose() {
        if (singleton != null) {
            SystemTray.getSystemTray().remove(singleton.trayIcon);
        }
    }
}
