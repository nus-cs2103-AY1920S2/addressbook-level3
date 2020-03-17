package seedu.zerotoone.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.ReadOnlyExerciseList;
import seedu.zerotoone.model.ReadOnlyUserPrefs;
import seedu.zerotoone.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ExerciseListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getExerciseListFilePath();

    @Override
    Optional<ReadOnlyExerciseList> readExerciseList() throws DataConversionException, IOException;

    @Override
    void saveExerciseList(ReadOnlyExerciseList addressBook) throws IOException;

}
