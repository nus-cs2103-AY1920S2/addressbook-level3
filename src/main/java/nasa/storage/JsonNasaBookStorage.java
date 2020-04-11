package nasa.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import nasa.commons.core.LogsCenter;
import nasa.commons.exceptions.DataConversionException;
import nasa.commons.exceptions.IllegalValueException;
import nasa.commons.util.FileUtil;
import nasa.commons.util.JsonUtil;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.UniqueModuleList;

/**
 * A class to access NasaBook data stored as a json file on the hard disk.
 */
public class JsonNasaBookStorage implements NasaBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNasaBookStorage.class);

    private Path filePathOne;
    private Path filePathTwo;
    private Path filePathThree;

    public JsonNasaBookStorage(Path filePathOne, Path filePathTwo, Path filePathThree) {
        this.filePathOne = filePathOne;
        this.filePathTwo = filePathTwo;
        this.filePathThree = filePathThree;
    }

    public Path getNasaBookFilePath() {
        return filePathOne;
    }

    public Path getHistoryBookFilePath() {
        return filePathTwo;
    }

    public Path getUiHistoryBookFilePath() {
        return filePathThree;
    }

    @Override
    public Optional<ReadOnlyNasaBook> readNasaBook() throws DataConversionException {
        return readNasaBook(filePathOne);
    }

    /**
     * Similar to {@link #readNasaBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyNasaBook> readNasaBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableNasaBook> jsonNasaBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableNasaBook.class);
        if (!jsonNasaBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNasaBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public Optional<ReadOnlyHistory> readHistoryBook() throws DataConversionException {
        return readHistoryBook(filePathTwo);
    }

    /**
     * Similar to {@link #readHistoryBook()}.
     * @param filePath
     * @return
     * @throws DataConversionException
     */
    public Optional<ReadOnlyHistory> readHistoryBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonAdaptedHistory> jsonHistoryBook = JsonUtil.readJsonFile(
                filePath, JsonAdaptedHistory.class);
        if (!jsonHistoryBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHistoryBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public Optional<ReadOnlyHistory> readUiHistoryBook() throws DataConversionException {
        return readUiHistoryBook(filePathThree);
    }

    /**
     * Similar to {@link #readUiHistoryBook()}.
     * @param filePath
     * @return
     * @throws DataConversionException
     */
    public Optional<ReadOnlyHistory> readUiHistoryBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonAdaptedUiHistory> jsonHistoryBook = JsonUtil.readJsonFile(
                filePath, JsonAdaptedUiHistory.class);
        if (!jsonHistoryBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHistoryBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveNasaBook(ReadOnlyNasaBook nasaBook) throws IOException {
        saveNasaBook(nasaBook, filePathOne);
    }

    /**
     * Similar to {@link #saveNasaBook(ReadOnlyNasaBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveNasaBook(ReadOnlyNasaBook nasaBook, Path filePath) throws IOException {
        requireNonNull(nasaBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNasaBook(nasaBook), filePath);
    }

    @Override
    public void saveUltimate(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                             ReadOnlyHistory<String> uiHistoryBook)
            throws IOException {
        saveUltimate(nasaBook, historyBook, uiHistoryBook, filePathOne, filePathTwo, filePathThree);
    }

    /**
     * Save data into json files.
     * @param nasaBook
     * @param historyBook
     * @param uiHistoryBook
     * @param filePathOne
     * @param filePathTwo
     * @param filePathThree
     * @throws IOException
     */
    public void saveUltimate(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                             ReadOnlyHistory<String> uiHistoryBook, Path filePathOne,
                             Path filePathTwo, Path filePathThree) throws IOException {
        requireNonNull(nasaBook);
        requireNonNull(filePathOne);
        requireNonNull(filePathTwo);
        requireNonNull(filePathThree);

        FileUtil.createIfMissing(filePathOne);
        FileUtil.createIfMissing(filePathTwo);
        FileUtil.createIfMissing(filePathThree);
        JsonUtil.saveJsonFile(new JsonSerializableNasaBook(nasaBook), filePathOne);
        JsonUtil.saveJsonFile(new JsonAdaptedHistory(historyBook), filePathTwo);
        JsonUtil.saveJsonFile(new JsonAdaptedUiHistory(uiHistoryBook), filePathThree);
    }
}

