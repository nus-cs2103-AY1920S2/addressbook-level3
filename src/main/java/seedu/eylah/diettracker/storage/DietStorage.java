package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.storage.Storage;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;

/**
 * API of the Storage component
 */
public interface DietStorage extends FoodBookStorage, Storage {

    @Override
    Path getFoodBookFilePath();

    @Override
    Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException, IOException;

    @Override
    void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException;

}
