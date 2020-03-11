package csdev.couponstash.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.AddressBook;
import csdev.couponstash.model.ReadOnlyAddressBook;
import csdev.couponstash.model.coupon.Coupon;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_COUPON = "Coupons list contains duplicate coupon(s).";

    private final List<JsonAdaptedCoupon> coupons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given coupons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("coupons") List<JsonAdaptedCoupon> coupons) {
        this.coupons.addAll(coupons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        coupons.addAll(source.getCouponList().stream().map(JsonAdaptedCoupon::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedCoupon jsonAdaptedCoupon : coupons) {
            Coupon coupon = jsonAdaptedCoupon.toModelType();
            if (addressBook.hasCoupon(coupon)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COUPON);
            }
            addressBook.addCoupon(coupon);
        }
        return addressBook;
    }

}
