package csdev.couponstash.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.StashSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private StashSettings stashSettings = new StashSettings();
    private Path couponStashFilePath = Paths.get("data" , "couponStash.json");

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
        setStashSettings(newUserPrefs.getStashSettings());
        setCouponStashFilePath(newUserPrefs.getCouponStashFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public StashSettings getStashSettings() {
        return this.stashSettings;
    }

    /**
     * Sets the new StashSettings that contains application
     * settings that were set by the user.
     * @param ss StashSettings that holds the application
     *           settings set by the user.
     */
    public void setStashSettings(StashSettings ss) {
        requireNonNull(ss);
        this.stashSettings = ss;
    }

    public Path getCouponStashFilePath() {
        return couponStashFilePath;
    }

    public void setCouponStashFilePath(Path couponStashFilePath) {
        requireNonNull(couponStashFilePath);
        this.couponStashFilePath = couponStashFilePath;
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
                && stashSettings.equals(o.stashSettings)
                && couponStashFilePath.equals(o.couponStashFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, stashSettings, couponStashFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nStash Settings : " + stashSettings);
        sb.append("\nLocal data file location : " + couponStashFilePath);
        return sb.toString();
    }

}
