package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ESTHOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditAssignmentDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Edits the details of an existing assignment in the scheduler.
 */
public class EditAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "(st)edit";
    public static final String COMMAND_FUNCTION = "Edits the estimated workload of the assignment identified "
        + "by the index number used in the displayed assignment list. "
        + "Existing values will be overwritten by the input values.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_TITLE + "TITLE] "
        + "[" + PREFIX_DEADLINE + "DEADLINE] "
        + "[" + PREFIX_ESTHOURS + "ESTIMATED_HOURS] "
        + "[" + PREFIX_STATUS + "STATUS]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_TITLE + "CS2103 tP "
        + PREFIX_DEADLINE + "2020-11-11 23:59 "
        + PREFIX_ESTHOURS + "5.0 "
        + PREFIX_STATUS + "Completed";

    public static final String MESSAGE_EDIT_ASSIGNMENT_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the scheduler.";

    private final Index index;
    private final EditAssignmentDescriptor editAssignmentDescriptor;

    /**
     * @param index of the assignment in the filtered assignment list to edit
     * @param editAssignmentDescriptor details to edit the assignment with
     */
    public EditAssignmentCommand(Index index, EditAssignmentDescriptor editAssignmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAssignmentDescriptor);

        this.index = index;
        this.editAssignmentDescriptor = new EditAssignmentDescriptor(editAssignmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToEdit = lastShownList.get(index.getZeroBased());
        Assignment editedAssignment = editAssignmentDescriptor.createEditedAssignment(assignmentToEdit);

        if (!assignmentToEdit.isSameAssignment(editedAssignment) && model.hasAssignment(editedAssignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.setAssignment(assignmentToEdit, editedAssignment);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_ASSIGNMENT_SUCCESS, assignmentToEdit),
            false, false, false, true, false, false, false, false);
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

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
