package nasa.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import nasa.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path nasaBookFilePath = Paths.get("data" , "nasabook.json");
    private Path historyBookFilePath = Paths.get("data" , "history.json");
    private Path uiHistoryBookFilePath = Paths.get("data", "uiHistory.json");
    private Path calendarExportPath = Paths.get("data");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     * @param userPrefs ReadOnlyUserPrefs
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     * @param newUserPrefs ReadOnlyUserPrefs
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setNasaBookFilePath(newUserPrefs.getNasaBookFilePath());
        setHistoryBookFilePath(newUserPrefs.getHistoryBookFilePath());
        setUiHistoryBookFilePath(newUserPrefs.getUiHistoryBookFilePath());
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

    public Path getHistoryBookFilePath() {
        return historyBookFilePath;
    }

    public Path getUiHistoryBookFilePath() {
        return uiHistoryBookFilePath;
    }

    public void setNasaBookFilePath(Path nasaBookFilePath) {
        requireNonNull(nasaBookFilePath);
        this.nasaBookFilePath = nasaBookFilePath;
    }

    public void setHistoryBookFilePath(Path historyBookFilePath) {
        requireNonNull(historyBookFilePath);
        this.historyBookFilePath = historyBookFilePath;
    }

    public void setUiHistoryBookFilePath(Path uiHistoryBookFilePath) {
        requireNonNull(uiHistoryBookFilePath);
        this.uiHistoryBookFilePath = uiHistoryBookFilePath;
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
                && nasaBookFilePath.equals(o.nasaBookFilePath)
                && historyBookFilePath.equals(o.historyBookFilePath)
                && uiHistoryBookFilePath.equals(o.uiHistoryBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, nasaBookFilePath, historyBookFilePath, uiHistoryBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + nasaBookFilePath);
        sb.append("\nLocal history data file location : " + historyBookFilePath);
        sb.append("\nLocal ui history data file location : " + uiHistoryBookFilePath);
        return sb.toString();
    }

    public Path getCalendarExportPath() {
        return calendarExportPath;
    }
}
