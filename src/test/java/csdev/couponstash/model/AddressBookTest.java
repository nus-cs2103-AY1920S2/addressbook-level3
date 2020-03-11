package csdev.couponstash.model;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static csdev.couponstash.testutil.TypicalCoupons.ALICE;
import static csdev.couponstash.testutil.TypicalCoupons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import csdev.couponstash.testutil.CouponBuilder;
import org.junit.jupiter.api.Test;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.exceptions.DuplicateCouponException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCouponList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateCoupons_throwsDuplicateCouponException() {
        // Two coupons with the same identity fields
        Coupon editedAlice = new CouponBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Coupon> newCoupons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCoupons);

        assertThrows(DuplicateCouponException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasCoupon_nullCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasCoupon(null));
    }

    @Test
    public void hasCoupon_couponNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCoupon(ALICE));
    }

    @Test
    public void hasCoupon_couponInAddressBook_returnsTrue() {
        addressBook.addCoupon(ALICE);
        assertTrue(addressBook.hasCoupon(ALICE));
    }

    @Test
    public void hasCoupon_couponWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCoupon(ALICE);
        Coupon editedAlice = new CouponBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasCoupon(editedAlice));
    }

    @Test
    public void getCouponList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getCouponList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose coupons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Coupon> coupons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Coupon> coupons) {
            this.coupons.setAll(coupons);
        }

        @Override
        public ObservableList<Coupon> getCouponList() {
            return coupons;
        }
    }

}
