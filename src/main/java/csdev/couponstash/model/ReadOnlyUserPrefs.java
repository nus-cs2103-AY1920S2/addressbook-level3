package csdev.couponstash.model;

import java.nio.file.Path;

import csdev.couponstash.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getCouponStashFilePath();

}
