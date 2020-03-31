package seedu.address.model.hirelah.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.hirelah.AttributeList;

/**
 * AttributeStorage containing the file path
 * to store the attributes objects.
 */
public class AttributeStorage {
    private static final Logger logger = LogsCenter.getLogger(AttributeStorage.class);
    private final Path path;

    /**
     * constructor to initialise the Storage
     * @param newPath path
     */
    public AttributeStorage(Path newPath) {
        this.path = newPath;
    }

    /**
     * get the path of the attribute directory
     * @return path
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * reads the data from the current Path to
     * retrieve all the information regarding Attribute.
     * @return OptionalAttributeList
     * @throws DataConversionException error when processing the fille
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
