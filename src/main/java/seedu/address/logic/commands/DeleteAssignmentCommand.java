package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Deletes an assignment using it's displayed index from the AssignmentSchedule.
 */
public class DeleteAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "(st)delete";
    public static final String COMMAND_FUNCTION = "Deletes the assignment identified by the "
        + "index number used in the displayed assignment list.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted Assignment: %1$s\nYou now have one less "
        + "assignment. Keep up the good work!";

    private final Index targetIndex;

    public DeleteAssignmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAssignment(assignmentToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, assignmentToDelete),
            false, false, false, true, false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteAssignmentCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteAssignmentCommand) other).targetIndex)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
