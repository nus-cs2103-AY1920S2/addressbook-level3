package csdev.couponstash.storage;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static csdev.couponstash.testutil.TypicalCoupons.ALICE;
import static csdev.couponstash.testutil.TypicalCoupons.HOON;
import static csdev.couponstash.testutil.TypicalCoupons.IDA;
import static csdev.couponstash.testutil.TypicalCoupons.getTypicalCouponStash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import csdev.couponstash.commons.exceptions.DataConversionException;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;

public class JsonCouponStashStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCouponStashStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCouponStash_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCouponStash(null));
    }

    private java.util.Optional<ReadOnlyCouponStash> readCouponStash(String filePath) throws Exception {
        return new JsonCouponStashStorage(Paths.get(filePath)).readCouponStash(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCouponStash("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCouponStash("notJsonFormatCouponStash.json"));
    }

    @Test
    public void readCouponStash_invalidCouponCouponStash_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCouponStash("invalidCouponCouponStash.json"));
    }

    @Test
    public void readCouponStash_invalidAndValidCouponCouponStash_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCouponStash("invalidAndValidCouponCouponStash.json"));
    }

    @Test
    public void readAndSaveCouponStash_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCouponStash.json");
        CouponStash original = getTypicalCouponStash();
        JsonCouponStashStorage jsonCouponStashStorage = new JsonCouponStashStorage(filePath);

        // Save in new file and read back
        jsonCouponStashStorage.saveCouponStash(original, filePath);
        ReadOnlyCouponStash readBack = jsonCouponStashStorage.readCouponStash(filePath).get();
        assertEquals(original, new CouponStash(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addCoupon(HOON);
        original.removeCoupon(ALICE);
        jsonCouponStashStorage.saveCouponStash(original, filePath);
        readBack = jsonCouponStashStorage.readCouponStash(filePath).get();
        assertEquals(original, new CouponStash(readBack));

        // Save and read without specifying file path
        original.addCoupon(IDA);
        jsonCouponStashStorage.saveCouponStash(original); // file path not specified
        readBack = jsonCouponStashStorage.readCouponStash().get(); // file path not specified
        assertEquals(original, new CouponStash(readBack));

    }

    @Test
    public void saveCouponStash_nullCouponStash_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCouponStash(null, "SomeFile.json"));
    }

    /**
     * Saves {@code couponStash} at the specified {@code filePath}.
     */
    private void saveCouponStash(ReadOnlyCouponStash couponStash, String filePath) {
        try {
            new JsonCouponStashStorage(Paths.get(filePath))
                    .saveCouponStash(couponStash, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCouponStash_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCouponStash(new CouponStash(), null));
    }
}
