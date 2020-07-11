package csdev.couponstash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import csdev.couponstash.commons.exceptions.DataConversionException;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.ReadOnlyUserPrefs;
import csdev.couponstash.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CouponStashStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCouponStashFilePath();

    @Override
    Optional<ReadOnlyCouponStash> readCouponStash() throws DataConversionException, IOException;

    @Override
    void saveCouponStash(ReadOnlyCouponStash couponStash) throws IOException;

}
