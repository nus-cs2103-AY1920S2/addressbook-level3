package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.UserPrefs;

/**
 * API of the Storage component.
 */
public interface Storage extends PersonAmountStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPersonAmountBookFilePath();

    @Override
    Optional<ReadOnlyPersonAmountBook> readPersonAmountBook() throws DataConversionException, IOException;

    @Override
    void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook) throws IOException;

}
