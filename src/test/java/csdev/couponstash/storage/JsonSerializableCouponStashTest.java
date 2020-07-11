package csdev.couponstash.storage;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.commons.util.JsonUtil;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.testutil.TypicalCoupons;

public class JsonSerializableCouponStashTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCouponStashTest");
    private static final Path TYPICAL_COUPONS_FILE = TEST_DATA_FOLDER.resolve("typicalCouponsCouponStash.json");
    private static final Path INVALID_COUPON_FILE = TEST_DATA_FOLDER.resolve("invalidCouponCouponStash.json");
    private static final Path DUPLICATE_COUPON_FILE = TEST_DATA_FOLDER.resolve("duplicateCouponCouponStash.json");

    @Test
    public void toModelType_typicalCouponsFile_success() throws Exception {
        JsonSerializableCouponStash dataFromFile = JsonUtil.readJsonFile(TYPICAL_COUPONS_FILE,
                JsonSerializableCouponStash.class).get();
        CouponStash couponStashFromFile = dataFromFile.toModelType();
        CouponStash typicalCouponsCouponStash = TypicalCoupons.getTypicalCouponStash();
        assertEquals(couponStashFromFile, typicalCouponsCouponStash);
    }
    //typicalCouponsCouponStash

    @Test
    public void toModelType_invalidCouponFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCouponStash dataFromFile = JsonUtil.readJsonFile(INVALID_COUPON_FILE,
                JsonSerializableCouponStash.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateCoupons_throwsIllegalValueException() throws Exception {
        JsonSerializableCouponStash dataFromFile = JsonUtil.readJsonFile(DUPLICATE_COUPON_FILE,
                JsonSerializableCouponStash.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCouponStash.MESSAGE_DUPLICATE_COUPON,
                dataFromFile::toModelType);
    }

}
