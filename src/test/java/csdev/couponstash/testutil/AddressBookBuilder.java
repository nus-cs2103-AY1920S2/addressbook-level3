package csdev.couponstash.testutil;

import csdev.couponstash.model.AddressBook;
import csdev.couponstash.model.coupon.Coupon;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withCoupon("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Coupon} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withCoupon(Coupon coupon) {
        addressBook.addCoupon(coupon);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
