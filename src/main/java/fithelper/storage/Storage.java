package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.ReadOnlyWeightRecords;

/**
 * API of the Storage component
 */
public interface Storage extends FitHelperStorage, UserPrefsStorage, UserProfileStorage, WeightRecordsStorage {

    @Override
    Path getFitHelperFilePath();

    @Override
    Optional<ReadOnlyFitHelper> readFitHelper() throws DataConversionException, IOException;

    @Override
    void saveFitHelper(ReadOnlyFitHelper fitHelper) throws IOException;

    // Methods related to user profile.
    @Override
    Path getUserProfilePath();

    @Override
    Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException;

    @Override
    void saveUserProfile(ReadOnlyUserProfile userProfile) throws IOException;

    // Methods related to weight records.
    @Override
    Path getWeightRecordsFilePath();

    @Override
    Optional<ReadOnlyWeightRecords> readWeightRecords() throws DataConversionException, IOException;

    @Override
    void saveWeightRecords(ReadOnlyWeightRecords weightRecords) throws IOException;

}
