package seedu.address.logic.commands.academics;

import seedu.address.logic.commands.Command;

/**
 * Adds an academic report to the address book.
 */
public abstract class AcademicsCommand extends Command {

    public static final String COMMAND_WORD = "academics";
    public static final String MESSAGE_USAGE = "Commands: \n"
            + COMMAND_WORD + " report - displays academic statistics.\n"
            + COMMAND_WORD + " homework - filters homework assessments.\n"
            + COMMAND_WORD + " exam - filters exam assessments.\n"
            + COMMAND_WORD + " add - adds new assessment.\n"
            + COMMAND_WORD + " delete - deletes assessment.\n"
            + COMMAND_WORD + " edit - edits current assessment.\n"
            + COMMAND_WORD + " submit - submits students' work.\n"
            + COMMAND_WORD + " mark - marks students' work.\n"
            + "Type the following commands for more info!";
}
