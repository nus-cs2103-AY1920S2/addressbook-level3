package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyUserPrefs;
import seedu.eylah.diettracker.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FoodBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFoodBookFilePath();

    @Override
    Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException, IOException;

    @Override
    void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException;

}
