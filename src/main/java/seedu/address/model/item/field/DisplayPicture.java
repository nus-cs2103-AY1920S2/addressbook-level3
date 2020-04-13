package seedu.address.model.item.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * The file path to image file to display as profile picture.
 */
public class DisplayPicture {
    public static final String MESSAGE_CONSTRAINTS_VALID_PATH = "Display picture path has to be a valid path of the "
            + "image you want to display as your profile picture. Example format: /Users/nhamquochung/Desktop/test.png";
    public static final String MESSAGE_CONSTRAINTS_FILE_TYPE = "Display picture has to be a valid image file "
            + "ending with .jpg, .png or .jpeg. Example format: /Users/nhamquochung/Desktop/test.png";

    /**
     * Must end with either '.jpg', '.png' or '.jpeg' with their cases ignored.
     */
    public static final String VALIDATION_REGEX = "((.*)+(\\.(?i)(jpg|png|jpeg))$)";


    public final String value;

    /**
     * Construct a {@code dpPath}.
     *
     * @param dpPath A valid file path.
     */
    public DisplayPicture(String dpPath) {
        requireNonNull(dpPath);
        checkArgument(isValidDisplayPicture(dpPath), MESSAGE_CONSTRAINTS_FILE_TYPE);
        value = dpPath;
    }

    /**
     * Return true if the dpPath is a valid file path.
     */
    public static boolean isValidDisplayPicture(String dpPath) {
        return dpPath.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicture // instanceof handles nulls
                && value.equals(((DisplayPicture) other).value)); // state check
    }
}
