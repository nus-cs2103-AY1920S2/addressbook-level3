package tatracker.commons.core;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class that handles the displaying of System Notifications.
 */
public class Notification {
    public static final String APPICON_PATH = "images/address_book_32.png";
    private static Notification singleton;
    private final TrayIcon trayIcon;

    private Notification() throws AWTException, IOException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = ImageIO.read(getClass().getClassLoader().getResource(APPICON_PATH));
        trayIcon = new TrayIcon(image, "TrayIcon");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("TA Tracker");
        tray.add(trayIcon);
    }

    private static Notification getInstance() throws AWTException, IOException {
        if (singleton == null) {
            singleton = new Notification();
        }

        return singleton;
    }

    private void notify(String caption, String message, TrayIcon.MessageType type) {
        trayIcon.displayMessage(caption, message, type);
    }

    /**
     * Sends
     * @param caption The title displayed in the notification
     * @param message The message displayed in the notification
     * @param type the type of notification (error, info, warning, etc.)
     * @throws AWTException if {@code SystemTray} is not supported on the current platform.
     */
    public static void sendNotification(String caption, String message, TrayIcon.MessageType type) throws AWTException, IOException {
        Notification.getInstance().notify(caption, message, type);
    }
}
