package com.notably.model;

import java.nio.file.Path;

import com.notably.commons.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getBlockDataFilePath();

}
