package nasa.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import nasa.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefsNasa implements ReadOnlyUserPrefsNasa {

    private GuiSettings guiSettings = new GuiSettings();
    private Path nasaBookFilePath = Paths.get("data" , "addressbook.json");

    /**
     * Creates a {@code UserPrefsNasa} with default values.
     */
    public UserPrefsNasa() {}

    /**
     * Creates a {@code UserPrefsNasa} with the prefs in {@code userPrefs}.
     */
    public UserPrefsNasa(ReadOnlyUserPrefsNasa userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefsNasa} with {@code newUserPrefsNasa}.
     */
    public void resetData(ReadOnlyUserPrefsNasa newUserPrefsNasa) {
        requireNonNull(newUserPrefsNasa);
        setGuiSettings(newUserPrefsNasa.getGuiSettings());
        setNasaBookFilePath(newUserPrefsNasa.getNasaBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getNasaBookFilePath() {
        return nasaBookFilePath;
    }

    public void setNasaBookFilePath(Path nasaBookFilePath) {
        requireNonNull(nasaBookFilePath);
        this.nasaBookFilePath = nasaBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefsNasa)) { //this handles null as well.
            return false;
        }

        UserPrefsNasa o = (UserPrefsNasa) other;

        return guiSettings.equals(o.guiSettings)
                && nasaBookFilePath.equals(o.nasaBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, nasaBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + nasaBookFilePath);
        return sb.toString();
    }

}
