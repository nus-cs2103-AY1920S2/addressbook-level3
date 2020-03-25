package cookbuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cookbuddy.commons.exceptions.DataConversionException;
import cookbuddy.model.ReadOnlyRecipeBook;
import cookbuddy.model.ReadOnlyUserPrefs;
import cookbuddy.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RecipeBookStorage, UserPrefsStorage {

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

}
