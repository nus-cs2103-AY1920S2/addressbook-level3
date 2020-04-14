package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_INDEX;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
import fithelper.commons.core.index.Index;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Type;

/**
 * Deletes a entry identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the entry identified by the index number used in the displayed entry list.\n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "food "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Entry: %1$s";

    private static final String MESSAGE_COMMIT = "Delete an entry";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Type deleteType;
    private final Index targetIndex;

    public DeleteCommand(Type deleteType, Index targetIndex) {
        this.deleteType = deleteType;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Entry> lastShownList;
        if (deleteType.equals(new Type("food"))) {
            lastShownList = model.getFilteredFoodEntryList();
        } else {
            lastShownList = model.getFilteredSportsEntryList();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Entry entryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEntry(entryToDelete);
        model.deleteVevent(entryToDelete);


        model.commit(MESSAGE_COMMIT);
        logger.info("Deleted an entry");

        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
