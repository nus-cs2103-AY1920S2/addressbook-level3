package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {
    private static int firstErrorEncountered = 1;
    private static int secondErrorEncountered = 2;

    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Checks that {@code condition} is which condition and display the respective message.
     * Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is 1 or 2.
     */
    public static void checkArgument(int condition, String errorMessage, String alternateErrorMessage) {
        if (condition == firstErrorEncountered) {
            throw new IllegalArgumentException(errorMessage);
        } else if (condition == secondErrorEncountered) {
            throw new IllegalArgumentException(alternateErrorMessage);
        }
    }
}
