package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.delino.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.delino.model.Model.PREDICATE_SHOW_ALL_RETURNS;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.delino.commons.core.Messages;
import seedu.delino.commons.core.index.Index;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.logic.parser.Flag;
import seedu.delino.model.Model;
import seedu.delino.model.parcel.optionalparcelattributes.Comment;
import seedu.delino.model.parcel.optionalparcelattributes.TypeOfItem;
import seedu.delino.model.parcel.order.CashOnDelivery;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.Address;
import seedu.delino.model.parcel.parcelattributes.Email;
import seedu.delino.model.parcel.parcelattributes.Name;
import seedu.delino.model.parcel.parcelattributes.Phone;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.parcelattributes.Warehouse;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

//@@author cherweijie
/**
 * Adds a order to the order book.
 */
public class DeliveredCommand extends Command {


    public static final String COMMAND_WORD = "delivered";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks an order as delivered based on its index in the current list.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "FLAG: -r or -o"
            + " Example: " + COMMAND_WORD + " 1 -o";

    public static final String MESSAGE_ORDER_DELIVERED_SUCCESS = "The order has been delivered: %1$s";
    public static final String MESSAGE_RETURN_ORDER_DELIVERED_SUCCESS = "The return order has been delivered: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_DELIVERED = "This order was already delivered";
    public static final String MESSAGE_RETURN_ORDER_ALREADY_DELIVERED = "This return order was already delivered";

    private static final Logger logger = Logger.getLogger(DeliveredCommand.class.getName());
    private final Index targetIndex;
    private final DeliveredParcelDescriptor deliveredParcelDescriptor;
    private final Flag flag;

    /**
     * @param targetIndex                of the order in the filtered order list to edit
     * @param flag to identify which list this command is targeting
     * @param deliveredParcelDescriptor details to edit the order with
     */
    public DeliveredCommand(Index targetIndex, Flag flag, DeliveredParcelDescriptor deliveredParcelDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(flag);
        requireNonNull(deliveredParcelDescriptor);

        this.targetIndex = targetIndex;
        this.flag = flag;
        this.deliveredParcelDescriptor = new DeliveredParcelDescriptor(deliveredParcelDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isFlagForOrderList()) {
            logger.fine("Input is for order list.");
            return processDeliveryOfOrder(model);
        } else if (isFlagForReturnList()) {
            logger.fine("Input is for return order list.");
            return processDeliveryOfReturnOrder(model);
        } else {
            logger.fine("Input flag is not valid.");
            throw new CommandException(String.format(MESSAGE_USAGE));
        }
    }

    private boolean isFlagForOrderList() {
        logger.fine("Check if flag is for order list.");
        return flag.toString().trim().equals("-o");
    }

    /**
     * Checks if the index is valid and if it is for order list.
     * @param model Model with the current state.
     * @return true if index is valid and it is for order list.
     */
    private boolean isIndexValidForOrderList(Model model) {
        requireNonNull(model);
        logger.fine("Check if index is valid for order list.");
        List<Order> orderList = model.getFilteredOrderList();
        return targetIndex.getZeroBased() >= orderList.size();
    }

    private boolean isFlagForReturnList() {
        logger.fine("Check if flag is for return order list.");
        return flag.toString().trim().equals("-r");
    }

    /**
     * Checks if the index is valid and if it is for return order list.
     * @param model Model with the current state.
     * @return true if index is valid and it is for return order list.
     */
    private boolean isIndexValidForReturnList(Model model) {
        requireNonNull(model);
        logger.fine("Check if index is valid for return order list.");
        List<ReturnOrder> returnOrderList = model.getFilteredReturnOrderList();
        return targetIndex.getZeroBased() >= returnOrderList.size();
    }

    /**
     * Processes the input for the delivery of an order and outputs the correct CommandResult.
     * @param model The current Model
     * @return A CommandResult based on the order
     * @throws CommandException CommandException thrown when user inputs an invalid index.
     */
    private CommandResult processDeliveryOfOrder(Model model) throws CommandException {
        if (isIndexValidForOrderList(model)) {
            logger.info("Input index is invalid for order list.");
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX));
        }
        Order orderToBeDelivered = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        if (!orderToBeDelivered.isDelivered()) {
            logger.fine("Order is not delivered, valid to be delivered.");
            deliverAndUpdateOrderList(model);
            return new CommandResult(String.format(MESSAGE_ORDER_DELIVERED_SUCCESS, orderToBeDelivered));
        } else {
            logger.fine("Order was already delivered. Unable to be delivered again.");
            updateOrderList(model);
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_DELIVERED, orderToBeDelivered));
        }
    }

    /**
     * Processes the input for the delivery of a return order and outputs the correct CommandResult.
     * @param model The current Model
     * @return A CommandResult based on the return order
     * @throws CommandException CommandException thrown when user inputs an invalid index.
     */
    private CommandResult processDeliveryOfReturnOrder(Model model) throws CommandException {
        if (isIndexValidForReturnList(model)) {
            logger.info("Input index is invalid for return order list.");
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_RETURN_DISPLAYED_INDEX));
        }
        ReturnOrder returnOrderToBeDelivered = model.getFilteredReturnOrderList().get(targetIndex.getZeroBased());
        if (!returnOrderToBeDelivered.isDelivered()) {
            logger.fine("Return order is not delivered, valid to be delivered.");
            deliverAndUpdateReturnList(model);
            return new CommandResult(String.format(MESSAGE_RETURN_ORDER_DELIVERED_SUCCESS, returnOrderToBeDelivered));
        } else {
            logger.fine("Return order was already delivered. Unable to be delivered again.");
            updateReturnList(model);
            return new CommandResult(String.format(MESSAGE_RETURN_ORDER_ALREADY_DELIVERED, returnOrderToBeDelivered));
        }
    }

    /**
     * Sets the order in model as delivered and updates the entire list of orders.
     * @param model The current Model
     */
    private void deliverAndUpdateOrderList(Model model) {
        logger.fine("Deliver and update the order list after delivery.");
        List<Order> orderList = model.getFilteredOrderList();
        Order orderToBeDelivered = orderList.get(targetIndex.getZeroBased());
        Order editedOrder = createDeliveredOrder(orderToBeDelivered, deliveredParcelDescriptor);
        model.setOrder(orderToBeDelivered, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    private void updateOrderList(Model model) {
        logger.fine("Update order list.");
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    /**
     * Sets the return order in model as delivered and updates the entire list of return orders.
     * @param model The current Model
     */
    private void deliverAndUpdateReturnList(Model model) {
        logger.fine("Deliver and update the return order list after delivery.");
        List<ReturnOrder> returnOrderList = model.getFilteredReturnOrderList();
        ReturnOrder returnOrderToBeDelivered = returnOrderList.get(targetIndex.getZeroBased());
        ReturnOrder editedReturnOrder = createDeliveredReturnOrder(returnOrderToBeDelivered,
                deliveredParcelDescriptor);
        model.setReturnOrder(returnOrderToBeDelivered, editedReturnOrder);
        model.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
    }

    private void updateReturnList(Model model) {
        logger.fine("Update return order list.");
        model.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
    }

    /**
     * Creates and returns a {@code deliveredOrder} with the details of {@code orderToDeliver}
     * edited with {@code donePersonDescriptor}.
     */
    private static Order createDeliveredOrder(Order orderToDeliver,
                                              DeliveredParcelDescriptor deliveredParcelDescriptor) {
        assert orderToDeliver != null;
        logger.fine("Create a delivered order.");
        TransactionId updatedTid = deliveredParcelDescriptor.getTid().orElse(orderToDeliver.getTid());
        Name updatedName = deliveredParcelDescriptor.getName().orElse(orderToDeliver.getName());
        Phone updatedPhone = deliveredParcelDescriptor.getPhone().orElse(orderToDeliver.getPhone());
        Email updatedEmail = deliveredParcelDescriptor.getEmail().orElse(orderToDeliver.getEmail());
        Address updatedAddress = deliveredParcelDescriptor.getAddress().orElse(orderToDeliver.getAddress());
        TimeStamp updateTimeStamp = deliveredParcelDescriptor.getTimeStamp().orElse(orderToDeliver.getTimestamp());
        Warehouse updatedWarehouse = deliveredParcelDescriptor.getWarehouse().orElse(orderToDeliver.getWarehouse());
        CashOnDelivery updatedCod = deliveredParcelDescriptor.getCash().orElse(orderToDeliver.getCash());
        Comment updatedComment = deliveredParcelDescriptor.getComment().orElse(orderToDeliver.getComment());
        TypeOfItem updatedType = deliveredParcelDescriptor.getItemType().orElse(orderToDeliver.getItemType());

        Order deliveredOrder = new Order(updatedTid, updatedName, updatedPhone, updatedEmail, updatedAddress,
                updateTimeStamp, updatedWarehouse, updatedCod, updatedComment, updatedType);
        deliveredOrder.setDeliveryStatus(true);
        return deliveredOrder;
    }

    /**
     * Creates and returns a {@code deliveredReturnOrder} with the details of {@code returnOrderToDeliver}
     * edited with {@code donePersonDescriptor}.
     */
    private static ReturnOrder createDeliveredReturnOrder(ReturnOrder returnOrderToDeliver,
                                                    DeliveredParcelDescriptor deliveredParcelDescriptor) {
        assert returnOrderToDeliver != null;
        logger.fine("Create a delivered return order");
        TransactionId updatedTid = deliveredParcelDescriptor.getTid().orElse(returnOrderToDeliver.getTid());
        Name updatedName = deliveredParcelDescriptor.getName().orElse(returnOrderToDeliver.getName());
        Phone updatedPhone = deliveredParcelDescriptor.getPhone().orElse(returnOrderToDeliver.getPhone());
        Email updatedEmail = deliveredParcelDescriptor.getEmail().orElse(returnOrderToDeliver.getEmail());
        Address updatedAddress = deliveredParcelDescriptor.getAddress().orElse(returnOrderToDeliver.getAddress());
        TimeStamp updateTimeStamp = deliveredParcelDescriptor.getTimeStamp().orElse(returnOrderToDeliver
                .getTimestamp());
        Warehouse updatedWarehouse = deliveredParcelDescriptor.getWarehouse().orElse(returnOrderToDeliver
                .getWarehouse());
        Comment updatedComment = deliveredParcelDescriptor.getComment().orElse(returnOrderToDeliver.getComment());
        TypeOfItem updatedType = deliveredParcelDescriptor.getItemType().orElse(returnOrderToDeliver.getItemType());

        ReturnOrder deliveredReturnOrder = new ReturnOrder(updatedTid, updatedName, updatedPhone, updatedEmail,
                updatedAddress, updateTimeStamp, updatedWarehouse, updatedComment, updatedType);
        deliveredReturnOrder.setDeliveryStatus(true);
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
                && deliveredParcelDescriptor.equals(e.deliveredParcelDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class DeliveredParcelDescriptor {
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

        public DeliveredParcelDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DeliveredParcelDescriptor(Order toCopy) {
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
        public DeliveredParcelDescriptor(ReturnOrder toCopy) {
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
        public DeliveredParcelDescriptor(DeliveredParcelDescriptor toCopy) {
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
            if (!(other instanceof DeliveredParcelDescriptor)) {
                return false;
            }

            // state check
            DeliveredParcelDescriptor e = (DeliveredParcelDescriptor) other;

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
}

