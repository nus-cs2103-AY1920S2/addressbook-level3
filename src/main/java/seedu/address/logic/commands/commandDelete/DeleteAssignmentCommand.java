package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddAssignmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelObjectTags.ID;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a assignment identified using it's displayed ID from the address book.
 */
public class DeleteAssignmentCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the assignment identified by the existing ID number used in the displayed assignment list.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 16100";

    public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted assignment: %1$s";

    private Index targetIndex;
    private ID targetID;
    private Assignment toDelete;

    public DeleteAssignmentCommand(ID targetID) {
        this.targetID = targetID;
    }

    public DeleteAssignmentCommand(Assignment toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        if (this.toDelete == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.ASSIGNMENT)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_ASSIGNMENT_DISPLAYED_ID);
            }
            this.toDelete = (Assignment) model.get(targetID, Constants.ENTITY_TYPE.ASSIGNMENT);
        }
        if (this.targetID == null) {
            this.targetID = this.toDelete.getId();
        }
        if (this.targetIndex == null) {
            this.targetIndex = model.getIndex(this.toDelete);
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AddAssignmentCommand(toDelete.clone(), targetIndex.getZeroBased());
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.delete(this.toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, this.toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAssignmentCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteAssignmentCommand) other).targetIndex)); // state check
    }
}
