package seedu.address.testutil;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.DoneCommand.DoneOrderDescriptor;
import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.CashOnDelivery;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class DoneOrderDescriptorBuilder {

    private DoneCommand.DoneOrderDescriptor descriptor;

    public DoneOrderDescriptorBuilder() {
        descriptor = new DoneCommand.DoneOrderDescriptor();
    }

    public DoneOrderDescriptorBuilder(DoneCommand.DoneOrderDescriptor descriptor) {
        this.descriptor = new DoneOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code DoneOrderDescriptor} with fields containing {@code order}'s details
     */
    public DoneOrderDescriptorBuilder(Order order) {
        descriptor = new DoneOrderDescriptor();
        descriptor.setTid(order.getTid());
        descriptor.setName(order.getName());
        descriptor.setPhone(order.getPhone());
        descriptor.setEmail(order.getEmail());
        descriptor.setAddress(order.getAddress());
        descriptor.setTimeStamp(order.getTimestamp());
        descriptor.setWarehouse(order.getWarehouse());
        descriptor.setCash(order.getCash());
        descriptor.setComment(order.getComment());
        descriptor.setItemType(order.getItemType());
        descriptor.setDeliveryStatus(order.isDelivered());
    }

    /**
     * Sets the {@code Comment} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }

    /**
     * Sets the {@code TransactionId} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withTid(String tid) {
        descriptor.setTid(new TransactionId(tid));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code TimeStamp} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withTimeStamp(String timeStamp) {
        descriptor.setTimeStamp(new TimeStamp(timeStamp));
        return this;
    }

    /**
     * Sets the {@code Warehouse} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withWarehouse(String warehouseLocation) {
        descriptor.setWarehouse(new Warehouse(warehouseLocation));
        return this;
    }

    /**
     * Sets the {@code CashOnDelivery} of the {@code EditOrderDescriptor} that we are building.
     */
    public DoneOrderDescriptorBuilder withCash(String cash) {
        descriptor.setCash(new CashOnDelivery(cash));
        return this;
    }

    /**
     * Sets the {@code TypeOfItem} of the {@code EditOrderDescriptor} that we are building.
     *
     */
    public DoneOrderDescriptorBuilder withItemType(String item) {
        descriptor.setItemType(new TypeOfItem(item));
        return this;
    }

    public DoneCommand.DoneOrderDescriptor build() {
        return descriptor;
    }
}
