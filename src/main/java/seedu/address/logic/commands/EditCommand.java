package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RETURNS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.order.CashOnDelivery;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * Edits the details of an existing order in the order book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
        + "by the index number used in the displayed order list or return order list." + Messages.NEWLINE
        + "Existing values will be overwritten by the input values." + Messages.NEWLINE
        + "Example 1: " + COMMAND_WORD + " " + FLAG_ORDER_BOOK.getFlag() + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_TID + "A0185837Q" + Messages.NEWLINE
        + "Example 2: " + COMMAND_WORD + " " + FLAG_RETURN_BOOK.getFlag() + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_TID + "A0185837Q" + Messages.NEWLINE
        + "General format for editing orders: " + Messages.NEWLINE
        + COMMAND_WORD + " FLAGS INDEX(must be a positive integer) "
        + "[" + PREFIX_TID + "TRANSACTION_ID] "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_ADDRESS + "ADDRESS]" + Messages.NEWLINE
        + "[" + PREFIX_DELIVERY_TIMESTAMP + "DELIVERY_DATE_TIME] OR "
        + "[" + PREFIX_RETURN_TIMESTAMP + "RETURN_DATE_TIME] "
        + "[" + PREFIX_WAREHOUSE + "WAREHOUSE_LOCATION] "
        + "[" + PREFIX_COD + "CASH_ON_DELIVERY] (Only for Orders) "
        + "[" + PREFIX_COMMENT + "COMMENT] "
        + "[" + PREFIX_TYPE + "TYPE_OF_ITEM] ";

    public static final String MESSAGE_DUPLICATE_PARCEL = "This parcel already exists in the list.";
    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_EDIT_RETURN_ORDER_SUCCESS = "Edited Return Order: %1$s";
    public static final String MULTIPLE_FLAGS_DETECTED = "Different flags detected, please check your input."
        + Messages.NEWLINE + "Format example: " + COMMAND_WORD + " -o 1 n/Alex" + Messages.NEWLINE
        + "OR " + COMMAND_WORD + " -r 1 n/Alex";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided."
        + Messages.NEWLINE + "%1$s";

    private final Flag flag;
    private final Index index;
    private final EditParcelDescriptor editParcelDescriptor;

    /**
     * @param index                of the order in the filtered order list to edit
     * @param editParcelDescriptor details to edit the order with
     */
    public EditCommand(Index index, EditParcelDescriptor editParcelDescriptor, Flag flag) {
        requireNonNull(index);
        requireNonNull(editParcelDescriptor);

        this.flag = flag;
        this.index = index;
        this.editParcelDescriptor = new EditParcelDescriptor(editParcelDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();
        List<ReturnOrder> lastReturnShownList = model.getFilteredReturnOrderList();

        if ((index.getZeroBased() >= lastShownList.size() && flag.equals(FLAG_ORDER_BOOK))
            || index.getZeroBased() >= lastReturnShownList.size() && flag.equals(FLAG_RETURN_BOOK)) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        if (flag.equals(FLAG_ORDER_BOOK)) {
            Order orderToEdit;
            orderToEdit = lastShownList.get(index.getZeroBased());
            Order editedOrder = createEditedOrder(orderToEdit, editParcelDescriptor);
            return generalSetParcel(orderToEdit, editedOrder, model);
        } else if (flag.equals(FLAG_RETURN_BOOK)) {
            ReturnOrder returnToEdit;
            returnToEdit = lastReturnShownList.get(index.getZeroBased());
            ReturnOrder editedReturnOrder = createEditedReturnOrder(returnToEdit, editParcelDescriptor);
            return generalSetParcel(returnToEdit, editedReturnOrder, model);
        }

        throw new CommandException(Messages.MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP);

    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editParcelDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditParcelDescriptor editParcelDescriptor) {
        assert orderToEdit != null;

        TransactionId updatedTid = editParcelDescriptor.getTid().orElse(orderToEdit.getTid());
        Name updatedName = editParcelDescriptor.getName().orElse(orderToEdit.getName());
        Phone updatedPhone = editParcelDescriptor.getPhone().orElse(orderToEdit.getPhone());
        Email updatedEmail = editParcelDescriptor.getEmail().orElse(orderToEdit.getEmail());
        Address updatedAddress = editParcelDescriptor.getAddress().orElse(orderToEdit.getAddress());
        TimeStamp updateTimeStamp = editParcelDescriptor.getTimeStamp().orElse(orderToEdit.getTimestamp());
        Warehouse updatedWarehouse = editParcelDescriptor.getWarehouse().orElse(orderToEdit.getWarehouse());
        CashOnDelivery updatedCod = editParcelDescriptor.getCash().orElse(orderToEdit.getCash());
        Comment updatedComment = editParcelDescriptor.getComment().orElse(orderToEdit.getComment());
        TypeOfItem updatedType = editParcelDescriptor.getItemType().orElse(orderToEdit.getItemType());

        return new Order(updatedTid, updatedName, updatedPhone, updatedEmail, updatedAddress, updateTimeStamp,
            updatedWarehouse, updatedCod, updatedComment, updatedType);
    }

    /**
     * Creates and returns a {@code ReturnOrder} with the details of {@code returnOrderToEdit}
     * edited with {@code editParcelDescriptor}.
     */
    private static ReturnOrder createEditedReturnOrder(ReturnOrder returnOrderToEdit,
                                                       EditParcelDescriptor editParcelDescriptor) {
        assert returnOrderToEdit != null;

        TransactionId updatedTid = editParcelDescriptor.getTid().orElse(returnOrderToEdit.getTid());
        Name updatedName = editParcelDescriptor.getName().orElse(returnOrderToEdit.getName());
        Phone updatedPhone = editParcelDescriptor.getPhone().orElse(returnOrderToEdit.getPhone());
        Email updatedEmail = editParcelDescriptor.getEmail().orElse(returnOrderToEdit.getEmail());
        Address updatedAddress = editParcelDescriptor.getAddress().orElse(returnOrderToEdit.getAddress());
        TimeStamp updateTimeStamp = editParcelDescriptor.getTimeStamp().orElse(returnOrderToEdit.getTimestamp());
        Warehouse updatedWarehouse = editParcelDescriptor.getWarehouse().orElse(returnOrderToEdit.getWarehouse());
        Comment updatedComment = editParcelDescriptor.getComment().orElse(returnOrderToEdit.getComment());
        TypeOfItem updatedType = editParcelDescriptor.getItemType().orElse(returnOrderToEdit.getItemType());

        return new ReturnOrder(updatedTid, updatedName, updatedPhone, updatedEmail, updatedAddress, updateTimeStamp,
            updatedWarehouse, updatedComment, updatedType);
    }

    /**
     * Checks if parcel is either same as before, or upon edited it is the same as another parcel.
     *
     * @param parcelToEdit {@code Parcel} object that is going to be edited.
     * @param editedParcel {@code Parcel} object that has been edited.
     * @param model        {@code ModelManager} that represents the in-memory model of the order book data.
     * @return Returns a true if the Parcel is not editable, else false.
     * @throws CommandException Throws {@code CommandException} whenever a {@code Parcel} is duplicated.
     */
    private boolean isNotEditable(Parcel parcelToEdit, Parcel editedParcel, Model model) {
        return (!parcelToEdit.isSameParcel(editedParcel) && model.hasParcel(editedParcel));
    }

    /**
     * Act as a helper method to create different {@code CommandResult} object based on the {@code Parcel}
     * type that has been edited.
     *
     * @param parcelToEdit {@code Parcel} object that is going to be edited.
     * @param editedParcel {@code Parcel} object that has been edited.
     * @param model        {@code ModelManager} that represents the in-memory model of the order book data.
     * @return Returns a {@code CommandResult} object representing the result of either editing an {@code Order} or
     * {@code ReturnOrder}.
     * @throws CommandException Throws {@code CommandException} whenever a {@code Parcel} is duplicated or if the
     *                          {@code Parcel} type is invalid.
     */
    private CommandResult generalSetParcel(Parcel parcelToEdit, Parcel editedParcel, Model model)
        throws CommandException {
        if (isNotEditable(parcelToEdit, editedParcel, model)) {
            throw new CommandException(MESSAGE_DUPLICATE_PARCEL);
        }
        if (parcelToEdit instanceof Order && editedParcel instanceof Order) {
            model.setOrder((Order) parcelToEdit, (Order) editedParcel);
            model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
            return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedParcel));
        } else if (parcelToEdit instanceof ReturnOrder && editedParcel instanceof ReturnOrder) {
            model.setReturnOrder((ReturnOrder) parcelToEdit, (ReturnOrder) editedParcel);
            model.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
            return new CommandResult(String.format(MESSAGE_EDIT_RETURN_ORDER_SUCCESS, editedParcel));
        }
        throw new CommandException("Parcel type invalid");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editParcelDescriptor.equals(e.editParcelDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditParcelDescriptor {
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

        public EditParcelDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditParcelDescriptor(EditParcelDescriptor toCopy) {
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
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(tid, name, phone, email, address, timeStamp, warehouse,
                cod, comment, itemType);
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditParcelDescriptor)) {
                return false;
            }

            // state check
            EditParcelDescriptor e = (EditParcelDescriptor) other;

            return getTid().equals(e.getTid())
                && getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getTimeStamp().equals(e.getTimeStamp())
                && getWarehouse().equals(e.getWarehouse())
                && getCash().equals(e.getCash())
                && getComment().equals(e.getComment())
                && getItemType().equals(e.getItemType());
        }
    }
}
