package seedu.address.logic.parser.consults;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears the Consult TAble.
 */
public class ClearConsultCommand extends Command {

    public static final String COMMAND_WORD = "clearConsults";
    public static final String MESSAGE_SUCCESS = "Consult TAble has been cleared!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.clearConsults();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
