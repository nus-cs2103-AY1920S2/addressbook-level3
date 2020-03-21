package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Adds a order to the order book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "delivered";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks an order as delivered based on its index in the current list.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELIVERED_SUCCESS = "The order has been delivered: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_DELIVERED = "This order was already delivered";

    private final Index targetIndex;
    private final DoneCommand.DoneOrderDescriptor doneOrderDescriptor;

    /**
     * @param targetIndex                of the order in the filtered order list to edit
     * @param doneOrderDescriptor details to edit the order with
     */
    public DoneCommand(Index targetIndex, DoneOrderDescriptor doneOrderDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(doneOrderDescriptor);

        this.targetIndex = targetIndex;
        this.doneOrderDescriptor = new DoneOrderDescriptor(doneOrderDescriptor);
    }

    /*@Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToBeDelivered = lastShownList.get(targetIndex.getZeroBased());
        if (orderToBeDelivered.isDelivered() == false) {
            model.deliverOrder(orderToBeDelivered);
            model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELIVERED_SUCCESS, orderToBeDelivered));
        } else {
            model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_DELIVERED, orderToBeDelivered));
        }
    }*/

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToBeDelivered = lastShownList.get(targetIndex.getZeroBased());
        Order editedOrder = createEditedOrder(orderToBeDelivered, doneOrderDescriptor);

        if (orderToBeDelivered.isDelivered() == false) {

            model.setOrder(orderToBeDelivered, editedOrder);
            model.deliverOrder(editedOrder);
            model.updateFilteredOrderList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELIVERED_SUCCESS, orderToBeDelivered));
        } else {
            model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_DELIVERED, orderToBeDelivered));
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Order createEditedOrder(Order orderToDeliver, DoneOrderDescriptor doneOrderDescriptor) {
        assert orderToDeliver != null;

        TransactionId updatedTid = doneOrderDescriptor.getTid().orElse(orderToDeliver.getTid());
        Name updatedName = doneOrderDescriptor.getName().orElse(orderToDeliver.getName());
        Phone updatedPhone = doneOrderDescriptor.getPhone().orElse(orderToDeliver.getPhone());
        Email updatedEmail = doneOrderDescriptor.getEmail().orElse(orderToDeliver.getEmail());
        Address updatedAddress = doneOrderDescriptor.getAddress().orElse(orderToDeliver.getAddress());
        TimeStamp updateTimeStamp = doneOrderDescriptor.getTimeStamp().orElse(orderToDeliver.getTimestamp());
        Warehouse updatedWarehouse = doneOrderDescriptor.getWarehouse().orElse(orderToDeliver.getWarehouse());
        CashOnDelivery updatedCod = doneOrderDescriptor.getCash().orElse(orderToDeliver.getCash());
        Comment updatedComment = doneOrderDescriptor.getComment().orElse(orderToDeliver.getComment());
        TypeOfItem updatedType = doneOrderDescriptor.getItemType().orElse(orderToDeliver.getItemType());
        boolean updatedDeliveryStatus = doneOrderDescriptor.getDeliveryStatus();

        Order copyOrder = new Order(updatedTid, updatedName, updatedPhone, updatedEmail, updatedAddress,
                updateTimeStamp, updatedWarehouse, updatedCod, updatedComment, updatedType);
        copyOrder.setDeliveryStatus(updatedDeliveryStatus);
        return copyOrder;


    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoneCommand)) {
            return false;
        }

        // state check
        DoneCommand e = (DoneCommand) other;
        return targetIndex.equals(e.targetIndex)
                && doneOrderDescriptor.equals(e.doneOrderDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class DoneOrderDescriptor {
        private TransactionId tid;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private TimeStamp timeStamp;
        private Warehouse warehouse;
        private CashOnDelivery cod;
        private Comment comment;
        private TypeOfItem itemType;
        private boolean deliveryStatus;

        public DoneOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DoneOrderDescriptor(Order toCopy) {
            setTid(toCopy.getTid());
            setName(toCopy.getName());
            setPhone(toCopy.getPhone());
            setEmail(toCopy.getEmail());
            setAddress(toCopy.getAddress());
            setTimeStamp(toCopy.getTimestamp());
            setWarehouse(toCopy.getWarehouse());
            setCash(toCopy.getCash());
            setComment(toCopy.getComment());
            setItemType(toCopy.getItemType());
            setDeliveryStatus(toCopy.isDelivered());
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DoneOrderDescriptor(DoneOrderDescriptor toCopy) {
            setTid(toCopy.tid);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTimeStamp(toCopy.timeStamp);
            setWarehouse(toCopy.warehouse);
            setCash(toCopy.cod);
            setComment(toCopy.comment);
            setItemType(toCopy.itemType);
            setDeliveryStatus(toCopy.deliveryStatus);
        }

        public void setTid(TransactionId tid) {
            this.tid = tid;
        }

        public Optional<TransactionId> getTid() {
            return Optional.ofNullable(tid);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setTimeStamp(TimeStamp timeStamp) {
            this.timeStamp = timeStamp;
        }

        public Optional<TimeStamp> getTimeStamp() {
            return Optional.ofNullable(timeStamp);
        }

        public void setWarehouse(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        public Optional<Warehouse> getWarehouse() {
            return Optional.ofNullable(warehouse);
        }

        public void setCash(CashOnDelivery cod) {
            this.cod = cod;
        }

        public Optional<CashOnDelivery> getCash() {
            return Optional.ofNullable(cod);
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public Optional<Comment> getComment() {
            return Optional.ofNullable(comment);
        }

        public void setItemType(TypeOfItem itemType) {
            this.itemType = itemType;
        }

        public Optional<TypeOfItem> getItemType() {
            return Optional.ofNullable(itemType);
        }

        public boolean getDeliveryStatus() {
            return this.deliveryStatus;
        }

        public void setDeliveryStatus(boolean deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DoneCommand.DoneOrderDescriptor)) {
                return false;
            }

            // state check
            DoneCommand.DoneOrderDescriptor e = (DoneOrderDescriptor) other;

            return getTid().equals(e.getTid())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTimeStamp().equals(e.getTimeStamp())
                    && getWarehouse().equals(e.getWarehouse())
                    && getCash().equals(e.getCash())
                    && getComment().equals(e.getComment())
                    && getItemType().equals(e.getItemType())
                    && (getDeliveryStatus() == (e.getDeliveryStatus()));
        }
    }
}

