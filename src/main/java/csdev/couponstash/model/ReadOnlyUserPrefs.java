package csdev.couponstash.model;

import java.nio.file.Path;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.StashSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    /**
     * Gets the StashSettings representing user-set application
     * settings from the UserPrefs.
     * @return The StashSettings that holds application settings
     *     like symbol to be used for money.
     */
    StashSettings getStashSettings();

    Path getCouponStashFilePath();

}
