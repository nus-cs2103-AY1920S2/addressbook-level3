package seedu.recipe.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.ReadOnlyQuoteBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.ReadOnlyUserPrefs;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.storage.achievement.QuoteBookStorage;
import seedu.recipe.storage.cooked.CookedRecordBookStorage;
import seedu.recipe.storage.plan.PlannedBookStorage;
import seedu.recipe.storage.recipe.RecipeBookStorage;
import seedu.recipe.storage.userpref.UserPrefsStorage;

/**
 * API of the Storage component
 */

public interface Storage extends RecipeBookStorage, CookedRecordBookStorage, PlannedBookStorage,
        QuoteBookStorage, UserPrefsStorage {

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

    // ====== Achievement feature ======
    @Override
    Path getQuoteBookFilePath();

    @Override
    Optional<ReadOnlyQuoteBook> readQuoteBook() throws DataConversionException, IOException;

    @Override
    void saveQuoteBook(ReadOnlyQuoteBook quoteBook) throws IOException;

}
