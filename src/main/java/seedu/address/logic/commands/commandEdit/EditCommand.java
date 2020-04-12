package seedu.address.logic.commands.commandEdit;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Edits the details of an existing person in the address book.
 */
public abstract class EditCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "edit";
}
