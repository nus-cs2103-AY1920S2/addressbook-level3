package com.notably.model.userpref;

import java.nio.file.Path;

import com.notably.commons.GuiSettings;

/**
 * Represents User's preferences.
 */
public interface UserPrefModel extends ReadOnlyUserPrefModel {
    /**
     * Sets the current user prefs.
     */
    public void setUserPrefModel(ReadOnlyUserPrefModel userPrefModel);

    /**
     * Gets the current user prefs.
     */
    public ReadOnlyUserPrefModel getUserPrefModel();

    /**
     * Resets the existing data of this {@code UserPrefModel} with {@code newUserPrefModel}.
     */
    public void resetUserPrefModel(ReadOnlyUserPrefModel newUserPrefModel);

    /**
     * Sets the user prefs' GUI settings.
     */
    public void setGuiSettings(GuiSettings guiSettings);

    /**
     * Sets the user prefs' path of block data file.
     */
    public void setBlockDataFilePath(Path blockDataFilePath);
}
