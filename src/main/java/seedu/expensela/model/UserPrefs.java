package seedu.expensela.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.expensela.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path expenseLaFilePath = Paths.get("data" , "expenseLa.json");
    private Path globalDataFilePath = Paths.get("data", "globalData.json");

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
        setExpenseLaFilePath(newUserPrefs.getExpenseLaFilePath());
        setGlobalDataFilePath(newUserPrefs.getGlobalDataFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getExpenseLaFilePath() {
        return expenseLaFilePath;
    }

    public void setExpenseLaFilePath(Path expenseLaFilePath) {
        requireNonNull(expenseLaFilePath);
        this.expenseLaFilePath = expenseLaFilePath;
    }

    public Path getGlobalDataFilePath() {
        return globalDataFilePath;
    }

    public void setGlobalDataFilePath(Path globalDataFilePath) {
        requireNonNull(globalDataFilePath);
        this.globalDataFilePath = globalDataFilePath;
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
                && expenseLaFilePath.equals(o.expenseLaFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, expenseLaFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + expenseLaFilePath);
        return sb.toString();
    }

}
