package csdev.couponstash.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.exceptions.DataConversionException;
import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.commons.util.FileUtil;
import csdev.couponstash.commons.util.JsonUtil;
import csdev.couponstash.model.ReadOnlyCouponStash;

/**
 * A class to access CouponStash data stored as a json file on the hard disk.
 */
public class JsonCouponStashStorage implements CouponStashStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCouponStashStorage.class);

    private Path filePath;

    public JsonCouponStashStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCouponStashFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCouponStash> readCouponStash() throws DataConversionException {
        return readCouponStash(filePath);
    }

    /**
     * Similar to {@link #readCouponStash()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCouponStash> readCouponStash(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCouponStash> jsonCouponStash = JsonUtil.readJsonFile(
                filePath, JsonSerializableCouponStash.class);
        if (!jsonCouponStash.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCouponStash.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCouponStash(ReadOnlyCouponStash couponStash) throws IOException {
        saveCouponStash(couponStash, filePath);
    }

    /**
     * Similar to {@link #saveCouponStash(ReadOnlyCouponStash)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCouponStash(ReadOnlyCouponStash couponStash, Path filePath) throws IOException {
        requireNonNull(couponStash);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCouponStash(couponStash), filePath);
    }

}
