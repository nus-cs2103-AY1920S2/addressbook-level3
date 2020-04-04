package seedu.address.logic.commands.commandEdit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.person.Date;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing Assignment in the address book.
 */
public class EditAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "edit-assignment";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the details of the identified assignment "
                    + "by the index number used in the displayed assignment list. "
                    + "Existing values will be overwritten by the input values.\n"
                    + "Parameters: INDEX (must be a positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_DEADLINE + "DEADLINE] "
                    + "[" + PREFIX_TAG + "TAG]...\n"
                    + "Example: " + COMMAND_WORD + " 1 "
                    + PREFIX_NAME + "Python Tutorial 3 ";

    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This Assignment already exists in the address book.";

    private final Index index;
    private final EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param index                 of the Assignment in the filtered Assignment list to edit
     * @param editAssignmentDescriptor details to edit the Assignment with
     */
    public EditAssignmentCommand(Index index, EditAssignmentDescriptor editAssignmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAssignmentDescriptor);

        this.index = index;
        this.editAssignmentDescriptor = new EditAssignmentDescriptor(editAssignmentDescriptor);
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code AssignmentToEdit} edited with
     * {@code editAssignmentDescriptor}.
     */
    private static Assignment createEditedAssignment(Assignment AssignmentToEdit,
                                               EditAssignmentDescriptor editAssignmentDescriptor) {
        assert AssignmentToEdit != null;

        Name updatedName = editAssignmentDescriptor.getName().orElse(AssignmentToEdit.getName());
        ID updatedID = AssignmentToEdit.getId();
        Date updatedDeadline = editAssignmentDescriptor.getDeadline().orElse(AssignmentToEdit.getDeadline());
        Set<Tag> updatedTags = editAssignmentDescriptor.getTags().orElse(AssignmentToEdit.getTags());

        return new Assignment(updatedName, updatedID, updatedDeadline, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_ID);
        }

        Assignment AssignmentToEdit = lastShownList.get(index.getZeroBased());
        Assignment editedAssignment = createEditedAssignment(AssignmentToEdit, editAssignmentDescriptor);

        if (!AssignmentToEdit.weakEquals(editedAssignment) && model.has(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.set(AssignmentToEdit, editedAssignment);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAssignmentCommand)) {
            return false;
        }

        // state check
        EditAssignmentCommand e = (EditAssignmentCommand) other;
        return index.equals(e.index)
                && editAssignmentDescriptor.equals(e.editAssignmentDescriptor);
    }

    /**
     * Stores the details to edit the Assignment with. Each non-empty field value will replace the
     * corresponding field value of the Assignment.
     */
    public static class EditAssignmentDescriptor {

        private Name name;
        private ID AssignmentID;
        private Date deadline;
        private Set<Tag> tags;

        public EditAssignmentDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditAssignmentDescriptor(EditAssignmentDescriptor toCopy) {
            setName(toCopy.name);
            setID(toCopy.AssignmentID);
            setDeadline(toCopy.deadline);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, AssignmentID, deadline, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<ID> getID() {
            return Optional.ofNullable(AssignmentID);
        }

        public void setID(ID AssignmentID) {
            this.AssignmentID = AssignmentID;
        }

        public Optional<Date> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setDeadline(Date deadline) {
            this.deadline = deadline;
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
            if (!(other instanceof EditAssignmentDescriptor)) {
                return false;
            }

            // state check
            EditAssignmentDescriptor e = (EditAssignmentDescriptor) other;

            return getName().equals(e.getName())
                    && getDeadline().equals(e.getDeadline())
                    && getID().equals(e.getID())
                    && getTags().equals(e.getTags());
        }
    }
}
