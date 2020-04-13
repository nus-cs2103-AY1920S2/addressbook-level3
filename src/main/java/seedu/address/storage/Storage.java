package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.ReadOnlyStudent;

/**
 * API of the Storage component
 */
public interface Storage extends StudentStorage, UserPrefsStorage, ConsultStorage,
    TutorialStorage, ModStorage, ReminderStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getStudentTAbleFilePath();

    @Override
    Optional<ReadOnlyStudent> readStudentTAble() throws DataConversionException, IOException;

    @Override
    void saveStudentTAble(ReadOnlyStudent studentTAble) throws IOException;

}
