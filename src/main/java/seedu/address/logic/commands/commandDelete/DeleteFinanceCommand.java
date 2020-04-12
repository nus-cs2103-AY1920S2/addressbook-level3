package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelObjectTags.ID;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a finance identified using it's displayed index from the address book.
 */
public class DeleteFinanceCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete-finance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the finance identified by the index number used in the displayed finance list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FINANCE_SUCCESS = "Deleted Finance: %1$s";

    private ID targetID;
    private Index targetIndex;
    private Finance toDelete;

    public DeleteFinanceCommand(ID targetID) {
        this.targetID = targetID;
    }

    public DeleteFinanceCommand(Finance toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        if (this.toDelete == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_FINANCE_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.FINANCE)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_FINANCE_DISPLAYED_ID);
            }
            this.toDelete = (Finance) model.get(targetID, Constants.ENTITY_TYPE.FINANCE);
        }
        if (this.targetID == null) {
            this.targetID = toDelete.getId();
        }
        if (this.targetIndex == null) {
            this.targetIndex = model.getIndex(toDelete);
        }
    }

    @Override
    protected void generateOppositeCommand() {
        oppositeCommand = new AddFinanceCommand(toDelete, targetIndex.getZeroBased());
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.delete(this.toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FINANCE_SUCCESS, this.toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFinanceCommand // instanceof handles nulls
                && targetID.equals(((DeleteFinanceCommand) other).targetID)); // state check
    }
}
