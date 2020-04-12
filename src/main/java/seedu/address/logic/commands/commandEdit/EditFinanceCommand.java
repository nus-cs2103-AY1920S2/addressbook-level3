package seedu.address.logic.commands.commandEdit;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.Date;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FINANCES;

/**
 * Edits the details of an existing finance in the address book.
 */
public class EditFinanceCommand extends EditCommand {

    public static final String COMMAND_WORD = "edit-finance";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the details of the finance identified "
                    + "by the index number used in the displayed finance list. "
                    + "Existing values will be overwritten by the input values.\n"
                    + "Parameters: INDEX (must be a positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_DATE + "DATE] "
                    + "[" + PREFIX_AMOUNT + "AMOUNT] "
                    + "[" + PREFIX_TAG + "TAG]...\n"
                    + "Example: " + COMMAND_WORD + " 1 "
                    + PREFIX_NAME + "Paid NUS "
                    + PREFIX_AMOUNT + "2000 ";

    public static final String MESSAGE_EDIT_FINANCE_SUCCESS = "Edited Finance: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FINANCE = "This finance already exists in the address book.";

    private ID targetID;
    private EditFinanceDescriptor editFinanceDescriptor;
    private Finance toEdit;
    private Finance editedFinance;

    /**
     * @param targetID              of the finance in the filtered finance list to edit
     * @param editFinanceDescriptor details to edit the finance with
     */
    public EditFinanceCommand(ID targetID, EditFinanceDescriptor editFinanceDescriptor) {
        requireNonNull(targetID);
        requireNonNull(editFinanceDescriptor);
        this.targetID = targetID;
        this.editFinanceDescriptor = new EditFinanceDescriptor(editFinanceDescriptor);

    }

    /**
     * Creates and returns a {@code Finance} with the details of {@code financeToEdit} edited with
     * {@code editFinanceDescriptor}.
     */
    private static Finance createEditedFinance(Finance financeToEdit,
                                               EditFinanceDescriptor editFinanceDescriptor) {
        assert financeToEdit != null;

        Name updatedName = editFinanceDescriptor.getName().orElse(financeToEdit.getName());
        Date updatedDate = editFinanceDescriptor.getDate().orElse(financeToEdit.getDate());
        Amount updatedAmount = editFinanceDescriptor.getAmount().orElse(financeToEdit.getAmount());
        ID updatedCourseID = editFinanceDescriptor.getCourseID().orElse(financeToEdit.getCourseID());
        ID updatedStudentID = editFinanceDescriptor.getStudentID().orElse(financeToEdit.getStudentID());
        ID updatedTeacherID = editFinanceDescriptor.getTeacherID().orElse(financeToEdit.getStaffID());
        Set<Tag> updatedTags = editFinanceDescriptor.getTags().orElse(financeToEdit.getTags());

        // fields that cannot edit
        FinanceType updatedFinanceType = financeToEdit.getFinanceType();

        return new Finance(updatedName, financeToEdit.getId(), updatedFinanceType, updatedDate, updatedAmount, updatedCourseID, updatedStudentID, updatedTeacherID, updatedTags);
    }

    @Override
    protected void generateOppositeCommand() {
        oppositeCommand = new EditFinanceCommand(targetID, new EditFinanceCommand.EditFinanceDescriptor(toEdit));
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        List<Finance> lastShownList = model.getFilteredFinanceList();
        if (this.toEdit == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_FINANCE_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.FINANCE)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_FINANCE_DISPLAYED_ID);
            }
            this.toEdit = (Finance) model.get(targetID, Constants.ENTITY_TYPE.FINANCE);
            this.editedFinance = createEditedFinance(toEdit, editFinanceDescriptor);
        }

        if (!this.toEdit.weakEquals(editedFinance) && model.has(editedFinance)) {
            throw new CommandException(MESSAGE_DUPLICATE_FINANCE);
        }
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.set(toEdit, editedFinance);
        model.updateFilteredFinanceList(PREDICATE_SHOW_ALL_FINANCES);
        return new CommandResult(String.format(MESSAGE_EDIT_FINANCE_SUCCESS, editedFinance));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditFinanceCommand)) {
            return false;
        }

        // state check
        EditFinanceCommand e = (EditFinanceCommand) other;
        return targetID.equals(e.targetID)
                && editFinanceDescriptor.equals(e.editFinanceDescriptor);
    }

    /**
     * Stores the details to edit the finance with. Each non-empty field value will replace the
     * corresponding field value of the finance.
     */
    public static class EditFinanceDescriptor {

        private Name name;
        private FinanceType financeType;
        private Date date;
        private Amount amount;
        private ID courseid;
        private ID studentid;
        private ID teacherid;
        private Set<Tag> tags;

        public EditFinanceDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditFinanceDescriptor(EditFinanceDescriptor toCopy) {
            setName(toCopy.name);
            setFinanceType(toCopy.financeType);
            setDate(toCopy.date);
            setAmount(toCopy.amount);
            setTags(toCopy.tags);
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditFinanceDescriptor(Finance toCopy) {
            setName(toCopy.getName());
            setFinanceType(toCopy.getFinanceType());
            setDate(toCopy.getDate());
            setAmount(toCopy.getAmount());
            setTags(toCopy.getTags());
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, date, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<FinanceType> getFinanceType() {
            return Optional.ofNullable(financeType);
        }

        public void setFinanceType(FinanceType financeType) {
            this.financeType = financeType;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<ID> getCourseID() {
            return Optional.ofNullable(courseid);
        }

        public void setCourseID(ID courseID) {
            this.courseid = courseID;
        }

        public Optional<ID> getStudentID() {
            return Optional.ofNullable(studentid);
        }

        public void setStudentID(ID studentID) {
            this.studentid = studentID;
        }

        public Optional<ID> getTeacherID() {
            return Optional.ofNullable(teacherid);
        }

        public void setTeacherID(ID teacherID) {
            this.teacherid = teacherID;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used
         * internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFinanceDescriptor)) {
                return false;
            }

            // state check
            EditFinanceDescriptor e = (EditFinanceDescriptor) other;

            return getName().equals(e.getName())
                    && getFinanceType().equals(e.getFinanceType())
                    && getDate().equals(e.getDate())
                    && getAmount().equals(e.getAmount())
                    && getCourseID().equals(e.getCourseID())
                    && getStudentID().equals(e.getStudentID())
                    && getTeacherID().equals(e.getTeacherID())
                    && getTags().equals(e.getTags());
        }
    }
}
