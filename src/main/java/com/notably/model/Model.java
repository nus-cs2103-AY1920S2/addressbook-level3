package com.notably.model;

import java.nio.file.Path;

import com.notably.commons.GuiSettings;
import com.notably.model.block.BlockModel;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.viewstate.ViewStateModel;

/**
 * The API of the Model component.
 */
public interface Model extends BlockModel, SuggestionModel, ViewStateModel {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' block data file path.
     */
    Path getBlockDataFilePath();

    /**
     * Sets the user prefs' block data file path.
     */
    void setBlockDataFilePath(Path blockDataFilePath);
}

