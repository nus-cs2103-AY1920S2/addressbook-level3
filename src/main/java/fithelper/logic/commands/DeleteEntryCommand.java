package fithelper.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import fithelper.commons.core.Messages;
import fithelper.commons.core.index.Index;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.entry.Entry;

/**
 * Deletes an entry identified using it's displayed index from the address book.
 */
public class DeleteEntryCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the entry identified by the index number used in the displayed entry list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Entry: %1$s";

    private final Index targetIndex;

    public DeleteEntryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList = model.getFilteredEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Entry entryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEntry(entryToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEntryCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteEntryCommand) other).targetIndex)); // state check
    }
}
