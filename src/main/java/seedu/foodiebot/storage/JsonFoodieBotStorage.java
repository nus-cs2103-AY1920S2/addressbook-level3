package seedu.foodiebot.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.foodiebot.commons.core.LogsCenter;
import seedu.foodiebot.commons.exceptions.DataConversionException;
import seedu.foodiebot.commons.exceptions.IllegalValueException;
import seedu.foodiebot.commons.util.FileUtil;
import seedu.foodiebot.commons.util.JsonUtil;
import seedu.foodiebot.model.ReadOnlyFoodieBot;
import seedu.foodiebot.model.UserPrefs;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonFoodieBotStorage implements FoodieBotStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFoodieBotStorage.class);

    private Path canteensFilePath;
    private Path stallsFilePath;

    public JsonFoodieBotStorage(Path filePath) {
        this.canteensFilePath = filePath;
    }


    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getCanteensFilePath() {
        return canteensFilePath;
    }

    @Override
    public Path getStallsFilePath() {
        return stallsFilePath;
    }

    @Override
    public Optional<ReadOnlyFoodieBot> readFoodieBot(String modelType) throws DataConversionException, IOException {
        return readFoodieBot(getModelFilePath(modelType), modelType);
    }

    /**
     * Similar to {@link #readFoodieBot(String modelType)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFoodieBot> readFoodieBot(Path filePath, String modelType) throws DataConversionException {
        requireNonNull(filePath);

        switch (modelType) {
        case "Canteen":
            Optional<JsonSerializableFoodieBot> jsonAddressBook = JsonUtil.readJsonFile(filePath,
                JsonSerializableFoodieBot.class);
            if (!jsonAddressBook.isPresent()) {
                return Optional.empty();
            }

            try {
                return Optional.of(jsonAddressBook.get().toModelType());
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
                throw new DataConversionException(ive);
            }
        case "Stall":
            Optional<JsonSerializableStall> json2 = JsonUtil.readJsonFile(filePath, JsonSerializableStall.class);
            if (!json2.isPresent()) {
                return Optional.empty();
            }

            try {
                return Optional.of(json2.get().toModelType());
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
                throw new DataConversionException(ive);
            }
        default:
            break;

        }
        return Optional.empty();
    }


    public Path getModelFilePath(String modelType) {
        Path path = null;
        switch (modelType) {
        case "Canteen":
            return new UserPrefs().getFoodieBotFilePath();
        case "Stall":
            return new UserPrefs().getStallsFilePath();
        default:
            break;
        }
        return path;
    }

    /**
     * Saves the given {@link ReadOnlyFoodieBot} to the storage.
     *
     * @param foodieBot cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveFoodieBot(ReadOnlyFoodieBot foodieBot) throws IOException {
        saveFoodieBot(foodieBot, "Canteen");
    }

    /**
     * Save foodiebot according to the model supplied.
     **/
    @Override
    public void saveFoodieBot(ReadOnlyFoodieBot foodieBot, String modelType) throws IOException {
        saveFoodieBot(foodieBot, getModelFilePath(modelType), modelType);
    }

    /**
     * Similar to {@link #saveFoodieBot(ReadOnlyFoodieBot)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveFoodieBot(
        ReadOnlyFoodieBot foodieBot, Path filePath,
        String modelType) throws IOException {

        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        switch (modelType) {
        case "Canteen":
            requireNonNull(foodieBot);
            JsonUtil.saveJsonFile(new JsonSerializableFoodieBot(foodieBot), filePath);
            break;
        case "Stall":
            requireNonNull(foodieBot);
            JsonUtil.saveJsonFile(new JsonSerializableStall(foodieBot), filePath);
            break;
        default:
            requireNonNull(foodieBot);
            JsonUtil.saveJsonFile(new JsonSerializableFoodieBot(foodieBot), filePath);
            break;
        }


    }
}
