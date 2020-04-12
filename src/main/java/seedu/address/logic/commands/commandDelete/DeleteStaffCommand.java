package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddStaffCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStaff.Staff;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a teacher identified using it's displayed index from the address book.
 */
public class DeleteStaffCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete-staff";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the staff identified by the existing ID number used in the displayed teacher list.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STAFF_SUCCESS = "Deleted Staff: %1$s";

    private ID targetID;
    private Index targetIndex;
    private Staff toDelete;

    public DeleteStaffCommand(ID targetID) {
        this.targetID = targetID;
    }

    public DeleteStaffCommand(Staff toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        if (this.toDelete == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.STAFF)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_STAFF_DISPLAYED_ID);
            }
            this.toDelete = (Staff) model.get(targetID, Constants.ENTITY_TYPE.STAFF);
        }
        if (this.targetID == null) {
            this.targetID = toDelete.getId();
        }
        if (this.targetIndex == null) {
            this.targetIndex = model.getIndex(this.toDelete);
        }
    }

    protected void generateOppositeCommand() {
        oppositeCommand = new AddStaffCommand(toDelete.clone(), targetIndex.getZeroBased());
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.delete(this.toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STAFF_SUCCESS, this.toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteStaffCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteStaffCommand) other).targetIndex)); // state check
    }
}
