package seedu.recipe.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.ReadOnlyUserPrefs;
import seedu.recipe.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RecipeBookStorage, CookedRecordBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getRecipeBookFilePath();

    @Override
    Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException, IOException;

    @Override
    void saveRecipeBook(ReadOnlyRecipeBook recipeBook) throws IOException;

    @Override
    Path getCookedRecordFilePath();

    @Override
    Optional<ReadOnlyCookedRecordBook> readCookedRecord() throws DataConversionException, IOException;

    @Override
    void saveCookedRecord(ReadOnlyCookedRecordBook cookedRecord) throws IOException;

}
