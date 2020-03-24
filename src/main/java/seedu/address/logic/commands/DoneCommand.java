package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Status;

/**
 * Marks an existing assignment in the scheduler as done.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "(st)done";
    public static final String COMMAND_FUNCTION = "Updates the status of the assignment identified. ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "by the index number used in the displayed assignment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UPDATE_STATUS_SUCCESS =
            "The status of %1$s have been updated! You have one less assignment now!! ^^";
    public static final String MESSAGE_ALREADY_DONE =
            "This assignment has already been completed, don't try to cheat me! Good Luck!! =)";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public DoneCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Assignment> lastShownList = model.getAssignmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToUpdate = lastShownList.get(index.getZeroBased());
        Assignment updatedAssignment = createUpdatedAssignment(assignmentToUpdate);

        if (assignmentToUpdate.getStatus().toString().equals(Status.ASSIGNMENT_DONE)) {
            throw new CommandException(MESSAGE_ALREADY_DONE);
        }

        model.setAssignment(assignmentToUpdate, updatedAssignment);
        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, updatedAssignment.getTitle().toString()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Assignment createUpdatedAssignment(Assignment assignmentToUpdate) {
        assert assignmentToUpdate != null;

        return new Assignment(assignmentToUpdate.getTitle(), assignmentToUpdate.getDeadline(),
                assignmentToUpdate.getWorkload(), new Status(Status.ASSIGNMENT_DONE));
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
