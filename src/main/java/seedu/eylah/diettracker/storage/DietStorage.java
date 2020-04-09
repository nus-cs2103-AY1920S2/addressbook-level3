package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.storage.Storage;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;

/**
 * API of the Storage component
 */
public interface DietStorage extends MyselfStorage, FoodBookStorage, Storage {

    @Override
    Path getMyselfFilePath();

    @Override
    Path getFoodBookFilePath();

    @Override
    Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException, IOException;

    @Override
    void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException;

    @Override
    Optional<ReadOnlyMyself> readMyself() throws DataConversionException, IOException;

    @Override
    void saveMyself(ReadOnlyMyself myself) throws IOException;

}
