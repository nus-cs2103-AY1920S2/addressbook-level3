package fithelper.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import fithelper.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path fitHelperFilePath = Paths.get("data" , "fithelper.json");
    private Path userProfilePath = Paths.get("data", "userprofile.json");
    private Path weightRecordsPath = Paths.get("data", "weightrecords");

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
        setFitHelperFilePath(newUserPrefs.getFitHelperFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getFitHelperFilePath() {
        return fitHelperFilePath;
    }

    public void setFitHelperFilePath(Path fitHelperFilePath) {
        requireNonNull(fitHelperFilePath);
        this.fitHelperFilePath = fitHelperFilePath;
    }

    public Path getUserProfilePath() {
        return userProfilePath;
    }

    public void setUserProfilePath(Path userProfilePath) {
        requireNonNull(userProfilePath);
        this.userProfilePath = userProfilePath;
    }

    public Path getWeightRecordsPath() {
        return weightRecordsPath;
    }

    public void setWeightRecordsPath(Path weightRecordsPath) {
        requireNonNull(weightRecordsPath);
        this.weightRecordsPath = weightRecordsPath;
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
                && fitHelperFilePath.equals(o.fitHelperFilePath)
                && userProfilePath.equals(o.userProfilePath)
                && weightRecordsPath.equals(o.weightRecordsPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, fitHelperFilePath, userProfilePath, weightRecordsPath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nEntry data file location : ").append(fitHelperFilePath);
        sb.append("\nUser Profile data file location : " + userProfilePath);
        sb.append("\nWeight Records data file location : " + weightRecordsPath);
        return sb.toString();
    }

}
