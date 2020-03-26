package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Model;

/**
 * Reset balance to 0 in expenseLa
 */
public class ResetBalanceCommand extends Command {

    public static final String COMMAND_WORD = "resetbalance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Total balance is now 0.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateTotalBalance(0.00);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
