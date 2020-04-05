package seedu.address.storage.notes;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.notes.ReadOnlyNotes;

/**
 * A class to access Notes data stored as a json file on the hard disk
 */
public class JsonNotesManagerStorage implements NotesManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNotesManagerStorage.class);

    private Path filePath;

    public JsonNotesManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getNotesManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyNotes> readNotesManager() throws DataConversionException, IOException {
        return readNotesManager(filePath);
    }

    /**
     * Similar to {@link #readNotesManager()}
     */
    @Override
    public Optional<ReadOnlyNotes> readNotesManager(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableNotesManager> jsonNotesManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableNotesManager.class);
        if (!jsonNotesManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNotesManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveNotesManager(ReadOnlyNotes notes) throws IOException {
        saveNotesManager(notes, filePath);
    }

    /**
     * Similar to {@link #saveNotesManager(ReadOnlyNotes)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveNotesManager(ReadOnlyNotes notes, Path filePath) throws IOException {
        requireNonNull(notes);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNotesManager(notes), filePath);
    }
}
