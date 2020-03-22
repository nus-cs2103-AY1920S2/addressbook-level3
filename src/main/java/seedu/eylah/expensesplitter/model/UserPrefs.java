package seedu.eylah.expensesplitter.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private Path personAmountFilePath = Paths.get("data", "personamount.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setPersonAmountBookFilePath(newUserPrefs.getPersonAmountBookFilePath());
    }

    public Path getPersonAmountBookFilePath() {
        return personAmountFilePath;
    }

    public void setPersonAmountBookFilePath(Path personAmountFilePath) {
        requireNonNull(personAmountFilePath);
        this.personAmountFilePath = personAmountFilePath;
    }


}
