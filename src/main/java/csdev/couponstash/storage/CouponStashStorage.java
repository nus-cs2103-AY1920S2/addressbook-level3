package csdev.couponstash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import csdev.couponstash.commons.exceptions.DataConversionException;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;

/**
 * Represents a storage for {@link CouponStash}.
 */
public interface CouponStashStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCouponStashFilePath();

    /**
     * Returns CouponStash data as a {@link ReadOnlyCouponStash}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCouponStash> readCouponStash() throws DataConversionException, IOException;

    /**
     * @see #getCouponStashFilePath()
     */
    Optional<ReadOnlyCouponStash> readCouponStash(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCouponStash} to the storage.
     * @param couponStash cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCouponStash(ReadOnlyCouponStash couponStash) throws IOException;

    /**
     * @see #saveCouponStash(ReadOnlyCouponStash)
     */
    void saveCouponStash(ReadOnlyCouponStash couponStash, Path filePath) throws IOException;

}
