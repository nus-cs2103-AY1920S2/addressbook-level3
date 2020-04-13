package com.flashspeed.model;

import java.nio.file.Path;

import com.flashspeed.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getLibraryFilePath();
}
