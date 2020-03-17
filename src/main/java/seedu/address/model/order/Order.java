package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.comment.Comment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Order in the order book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final TimeStamp timestamp;
    private final Warehouse warehouse;
    private final Comment comment;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Order(Name name, Phone phone, Email email, Address address, TimeStamp timestamp, Warehouse warehouse,
                 Comment comment, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, timestamp, warehouse, comment, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.timestamp = timestamp;
        this.warehouse = warehouse;
        this.comment = comment;
        this.tags.addAll(tags);
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

    public Comment getComment() {
        return comment;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both orders of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getName().equals(getName())
                && (otherOrder.getPhone().equals(getPhone()) || otherOrder.getEmail().equals(getEmail()));
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
        return otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getEmail().equals(getEmail())
                && otherOrder.getAddress().equals(getAddress())
                && otherOrder.getTimestamp().equals(getTimestamp())
                && otherOrder.getWarehouse().equals(getWarehouse())
                && otherOrder.getComment().equals(getComment())
                && otherOrder.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, timestamp, warehouse, comment, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
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
                .append(" Comment: ")
                .append(getComment())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
