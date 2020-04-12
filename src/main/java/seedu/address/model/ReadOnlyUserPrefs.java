package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    /**
     * Get GUI Settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Get address book File Path.
     */
    Path getAddressBookFilePath();

}
