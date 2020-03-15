package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.expensela.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.commons.core.index.Index;
import seedu.expensela.commons.util.CollectionUtil;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.*;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownList.get(index.getZeroBased());
        Transaction editedTransaction = createEditedPerson(transactionToEdit, editPersonDescriptor);

        if (!transactionToEdit.isSameTransaction(editedTransaction) && model.hasPerson(editedTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(transactionToEdit, editedTransaction);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Transaction createEditedPerson(Transaction transactionToEdit,
                                                  EditPersonDescriptor editPersonDescriptor) {
        assert transactionToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(transactionToEdit.getName());
        Amount updatedAmount = editPersonDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Date updatedDate = editPersonDescriptor.getDate().orElse(transactionToEdit.getDate());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(transactionToEdit.getRemark());

        return new Transaction(updatedName, updatedAmount, updatedDate, updatedRemark);
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
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Amount amount;
        private Date date;
        private Remark remark;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, date);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate());
        }
    }
}
