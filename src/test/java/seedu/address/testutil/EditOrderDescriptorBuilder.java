package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.address.model.comment.Comment;
import seedu.address.model.order.Address;
import seedu.address.model.order.CashOnDelivery;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TransactionID;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.Warehouse;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditCommand.EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditCommand.EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditCommand.EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code order}'s details
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setName(order.getName());
        descriptor.setPhone(order.getPhone());
        descriptor.setAddress(order.getAddress());
        descriptor.setTimeStamp(order.getTimestamp());
        descriptor.setWarehouse(order.getWarehouse());
        descriptor.setComment(order.getComment());
        descriptor.setTags(order.getTags());
    }

    /**
     * Sets the {@code Comment} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }

    /**
     * Sets the {@code TransactionID} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withTID(String tid) {
        descriptor.setTID(new TransactionID(tid));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code TimeStamp} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withTimeStamp(String timeStamp) {
        descriptor.setTimeStamp(new TimeStamp(timeStamp));
        return this;
    }

    /**
     * Sets the {@code Warehouse} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withWarehouse(String warehouseLocation) {
        descriptor.setWarehouse(new Warehouse(warehouseLocation));
        return this;
    }

    /**
     * Sets the {@code CashOnDelivery} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withCash(String cash) {
        descriptor.setCash(new CashOnDelivery(cash));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditOrderDescriptor}
     * that we are building.
     */
    public EditOrderDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditOrderDescriptor build() {
        return descriptor;
    }
}
