package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;

/**
 * Deletes an item identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number (must be a positive integer) used in the displayed "
            + "item list.\n"
            + "Format: "
            + COMMAND_WORD + " "
            + "INDEX "
            + PREFIX_ITEM + " TYPE\n"
            + "Example: " + COMMAND_WORD + " 1 i/ res";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted %1$s item";

    protected final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

}
