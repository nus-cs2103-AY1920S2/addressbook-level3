package seedu.address.model.hirelah.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.hirelah.AttributeList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class AttributeStorage {
    private final Path path;
    private static final Logger logger = LogsCenter.getLogger(AttributeStorage.class);

    public AttributeStorage(Path newPath) {
        this.path = newPath;
    }

    public Path getPath() {
        return this.path;
    }

    /**
     * reads the data from the current Path to
     * retrieve all the information regarding Attribute.
     * @return Optional<AttributeList>
     * @throws DataConversionException
     */
    public Optional<AttributeList> readAttribute(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableAttributes> jsonAttribute = JsonUtil.readJsonFile(
                filePath, JsonSerializableAttributes.class);
        if (jsonAttribute.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonAttribute.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Save the information of the Attribute
     * @param  source of the data. Cannot be null.
     */
    public void saveAttributes(AttributeList source) throws IOException, IllegalValueException {
        requireNonNull(source);
        requireNonNull(path);
        FileUtil.createIfMissing(path);
        JsonUtil.saveJsonFile(new JsonSerializableAttributes(source), path);
    }
}
