package csdev.couponstash.storage;

import static csdev.couponstash.storage.JsonAdaptedCoupon.MISSING_FIELD_MESSAGE_FORMAT;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static csdev.couponstash.testutil.TypicalCoupons.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.coupon.Archived;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.Savings;

public class JsonAdaptedCouponTest {
    private static final String INVALID_NAME = "R@chel";
    private static final JsonAdaptedSavings INVALID_SAVINGS =
            new JsonAdaptedSavings(null, null, null);
    private static final String INVALID_EXPIRY_DATE = "31-12-202";
    private static final String INVALID_START_DATE = "31-01-202";
    private static final String INVALID_USAGE = "-10";
    private static final String INVALID_LIMIT = "3a";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ARCHIVED = "archived";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PROMO_CODE = BENSON.getPromoCode().toString();
    private static final JsonAdaptedSavings VALID_SAVINGS =
            new JsonAdaptedSavings(BENSON.getSavingsForEachUse());
    private static final JsonAdaptedDssm VALID_TOTAL_SAVINGS =
            new JsonAdaptedDssm(BENSON.getSavingsMap());
    private static final String VALID_EXPIRY_DATE = BENSON.getExpiryDate().toString();
    private static final String VALID_START_DATE = BENSON.getStartDate().toString();
    private static final String VALID_USAGE = BENSON.getUsage().toString();
    private static final String VALID_LIMIT = BENSON.getLimit().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_REMIND_DATE = BENSON.getRemindDate().toString();
    private static final String VALID_CONDTIION = BENSON.getCondition().toString();
    private static final String VALID_ARCHIVED = BENSON.getArchived().toString();

    @Test
    public void toModelType_validCouponDetails_returnsCoupon() throws Exception {
        JsonAdaptedCoupon coupon = new JsonAdaptedCoupon(BENSON);
        assertEquals(BENSON, coupon.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(INVALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                        VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {

        JsonAdaptedCoupon coupon = new JsonAdaptedCoupon(null, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_nullPromoCode_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon = new JsonAdaptedCoupon(VALID_NAME, null, VALID_SAVINGS, VALID_EXPIRY_DATE,
                VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PromoCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidSavings_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, INVALID_SAVINGS, VALID_EXPIRY_DATE,
                        VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);

        String expectedMessage = Savings.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, INVALID_EXPIRY_DATE,
                        VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);

        String expectedMessage = ExpiryDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                        INVALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);

        String expectedMessage = StartDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidUsage_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                        VALID_START_DATE, INVALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);

        String expectedMessage = Usage.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidLimit_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                        VALID_START_DATE, VALID_USAGE, INVALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);

        String expectedMessage = Limit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                        VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, invalidTags,
                        VALID_REMIND_DATE, VALID_CONDTIION, VALID_ARCHIVED);


        assertThrows(IllegalValueException.class, coupon::toModelType);
    }

    @Test
    public void toModelType_invalidArchivedState_throwsIllegalValueException() {
        JsonAdaptedCoupon coupon =
                new JsonAdaptedCoupon(VALID_NAME, VALID_PROMO_CODE, VALID_SAVINGS, VALID_EXPIRY_DATE,
                        VALID_START_DATE, VALID_USAGE, VALID_LIMIT, VALID_TOTAL_SAVINGS, VALID_TAGS,
                        VALID_REMIND_DATE, VALID_CONDTIION, INVALID_ARCHIVED);
        String expectedMessage = Archived.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, coupon::toModelType);
    }

}
