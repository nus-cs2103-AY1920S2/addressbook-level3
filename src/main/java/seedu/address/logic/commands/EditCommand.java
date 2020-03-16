package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing order in the order book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TID + "TRANSACTION_ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DELIVERY_TIMESTAMP + "DELIVERY_DATE_&_TIME] "
            + "[" + PREFIX_WAREHOUSE + "WAREHOUSE_LOCATION] "
            + "[" + PREFIX_COD + "CASH_ON_DELIVERY] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_TID + "A0185837Q";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the order book.";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index                of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        if (!orderToEdit.isSameOrder(editedOrder) && model.hasOrder(editedOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedOrder));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        TransactionID updatedTID = editOrderDescriptor.getTID().orElse(orderToEdit.getTID());
        Name updatedName = editOrderDescriptor.getName().orElse(orderToEdit.getName());
        Phone updatedPhone = editOrderDescriptor.getPhone().orElse(orderToEdit.getPhone());
        Address updatedAddress = editOrderDescriptor.getAddress().orElse(orderToEdit.getAddress());
        TimeStamp updateTimeStamp = editOrderDescriptor.getTimeStamp().orElse(orderToEdit.getTimestamp());
        Warehouse updatedWarehouse = editOrderDescriptor.getWarehouse().orElse(orderToEdit.getWarehouse());
        CashOnDelivery updatedCOD = editOrderDescriptor.getCash().orElse(orderToEdit.getCash());
        Comment updatedComment = editOrderDescriptor.getComment().orElse(orderToEdit.getComment());
        Set<Tag> updatedTags = editOrderDescriptor.getTags().orElse(orderToEdit.getTags());

        return new Order(updatedTID, updatedName, updatedPhone, updatedAddress, updateTimeStamp, updatedWarehouse,
                updatedCOD, updatedComment, updatedTags);
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
                && editOrderDescriptor.equals(e.editOrderDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private TransactionID tid;
        private Name name;
        private Phone phone;
        private Address address;
        private TimeStamp timeStamp;
        private Warehouse warehouse;
        private CashOnDelivery cod;
        private Comment comment;
        private Set<Tag> tags;

        public EditOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setTID(toCopy.tid);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setAddress(toCopy.address);
            setTimeStamp(toCopy.timeStamp);
            setWarehouse(toCopy.warehouse);
            setCash(toCopy.cod);
            setComment(toCopy.comment);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(tid, name, phone, address, timeStamp, warehouse, cod, comment, tags);
        }
        public void setTID(TransactionID tid) {
            this.tid = tid;
        }

        public Optional<TransactionID> getTID() {
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            // state check
            EditOrderDescriptor e = (EditOrderDescriptor) other;

            return getTID().equals(e.getTID())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getTimeStamp().equals(e.getTimeStamp())
                    && getWarehouse().equals(e.getWarehouse())
                    && getCash().equals(e.getCash())
                    && getComment().equals(e.getComment())
                    && getTags().equals(e.getTags());
        }
    }
}
