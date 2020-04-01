package com.notably.model.userpref;

import java.nio.file.Path;

import com.notably.commons.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefModel {
    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Gets the path of the block data file.
     */
    Path getBlockDataFilePath();
}
