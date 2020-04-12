package seedu.address.storage.storageProgress;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelProgress.Progress;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access ProgressAddressBook data stored as a json file on the hard disk.
 */
public class JsonProgressAddressBookStorage implements ProgressAddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(
            JsonProgressAddressBookStorage.class);

    private Path filePath;

    public JsonProgressAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getProgressAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBookGeneric<Progress>> readProgressAddressBook()
            throws DataConversionException {
        return readProgressAddressBook(filePath);
    }

    /**
     * Similar to {@link #readProgressAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBookGeneric<Progress>> readProgressAddressBook(Path filePath)
            throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonProgressSerializableAddressBook> jsonProgressAddressBook = JsonUtil.readJsonFile(
                filePath, JsonProgressSerializableAddressBook.class);
        if (!jsonProgressAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonProgressAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (CommandException ce) {
            logger.info("Illegal values found in " + filePath + ": " + ce.getMessage());
            throw new DataConversionException(ce);
        }
    }

    @Override
    public void saveProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> ProgressAddressBook)
            throws IOException {
        saveProgressAddressBook(ProgressAddressBook, filePath);
    }

    /**
     * Similar to {@link #saveProgressAddressBook(ReadOnlyAddressBookGeneric<Progress>)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> ProgressAddressBook, Path filePath)
            throws IOException {
        requireNonNull(ProgressAddressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonProgressSerializableAddressBook(ProgressAddressBook), filePath);
    }

}
