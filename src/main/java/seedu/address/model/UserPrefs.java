package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path consultTAbleFilePath = Paths.get("data", "consults.json");
    private Path tutorialTableFilePath = Paths.get("data", "tutorials.json");
    private Path reminderTableFilePath = Paths.get("data", "reminders.json");

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
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    // Consults start
    public Path getConsultTAbleFilePath() {
        return consultTAbleFilePath;
    }

    public void setConsultTAbleFilePath(Path consultTAbleFilePath) {
        requireNonNull(consultTAbleFilePath);
        this.consultTAbleFilePath = consultTAbleFilePath;
    }
    //end

    // Tutorials start
    public Path getTutorialTableFilePath() {
        return tutorialTableFilePath;
    }

    public void setTutorialTableFilePath(Path tutorialTableFilePath) {
        requireNonNull(tutorialTableFilePath);
        this.tutorialTableFilePath = tutorialTableFilePath;
    }
    // end

    // Reminders start ==============================================================================
    public Path getReminderTableFilePath() {
        return reminderTableFilePath;
    }

    public void setReminderTableFilePath(Path reminderTableFilePath) {
        requireNonNull(reminderTableFilePath);
        this.reminderTableFilePath = reminderTableFilePath;
    }
    //end

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
