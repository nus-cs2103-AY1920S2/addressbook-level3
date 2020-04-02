package seedu.recipe.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.ReadOnlyUserPrefs;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.storage.plan.PlannedBookStorage;

/**
 * API of the Storage component
 */

public interface Storage extends RecipeBookStorage, CookedRecordBookStorage, PlannedBookStorage, UserPrefsStorage {

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

    // ====== CookedRecords feature ======
    @Override
    Path getCookedRecordBookFilePath();

    @Override
    Optional<ReadOnlyCookedRecordBook> readCookedRecordBook() throws DataConversionException, IOException;

    @Override
    void saveCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook) throws IOException;

    // ====== Planning feature ======
    @Override
    Path getPlannedBookFilePath();

    @Override
    Optional<ReadOnlyPlannedBook> readPlannedBook() throws DataConversionException, IOException;

    void savePlannedBook(ReadOnlyPlannedBook plannedBook) throws IOException;

}
