package seedu.jelphabot.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.jelphabot.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path jelphaBotFilePath = Paths.get("data" , "jelphabot.json");
    private Path remindersFilePath = Paths.get("data", "reminder.json");

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
        setJelphaBotFilePath(newUserPrefs.getJelphaBotFilePath());
        setJelphaBotReminderFilePath(newUserPrefs.getRemindersFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public GuiSettings getPopUpWindowGuiSettings() {
        // create a new GuiSettings object with smaller dimensions than the default
        return new GuiSettings(540.0, 400.0);
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getJelphaBotFilePath() {
        return jelphaBotFilePath;
    }

    public Path getRemindersFilePath() {
        return remindersFilePath;
    }

    public void setJelphaBotFilePath(Path jelphaBotFilePath) {
        requireNonNull(jelphaBotFilePath);
        this.jelphaBotFilePath = jelphaBotFilePath;
    }

    public void setJelphaBotReminderFilePath(Path remindersFilePath) {
        requireNonNull(remindersFilePath);
        this.remindersFilePath = remindersFilePath;
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
                && jelphaBotFilePath.equals(o.jelphaBotFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, jelphaBotFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + jelphaBotFilePath);
        return sb.toString();
    }

}
