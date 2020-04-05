package seedu.address.storage.notes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.notes.ReadOnlyNotes;

/**
 * Represents a storage for {@link seedu.address.model.notes.NotesManager}.
 */
public interface NotesManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNotesManagerFilePath();

    /**
     * Returns NotesManager data as a {@link ReadOnlyNotes}
     * Retuns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage/
     */
    Optional<ReadOnlyNotes> readNotesManager() throws DataConversionException, IOException;

    /**
     * @see #getNotesManagerFilePath()
     */
    Optional<ReadOnlyNotes> readNotesManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyNotes} to the storage
     * @param notes cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNotesManager(ReadOnlyNotes notes) throws IOException;


    /**
     * @see #saveNotesManager(ReadOnlyNotes) ()
     */
    void saveNotesManager(ReadOnlyNotes notes, Path filePath) throws IOException;

}
