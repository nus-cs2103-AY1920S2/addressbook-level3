package csdev.couponstash.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Helper functions for handling UI.
 */
public class UiUtil {

    /**
     * Creates accelerator for {@code scene} stage.
     */
    public static void setAccelerator(Scene scene, KeyCombination keyCombination, Runnable runnable) {
        scene.getAccelerators().put(keyCombination, runnable);
    }

    /**
     * Creates an accelerator to exit {@code root}.
     * This accelerator will be attached to {@code scene}.
     *
     * @param root          Stage to be exited from.
     * @param scene         Scene to attach the accelerator.
     * @param logger        Logger to log the exit process.
     * @param windowName    Window name of the window to be exited.
     */
    public static void setExitAccelerator(Stage root, Scene scene, Logger logger, String windowName) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        Runnable runnable = () -> {
            logger.info("Ctrl + Q is pressed. " + windowName + " will close.");
            root.close();
        };
        setAccelerator(scene, keyCombination, runnable);
    }
}
