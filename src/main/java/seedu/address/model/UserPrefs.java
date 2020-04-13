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
    private Path studentTAbleFilePath = Paths.get("data", "TAble.json");
    private Path consultTAbleFilePath = Paths.get("data", "consults.json");
    private Path tutorialTAbleFilePath = Paths.get("data", "tutorials.json");
    private Path modTAbleFilePath = Paths.get("data", "mods.json");
    private Path reminderTableFilePath = Paths.get("data", "reminders.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

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
        setStudentTAbleFilePath(newUserPrefs.getStudentTAbleFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getStudentTAbleFilePath() {
        return studentTAbleFilePath;
    }

    public void setStudentTAbleFilePath(Path studentTAbleFilePath) {
        requireNonNull(studentTAbleFilePath);
        this.studentTAbleFilePath = studentTAbleFilePath;
    }

    // Consults start =============================================================================
    public Path getConsultTAbleFilePath() {
        return consultTAbleFilePath;
    }

    public void setConsultTAbleFilePath(Path consultTAbleFilePath) {
        requireNonNull(consultTAbleFilePath);
        this.consultTAbleFilePath = consultTAbleFilePath;
    }

    // Tutorials start ============================================================================
    public Path getTutorialTAbleFilePath() {
        return tutorialTAbleFilePath;
    }

    public void setTutorialTAbleFilePath(Path tutorialTAbleFilePath) {
        requireNonNull(tutorialTAbleFilePath);
        this.tutorialTAbleFilePath = tutorialTAbleFilePath;
    }

    // Modules start ==============================================================================
    public Path getModTAbleFilePath() {
        return modTAbleFilePath;
    }

    public void setModTAbleFilePath(Path modTAbleFilePath) {
        requireNonNull(modTAbleFilePath);
        this.modTAbleFilePath = modTAbleFilePath;
    }

    // Reminders start ==============================================================================
    public Path getReminderTAbleFilePath() {
        return reminderTableFilePath;
    }

    public void setReminderTAbleFilePath(Path reminderTableFilePath) {
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
                && studentTAbleFilePath.equals(o.studentTAbleFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, studentTAbleFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + studentTAbleFilePath);
        return sb.toString();
    }

}
