package fithelper.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.DataConversionException;
import fithelper.commons.util.FileUtil;
import fithelper.commons.util.JsonUtil;
import fithelper.model.ReadOnlyUserProfile;

/**
 * A class to access UserProfile data stored as a json file on the hard disk.
 */
public class JsonUserProfileStorage implements UserProfileStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserProfileStorage.class);

    private Path filePath;

    public JsonUserProfileStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserProfilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException {
        return readUserProfile(filePath);
    }

    /**
     * Similar to {@link #readUserProfile()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableUserProfile> jsonUserProfile = JsonUtil.readJsonFile(
                filePath, JsonSerializableUserProfile.class);
        if (!jsonUserProfile.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonUserProfile.get().toModelType());
        } catch (IllegalArgumentException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        } catch (Exception i) {
            logger.info("Exception happened while reading data" + i.getMessage());
            throw new DataConversionException(i);
        }
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile userProfile) throws IOException {
        logger.fine("Attempting to write user profile to data file: " + filePath);
        saveUserProfile(userProfile, filePath);
    }

    /**
     * Similar to {@link #saveUserProfile(ReadOnlyUserProfile)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUserProfile(ReadOnlyUserProfile userProfile, Path filePath) throws IOException {
        requireNonNull(userProfile);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUserProfile(userProfile), filePath);
    }

}
