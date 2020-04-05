package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.storage.academics.AcademicsStorage;
import seedu.address.storage.admin.AdminStorage;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.notes.NotesManagerStorage;


/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, AcademicsStorage, NotesManagerStorage,
        UserPrefsStorage, AdminStorage, EventStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getAcademicsFilePath();

    @Override
    Optional<ReadOnlyAcademics> readAcademics() throws DataConversionException, IOException;

    @Override
    void saveAcademics(ReadOnlyAcademics academics) throws IOException;

    // ================ Event methods ==============================
    @Override
    Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException;

    @Override
    Path getEventHistoryFilePath();

    @Override
    void saveEvents(ReadOnlyEvents readOnlyEvents) throws IOException;

    // ================ Notes methods ==============================
    @Override
    Path getAdminFilePath();

    @Override
    Optional<ReadOnlyNotes> readNotesManager() throws DataConversionException, IOException;

    @Override
    void saveNotesManager(ReadOnlyNotes notes) throws IOException;

    @Override
    Path getNotesManagerFilePath();

    @Override
    Optional<ReadOnlyAdmin> readAdmin() throws DataConversionException, IOException;

    @Override
    void saveAdmin(ReadOnlyAdmin admin) throws IOException;
}
