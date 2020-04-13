package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.calender.Task;

/**
 * A class to access ModuleBook data stored as a json file on the hard disk.
 */
public class JsonCalendarStorage implements CalendarBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonModuleBookStorage.class);

    private Path filePath;

    public JsonCalendarStorage(Path filePath) {
        this.filePath = filePath;
    }


    @Override
    public Path getCalendarEntriesFilePath() {
        return filePath;
    }

    @Override
    public Optional<ObservableList<Task>> readCalendar() throws DataConversionException, IOException {
        return readCalendar(filePath);
    }

    @Override
    public Optional<ObservableList<Task>> readCalendar(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableCalenderBook> jsonCalendarBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableCalenderBook.class);
        if (!jsonCalendarBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCalendarBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCalendar(ObservableList<Task> taskList) throws IOException {
        saveCalendar(taskList, filePath);

    }

    @Override
    public void saveCalendar(ObservableList<Task> taskList, Path filePath) throws IOException {
        requireNonNull(taskList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCalenderBook(taskList), filePath);

    }
}
