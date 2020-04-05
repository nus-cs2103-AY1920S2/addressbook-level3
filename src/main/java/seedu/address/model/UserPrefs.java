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
    private Path academicsFilePath = Paths.get("data" , "academics.json");
    private Path adminFilePath = Paths.get("data", "admin.json");
    private Path eventHistoryFilePath = Paths.get("data", "events.json");
    private Path notesFilePath = Paths.get("data", "notes.json");


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
        setAdminFilePath(newUserPrefs.getAdminFilePath());
        setAcademicsFilePath(newUserPrefs.getAcademicsFilePath());
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

    public Path getAcademicsFilePath() {
        return academicsFilePath;
    }

    public void setAcademicsFilePath(Path academicsFilePath) {
        requireNonNull(academicsFilePath);
        this.academicsFilePath = academicsFilePath;
    }

    public Path getAdminFilePath() {
        return adminFilePath;
    }

    public void setAdminFilePath(Path adminFilePath) {
        requireNonNull(adminFilePath);
        this.adminFilePath = adminFilePath;
    }

    public Path getEventHistoryFilePath() {
        return eventHistoryFilePath;
    }

    public void setEventHistoryFilePath(Path eventHistoryFilePath) {
        this.eventHistoryFilePath = eventHistoryFilePath;
    }

    public Path getNotesFilePath() {
        return notesFilePath;
    }

    public void setNotesFilePath(Path notesFilePath) {
        requireNonNull(notesFilePath);
        this.notesFilePath = notesFilePath;
    }


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
                && addressBookFilePath.equals(o.addressBookFilePath)
                && academicsFilePath.equals(o.academicsFilePath)
                && adminFilePath.equals(o.adminFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, academicsFilePath, adminFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data addressbook file location : " + addressBookFilePath);
        sb.append("\nLocal data ad,om file location : " + adminFilePath);
        sb.append("\nLocal data academics file location : " + academicsFilePath);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nAcademics data file location : " + academicsFilePath);
        sb.append("\nNotes data file location : " + notesFilePath);
        return sb.toString();
    }
}
