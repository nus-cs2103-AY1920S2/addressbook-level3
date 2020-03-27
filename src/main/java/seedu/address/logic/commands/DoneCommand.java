package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RETURNS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Flag;
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
import seedu.address.model.order.returnorder.ReturnOrder;

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
    private final Flag flag;

    /**
     * @param targetIndex                of the order in the filtered order list to edit
     * @param flag to identify which list this command is targeting
     * @param doneOrderDescriptor details to edit the order with
     */
    public DoneCommand(Index targetIndex, Flag flag, DoneOrderDescriptor doneOrderDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(flag);
        requireNonNull(doneOrderDescriptor);

        this.targetIndex = targetIndex;
        this.flag = flag;
        this.doneOrderDescriptor = new DoneOrderDescriptor(doneOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> orderList = model.getFilteredOrderList();
        List<ReturnOrder> returnOrderList = model.getFilteredReturnOrderList();

        if (targetIndex.getZeroBased() >= orderList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        } else if (targetIndex.getZeroBased() >= returnOrderList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_RETURN_DISPLAYED_INDEX);
        }
        if (flag.toString().trim().equals("-o")) {
            Order orderToBeDelivered = orderList.get(targetIndex.getZeroBased());
            Order editedOrder = createDeliveredOrder(orderToBeDelivered, doneOrderDescriptor);
            if (!orderToBeDelivered.isDelivered()) {
                model.setOrder(orderToBeDelivered, editedOrder);
                model.deliverOrder(editedOrder);
                model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
                return new CommandResult(String.format(MESSAGE_DELIVERED_SUCCESS, orderToBeDelivered));
            } else {
                model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);
                return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_DELIVERED, orderToBeDelivered));
            }
        } else if (flag.toString().trim().equals("-r")) {
            ReturnOrder returnOrderToBeDelivered = returnOrderList.get(targetIndex.getZeroBased());
            ReturnOrder editedReturnOrder = createDeliveredReturnOrder(returnOrderToBeDelivered, doneOrderDescriptor);
            if (!returnOrderToBeDelivered.isDelivered()) {
                model.setReturnOrder(returnOrderToBeDelivered, editedReturnOrder);
                model.deliverReturnOrder(editedReturnOrder);
                model.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
                return new CommandResult(String.format(MESSAGE_DELIVERED_SUCCESS, returnOrderToBeDelivered));
            } else {
                model.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
                return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_DELIVERED, returnOrderToBeDelivered));
            }
        } else {
            return new CommandResult(String.format(MESSAGE_USAGE));
        }
    }

    /**
     * Creates and returns a {@code deliveredOrder} with the details of {@code orderToDeliver}
     * edited with {@code donePersonDescriptor}.
     */
    private static Order createDeliveredOrder(Order orderToDeliver, DoneOrderDescriptor doneOrderDescriptor) {
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

        Order deliveredOrder = new Order(updatedTid, updatedName, updatedPhone, updatedEmail, updatedAddress,
                updateTimeStamp, updatedWarehouse, updatedCod, updatedComment, updatedType);
        deliveredOrder.setDeliveryStatus(updatedDeliveryStatus);
        return deliveredOrder;
    }

    /**
     * Creates and returns a {@code deliveredReturnOrder} with the details of {@code returnOrderToDeliver}
     * edited with {@code donePersonDescriptor}.
     */
    private static ReturnOrder createDeliveredReturnOrder(ReturnOrder returnOrderToDeliver,
                                                    DoneOrderDescriptor doneOrderDescriptor) {
        assert returnOrderToDeliver != null;

        TransactionId updatedTid = doneOrderDescriptor.getTid().orElse(returnOrderToDeliver.getTid());
        Name updatedName = doneOrderDescriptor.getName().orElse(returnOrderToDeliver.getName());
        Phone updatedPhone = doneOrderDescriptor.getPhone().orElse(returnOrderToDeliver.getPhone());
        Email updatedEmail = doneOrderDescriptor.getEmail().orElse(returnOrderToDeliver.getEmail());
        Address updatedAddress = doneOrderDescriptor.getAddress().orElse(returnOrderToDeliver.getAddress());
        TimeStamp updateTimeStamp = doneOrderDescriptor.getTimeStamp().orElse(returnOrderToDeliver.getTimestamp());
        Warehouse updatedWarehouse = doneOrderDescriptor.getWarehouse().orElse(returnOrderToDeliver.getWarehouse());
        Comment updatedComment = doneOrderDescriptor.getComment().orElse(returnOrderToDeliver.getComment());
        TypeOfItem updatedType = doneOrderDescriptor.getItemType().orElse(returnOrderToDeliver.getItemType());
        boolean updatedDeliveryStatus = doneOrderDescriptor.getDeliveryStatus();

        ReturnOrder deliveredReturnOrder = new ReturnOrder(updatedTid, updatedName, updatedPhone, updatedEmail,
                updatedAddress, updateTimeStamp, updatedWarehouse, updatedComment, updatedType);
        deliveredReturnOrder.setDeliveryStatus(updatedDeliveryStatus);
        return deliveredReturnOrder;
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
        public DoneOrderDescriptor(ReturnOrder toCopy) {
            setTid(toCopy.getTid());
            setName(toCopy.getName());
            setPhone(toCopy.getPhone());
            setEmail(toCopy.getEmail());
            setAddress(toCopy.getAddress());
            setTimeStamp(toCopy.getTimestamp());
            setWarehouse(toCopy.getWarehouse());
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
                    && getComment().equals(e.getComment())
                    && getItemType().equals(e.getItemType())
                    && (getDeliveryStatus() == (e.getDeliveryStatus()));
        }
    }
}

