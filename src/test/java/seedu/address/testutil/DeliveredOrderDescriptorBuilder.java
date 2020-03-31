package seedu.address.testutil;

import seedu.address.logic.commands.DeliveredCommand;
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
 * A utility class to help with building EditParcelDescriptor objects.
 */
public class DeliveredOrderDescriptorBuilder {

    private DeliveredCommand.DeliveredOrderDescriptor descriptor;

    public DeliveredOrderDescriptorBuilder() {
        descriptor = new DeliveredCommand.DeliveredOrderDescriptor();
    }

    public DeliveredOrderDescriptorBuilder(DeliveredCommand.DeliveredOrderDescriptor descriptor) {
        this.descriptor = new DeliveredCommand.DeliveredOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code DoneOrderDescriptor} with fields containing {@code order}'s details
     */
    public DeliveredOrderDescriptorBuilder(Order order) {
        descriptor = new DeliveredCommand.DeliveredOrderDescriptor();
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
     * Sets the {@code Comment} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }

    /**
     * Sets the {@code TransactionId} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withTid(String tid) {
        descriptor.setTid(new TransactionId(tid));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code TimeStamp} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withTimeStamp(String timeStamp) {
        descriptor.setTimeStamp(new TimeStamp(timeStamp));
        return this;
    }

    /**
     * Sets the {@code Warehouse} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withWarehouse(String warehouseLocation) {
        descriptor.setWarehouse(new Warehouse(warehouseLocation));
        return this;
    }

    /**
     * Sets the {@code CashOnDelivery} of the {@code EditParcelDescriptor} that we are building.
     */
    public DeliveredOrderDescriptorBuilder withCash(String cash) {
        descriptor.setCash(new CashOnDelivery(cash));
        return this;
    }

    /**
     * Sets the {@code TypeOfItem} of the {@code EditParcelDescriptor} that we are building.
     *
     */
    public DeliveredOrderDescriptorBuilder withItemType(String item) {
        descriptor.setItemType(new TypeOfItem(item));
        return this;
    }

    public DeliveredCommand.DeliveredOrderDescriptor build() {
        return descriptor;
    }
}
