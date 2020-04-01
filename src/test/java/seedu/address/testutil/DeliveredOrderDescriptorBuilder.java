package seedu.address.testutil;

import seedu.address.logic.commands.DeliveredCommand;
import seedu.address.model.Parcel.comment.Comment;
import seedu.address.model.Parcel.itemtype.TypeOfItem;
import seedu.address.model.Parcel.ParcelAttributes.Address;
import seedu.address.model.Parcel.order.CashOnDelivery;
import seedu.address.model.Parcel.ParcelAttributes.Email;
import seedu.address.model.Parcel.ParcelAttributes.Name;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.ParcelAttributes.Phone;
import seedu.address.model.Parcel.ParcelAttributes.TimeStamp;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.model.Parcel.ParcelAttributes.Warehouse;

/**
 * A utility class to help with building EditParcelDescriptor objects.
 */
public class DeliveredOrderDescriptorBuilder {

    private DeliveredCommand.DeliveredParcelDescriptor descriptor;

    public DeliveredOrderDescriptorBuilder() {
        descriptor = new DeliveredCommand.DeliveredParcelDescriptor();
    }

    public DeliveredOrderDescriptorBuilder(DeliveredCommand.DeliveredParcelDescriptor descriptor) {
        this.descriptor = new DeliveredCommand.DeliveredParcelDescriptor(descriptor);
    }

    /**
     * Returns an {@code DoneOrderDescriptor} with fields containing {@code order}'s details
     */
    public DeliveredOrderDescriptorBuilder(Order order) {
        descriptor = new DeliveredCommand.DeliveredParcelDescriptor();
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

    public DeliveredCommand.DeliveredParcelDescriptor build() {
        return descriptor;
    }
}
