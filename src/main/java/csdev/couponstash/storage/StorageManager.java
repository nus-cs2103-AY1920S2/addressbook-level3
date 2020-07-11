package csdev.couponstash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.exceptions.DataConversionException;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.ReadOnlyUserPrefs;
import csdev.couponstash.model.UserPrefs;

/**
 * Manages storage of CouponStash data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CouponStashStorage couponStashStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CouponStashStorage couponStashStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.couponStashStorage = couponStashStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CouponStash methods ==============================

    @Override
    public Path getCouponStashFilePath() {
        return couponStashStorage.getCouponStashFilePath();
    }

    @Override
    public Optional<ReadOnlyCouponStash> readCouponStash() throws DataConversionException, IOException {
        return readCouponStash(couponStashStorage.getCouponStashFilePath());
    }

    @Override
    public Optional<ReadOnlyCouponStash> readCouponStash(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return couponStashStorage.readCouponStash(filePath);
    }

    @Override
    public void saveCouponStash(ReadOnlyCouponStash couponStash) throws IOException {
        saveCouponStash(couponStash, couponStashStorage.getCouponStashFilePath());
    }

    @Override
    public void saveCouponStash(ReadOnlyCouponStash couponStash, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        couponStashStorage.saveCouponStash(couponStash, filePath);
    }

}
