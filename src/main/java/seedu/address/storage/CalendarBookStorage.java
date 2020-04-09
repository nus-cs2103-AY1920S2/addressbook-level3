package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.calender.Task;

/**
 * API for module book storage
 */
public interface CalendarBookStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getCalendarEntriesFilePath();

    /**
     * Returns Calendar data as a List of task
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ObservableList<Task>> readCalendar() throws DataConversionException, IOException;

    /**
     * @see #readCalendar()
     */
    Optional<ObservableList<Task>> readCalendar(Path filePath) throws DataConversionException, IOException;


    void saveCalendar(ObservableList<Task> taskList) throws IOException;

    /**
     *
     */
    void saveCalendar(ObservableList<Task> taskList, Path filePath) throws IOException;

}
