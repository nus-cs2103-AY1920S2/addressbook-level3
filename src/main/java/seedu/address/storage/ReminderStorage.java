package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Represents a storage for {@link seedu.address.model.reminder.Reminder}.
 */
public interface ReminderStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRemindersFilePath();

    /**
     * Returns Reminder data as a {@link ReadOnlyReminder}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyReminder> readReminders() throws DataConversionException, IOException;

    /**
     * @see #getRemindersFilePath()
     */
    Optional<ReadOnlyReminder> readReminders(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyReminder} to the storage.
     * @param reminders cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReminders(ReadOnlyReminder reminders) throws IOException;

    /**
     * @see #saveReminders(ReadOnlyReminder)
     */
    void saveReminders(ReadOnlyReminder reminders, Path filePath) throws IOException;
}
