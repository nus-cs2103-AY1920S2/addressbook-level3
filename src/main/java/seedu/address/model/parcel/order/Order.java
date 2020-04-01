package seedu.address.model.parcel.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;


/**
 * Represents a Order in the order book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order extends Parcel {

    // Identity fields
    private final TransactionId tid;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final CashOnDelivery cod;
    private final Address address;
    private final TimeStamp timestamp;
    private final Warehouse warehouse;
    private final Comment comment;
    private final TypeOfItem itemType;
    private boolean deliveryStatus;
    /**
     * Every field must be present and not null.
     */
    public Order(TransactionId tid, Name name, Phone phone, Email email, Address address, TimeStamp timestamp,
                 Warehouse warehouse, CashOnDelivery cod, Comment comment, TypeOfItem itemType) {
        requireAllNonNull(tid, name, phone, email, address, timestamp, warehouse, cod, comment, itemType);
        this.tid = tid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.timestamp = timestamp;
        this.warehouse = warehouse;
        this.cod = cod;
        this.comment = comment;
        this.itemType = itemType;
        this.deliveryStatus = false;
    }

    public TransactionId getTid() {
        return tid;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public TimeStamp getTimestamp() {
        return timestamp;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public CashOnDelivery getCash() {
        return cod;
    }

    public Comment getComment() {
        return comment;
    }

    public TypeOfItem getItemType() {
        return itemType;
    }

    public boolean isDelivered() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(boolean status) {
        deliveryStatus = status;
    }

    /**
     * Returns true if both orders of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameParcel(Parcel otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getTid().equals(getTid())
                && otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return otherOrder.getTid().equals(getTid())
                && otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getEmail().equals(getEmail())
                && otherOrder.getAddress().equals(getAddress())
                && otherOrder.getTimestamp().equals(getTimestamp())
                && otherOrder.getWarehouse().equals(getWarehouse())
                && otherOrder.getComment().equals(getComment())
                && otherOrder.getCash().equals(getCash())
                && otherOrder.getItemType().equals(getItemType())
                && (otherOrder.isDelivered() == isDelivered());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tid, name, phone, email, address, timestamp, warehouse, cod, comment, itemType);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Transaction ID: ")
                .append(getTid())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Delivery Date & Time: ")
                .append(getTimestamp())
                .append(" Warehouse: ")
                .append(getWarehouse())
                .append(" Cash On Delivery: ")
                .append(getCash())
                .append(" Comment: ")
                .append(getComment())
                .append(" Item Type: ")
                .append(getItemType());
        if (this.isDelivered()) {
            builder.append(" Delivery Status: ").append("Not Delivered to Customer: " + getWarehouse());
        } else {
            builder.append(" Delivery Status: ").append("Delivered to Customer: " + getWarehouse());
        }
        return builder.toString();
    }
}
