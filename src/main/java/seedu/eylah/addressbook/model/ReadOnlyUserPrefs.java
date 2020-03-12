package seedu.eylah.addressbook.model;

import java.nio.file.Path;

import seedu.eylah.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
