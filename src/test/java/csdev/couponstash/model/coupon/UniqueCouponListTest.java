package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.CommandTestUtil;
import csdev.couponstash.model.coupon.exceptions.CouponNotFoundException;
import csdev.couponstash.model.coupon.exceptions.DuplicateCouponException;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.TypicalCoupons;

public class UniqueCouponListTest {

    private final UniqueCouponList uniqueCouponList = new UniqueCouponList();

    @Test
    public void contains_nullCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.contains(null));
    }

    @Test
    public void contains_couponNotInList_returnsFalse() {
        assertFalse(uniqueCouponList.contains(TypicalCoupons.ALICE));
    }

    @Test
    public void contains_couponInList_returnsTrue() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        assertTrue(uniqueCouponList.contains(TypicalCoupons.ALICE));
    }

    @Test
    public void contains_couponWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        Coupon editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCouponList.contains(editedAlice));
    }

    @Test
    public void add_nullCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.add(null));
    }

    @Test
    public void add_duplicateCoupon_throwsDuplicateCouponException() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        assertThrows(DuplicateCouponException.class, () -> uniqueCouponList.add(TypicalCoupons.ALICE));
    }

    @Test
    public void setCoupon_nullTargetCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.setCoupon(null, TypicalCoupons.ALICE));
    }

    @Test
    public void setCoupon_nullEditedCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.setCoupon(TypicalCoupons.ALICE, null));
    }

    @Test
    public void setCoupon_targetCouponNotInList_throwsCouponNotFoundException() {
        assertThrows(CouponNotFoundException.class, () ->
                uniqueCouponList.setCoupon(TypicalCoupons.ALICE, TypicalCoupons.ALICE));
    }

    @Test
    public void setCoupon_editedCouponIsSameCoupon_success() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        uniqueCouponList.setCoupon(TypicalCoupons.ALICE, TypicalCoupons.ALICE);
        UniqueCouponList expectedUniqueCouponList = new UniqueCouponList();
        expectedUniqueCouponList.add(TypicalCoupons.ALICE);
        assertEquals(expectedUniqueCouponList, uniqueCouponList);
    }

    @Test
    public void setCoupon_editedCouponHasSameIdentity_success() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        Coupon editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueCouponList.setCoupon(TypicalCoupons.ALICE, editedAlice);
        UniqueCouponList expectedUniqueCouponList = new UniqueCouponList();
        expectedUniqueCouponList.add(editedAlice);
        assertEquals(expectedUniqueCouponList, uniqueCouponList);
    }

    @Test
    public void setCoupon_editedCouponHasDifferentIdentity_success() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        uniqueCouponList.setCoupon(TypicalCoupons.ALICE, TypicalCoupons.BOB);
        UniqueCouponList expectedUniqueCouponList = new UniqueCouponList();
        expectedUniqueCouponList.add(TypicalCoupons.BOB);
        assertEquals(expectedUniqueCouponList, uniqueCouponList);
    }

    @Test
    public void setCoupon_editedCouponHasNonUniqueIdentity_throwsDuplicateCouponException() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        uniqueCouponList.add(TypicalCoupons.BOB);
        assertThrows(DuplicateCouponException.class, () ->
                uniqueCouponList.setCoupon(TypicalCoupons.ALICE, TypicalCoupons.BOB));
    }

    @Test
    public void remove_nullCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.remove(null));
    }

    @Test
    public void remove_couponDoesNotExist_throwsCouponNotFoundException() {
        assertThrows(CouponNotFoundException.class, () -> uniqueCouponList.remove(TypicalCoupons.ALICE));
    }

    @Test
    public void remove_existingCoupon_removesCoupon() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        uniqueCouponList.remove(TypicalCoupons.ALICE);
        UniqueCouponList expectedUniqueCouponList = new UniqueCouponList();
        assertEquals(expectedUniqueCouponList, uniqueCouponList);
    }

    @Test
    public void setCoupons_nullUniqueCouponList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.setCoupons((UniqueCouponList) null));
    }

    @Test
    public void setCoupons_uniqueCouponList_replacesOwnListWithProvidedUniqueCouponList() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        UniqueCouponList expectedUniqueCouponList = new UniqueCouponList();
        expectedUniqueCouponList.add(TypicalCoupons.BOB);
        uniqueCouponList.setCoupons(expectedUniqueCouponList);
        assertEquals(expectedUniqueCouponList, uniqueCouponList);
    }

    @Test
    public void setCoupons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCouponList.setCoupons((List<Coupon>) null));
    }

    @Test
    public void setCoupons_list_replacesOwnListWithProvidedList() {
        uniqueCouponList.add(TypicalCoupons.ALICE);
        List<Coupon> couponList = Collections.singletonList(TypicalCoupons.BOB);
        uniqueCouponList.setCoupons(couponList);
        UniqueCouponList expectedUniqueCouponList = new UniqueCouponList();
        expectedUniqueCouponList.add(TypicalCoupons.BOB);
        assertEquals(expectedUniqueCouponList, uniqueCouponList);
    }

    @Test
    public void setCoupons_listWithDuplicateCoupons_throwsDuplicateCouponException() {
        List<Coupon> listWithDuplicateCoupons = Arrays.asList(TypicalCoupons.ALICE, TypicalCoupons.ALICE);
        assertThrows(DuplicateCouponException.class, () -> uniqueCouponList.setCoupons(listWithDuplicateCoupons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCouponList.asUnmodifiableObservableList().remove(0));
    }
}
