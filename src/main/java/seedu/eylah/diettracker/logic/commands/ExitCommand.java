package seedu.eylah.diettracker.logic.commands;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.diettracker.model.DietModel;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting EYLAH as requested ...";

    @Override
    public CommandResult execute(DietModel dietModel) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true, false);
    }
}
