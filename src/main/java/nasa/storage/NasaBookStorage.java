package nasa.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import nasa.commons.exceptions.DataConversionException;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.UniqueModuleList;

/**
 * Represents a storage for {@link nasa.model.NasaBook}.
 */
public interface NasaBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNasaBookFilePath();

    Path getHistoryBookFilePath();

    Path getUiHistoryBookFilePath();

    /**
     * Returns NASABook data as a {@link ReadOnlyNasaBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyNasaBook> readNasaBook() throws DataConversionException, IOException;

    /**
     * @see #getNasaBookFilePath()
     */
    Optional<ReadOnlyNasaBook> readNasaBook(Path filePath) throws DataConversionException, IOException;

    Optional<ReadOnlyHistory> readHistoryBook() throws DataConversionException, IOException;

    Optional<ReadOnlyHistory> readHistoryBook(Path filePath) throws DataConversionException, IOException;

    Optional<ReadOnlyHistory> readUiHistoryBook() throws DataConversionException, IOException;

    Optional<ReadOnlyHistory> readUiHistoryBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyNasaBook} to the storage.
     * @param nasaBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNasaBook(ReadOnlyNasaBook nasaBook) throws IOException;
    /**
     * @see #saveNasaBook(ReadOnlyNasaBook)
     */
    void saveNasaBook(ReadOnlyNasaBook nasaBook, Path filePath) throws IOException;

    void saveUltimate(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                      ReadOnlyHistory<String> uiHistoryBook) throws IOException;

    void saveUltimate(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                      ReadOnlyHistory<String> uiHistoryBook, Path filePathOne,
                      Path filePathTwo, Path filePathThree) throws IOException;
}
