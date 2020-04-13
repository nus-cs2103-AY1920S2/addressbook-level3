package seedu.address.logic.commands.view;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;

/**
 * Views the details of an item in the ResuMe application.
 */
public abstract class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_VIEW_SUCCESS = "Viewing this item!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the Item in the resume book in more details.\n"
            + "Format: " + COMMAND_WORD + " INDEX i/ TYPE\n"
            + "Example: view 1 i/ int";

    protected final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

}
