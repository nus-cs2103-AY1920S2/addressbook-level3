package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ProfileList;

//@@author chanckben
/**
 * A class to access ProfileList data stored as a json file on the hard disk.
 */
public class JsonProfileListStorage implements ProfileListStorage {

    private Path filePath;

    public JsonProfileListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getProfileListFilePath() {
        return filePath;
    }

    public Optional<ProfileList> readProfileList() throws DataConversionException {
        return readProfileList(filePath);
    }

    /**
     * Similar to {@link #readProfileList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ProfileList> readProfileList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableProfileList> jsonProfileList = JsonUtil.readJsonFile(
                filePath, JsonSerializableProfileList.class);
        if (!jsonProfileList.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonProfileList.get().toModelType());
        } catch (IllegalValueException e) {
            throw new DataConversionException(e);
        }
    }

    public void saveProfileList(ProfileList profileList) throws IOException {
        saveProfileList(profileList, filePath);
    }

    /**
     * Similar to {@link #saveProfileList(ProfileList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveProfileList(ProfileList profileList, Path filePath) throws IOException {
        requireNonNull(profileList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableProfileList(profileList), filePath);
    }
}
