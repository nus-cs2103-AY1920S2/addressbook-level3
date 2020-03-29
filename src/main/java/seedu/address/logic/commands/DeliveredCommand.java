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
public class DeliveredCommand extends Command {

    public static final String COMMAND_WORD = "delivered";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks an order as delivered based on its index in the current list.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "FLAG: -r or -o"
            + "Example: " + COMMAND_WORD + " 1 -o";

    public static final String MESSAGE_DELIVERED_SUCCESS = "The order has been delivered: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_DELIVERED = "This order was already delivered";

    private final Index targetIndex;
    private final DeliveredCommand.DeliveredOrderDescriptor deliveredOrderDescriptor;
    private final Flag flag;

    /**
     * @param targetIndex                of the order in the filtered order list to edit
     * @param flag to identify which list this command is targeting
     * @param deliveredOrderDescriptor details to edit the order with
     */
    public DeliveredCommand(Index targetIndex, Flag flag, DeliveredOrderDescriptor deliveredOrderDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(flag);
        requireNonNull(deliveredOrderDescriptor);

        this.targetIndex = targetIndex;
        this.flag = flag;
        this.deliveredOrderDescriptor = new DeliveredOrderDescriptor(deliveredOrderDescriptor);
    }

    /*
     * @param targetIndex                of the order in the filtered order list to edit
     * @param flag to identify which list this command is targeting
     * @param deliveredReturnOrderDescriptor details to edit the order with
     */
    /*public DeliveredCommand(Index targetIndex, Flag flag, DeliveredReturnOrderDescriptor deliveredReturnOrderDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(flag);
        requireNonNull(deliveredReturnOrderDescriptor);

        this.targetIndex = targetIndex;
        this.flag = flag;
        this.deliveredOrderDescriptor = null;
        this.deliveredReturnOrderDescriptor = new DeliveredReturnOrderDescriptor(deliveredReturnOrderDescriptor);
    }*/

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> orderList = model.getFilteredOrderList();
        List<ReturnOrder> returnOrderList = model.getFilteredReturnOrderList();

        if (targetIndex.getZeroBased() >= orderList.size() || targetIndex.getZeroBased() == -1) {
            return new CommandResult(String.format(Messages.MESSAGE_INVALID_RETURN_DISPLAYED_INDEX));
        }

        if (flag.toString().trim().equals("-o")) {
            Order orderToBeDelivered = orderList.get(targetIndex.getZeroBased());
            Order editedOrder = createDeliveredOrder(orderToBeDelivered, deliveredOrderDescriptor);
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
            if (targetIndex.getZeroBased() >= returnOrderList.size()) {
                return new CommandResult(String.format(Messages.MESSAGE_INVALID_RETURN_DISPLAYED_INDEX));
            }
            ReturnOrder returnOrderToBeDelivered = returnOrderList.get(targetIndex.getZeroBased());
            ReturnOrder editedReturnOrder = createDeliveredReturnOrder(returnOrderToBeDelivered,
                    deliveredOrderDescriptor);
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
    private static Order createDeliveredOrder(Order orderToDeliver, DeliveredOrderDescriptor deliveredOrderDescriptor) {
        assert orderToDeliver != null;

        TransactionId updatedTid = deliveredOrderDescriptor.getTid().orElse(orderToDeliver.getTid());
        Name updatedName = deliveredOrderDescriptor.getName().orElse(orderToDeliver.getName());
        Phone updatedPhone = deliveredOrderDescriptor.getPhone().orElse(orderToDeliver.getPhone());
        Email updatedEmail = deliveredOrderDescriptor.getEmail().orElse(orderToDeliver.getEmail());
        Address updatedAddress = deliveredOrderDescriptor.getAddress().orElse(orderToDeliver.getAddress());
        TimeStamp updateTimeStamp = deliveredOrderDescriptor.getTimeStamp().orElse(orderToDeliver.getTimestamp());
        Warehouse updatedWarehouse = deliveredOrderDescriptor.getWarehouse().orElse(orderToDeliver.getWarehouse());
        CashOnDelivery updatedCod = deliveredOrderDescriptor.getCash().orElse(orderToDeliver.getCash());
        Comment updatedComment = deliveredOrderDescriptor.getComment().orElse(orderToDeliver.getComment());
        TypeOfItem updatedType = deliveredOrderDescriptor.getItemType().orElse(orderToDeliver.getItemType());
        boolean updatedDeliveryStatus = deliveredOrderDescriptor.getDeliveryStatus();

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
                                                    DeliveredOrderDescriptor deliveredOrderDescriptor) {
        assert returnOrderToDeliver != null;

        TransactionId updatedTid = deliveredOrderDescriptor.getTid().orElse(returnOrderToDeliver.getTid());
        Name updatedName = deliveredOrderDescriptor.getName().orElse(returnOrderToDeliver.getName());
        Phone updatedPhone = deliveredOrderDescriptor.getPhone().orElse(returnOrderToDeliver.getPhone());
        Email updatedEmail = deliveredOrderDescriptor.getEmail().orElse(returnOrderToDeliver.getEmail());
        Address updatedAddress = deliveredOrderDescriptor.getAddress().orElse(returnOrderToDeliver.getAddress());
        TimeStamp updateTimeStamp = deliveredOrderDescriptor.getTimeStamp().orElse(returnOrderToDeliver.getTimestamp());
        Warehouse updatedWarehouse = deliveredOrderDescriptor.getWarehouse().orElse(returnOrderToDeliver
                .getWarehouse());
        Comment updatedComment = deliveredOrderDescriptor.getComment().orElse(returnOrderToDeliver.getComment());
        TypeOfItem updatedType = deliveredOrderDescriptor.getItemType().orElse(returnOrderToDeliver.getItemType());
        boolean updatedDeliveryStatus = deliveredOrderDescriptor.getDeliveryStatus();

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
        if (!(other instanceof DeliveredCommand)) {
            return false;
        }

        // state check
        DeliveredCommand e = (DeliveredCommand) other;
        return targetIndex.equals(e.targetIndex)
                && deliveredOrderDescriptor.equals(e.deliveredOrderDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class DeliveredOrderDescriptor {
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

        public DeliveredOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DeliveredOrderDescriptor(Order toCopy) {
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
        public DeliveredOrderDescriptor(ReturnOrder toCopy) {
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
        public DeliveredOrderDescriptor(DeliveredOrderDescriptor toCopy) {
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
            if (!(other instanceof DeliveredCommand.DeliveredOrderDescriptor)) {
                return false;
            }

            // state check
            DeliveredCommand.DeliveredOrderDescriptor e = (DeliveredOrderDescriptor) other;

            return getTid().equals(e.getTid())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTimeStamp().equals(e.getTimeStamp())
                    && getWarehouse().equals(e.getWarehouse())
                    && getComment().equals(e.getComment())
                    && getCash().equals(e.getCash())
                    && getItemType().equals(e.getItemType())
                    && (getDeliveryStatus() == (e.getDeliveryStatus()));
        }
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    /*public static class DeliveredReturnOrderDescriptor {
        private TransactionId tid;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private TimeStamp timeStamp;
        private Warehouse warehouse;
        private Comment comment;
        private TypeOfItem itemType;
        private boolean deliveryStatus;

        public DeliveredReturnOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        /*public DeliveredReturnOrderDescriptor(Order toCopy) {
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
        /*public DeliveredReturnOrderDescriptor(ReturnOrder toCopy) {
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
        /*public DeliveredReturnOrderDescriptor(DeliveredReturnOrderDescriptor toCopy) {
            setTid(toCopy.tid);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTimeStamp(toCopy.timeStamp);
            setWarehouse(toCopy.warehouse);
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
            if (!(other instanceof DeliveredCommand.DeliveredReturnOrderDescriptor)) {
                return false;
            }

            // state check
            DeliveredCommand.DeliveredReturnOrderDescriptor e = (DeliveredReturnOrderDescriptor) other;

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
    }*/
}

