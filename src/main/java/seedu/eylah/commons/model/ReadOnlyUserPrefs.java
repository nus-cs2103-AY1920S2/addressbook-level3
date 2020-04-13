package seedu.eylah.commons.model;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    Path getMyselfFilePath();

    void setMyselfFilePath(Path myselfFilePath);

    Path getFoodBookFilePath();

    void setFoodBookFilePath(Path foodBookFilePath);

    void setPersonAmountBookFilePath(Path personAmountBookFilePath);

    Path getPersonAmountBookFilePath();

    Path getReceiptBookFilePath();
}
