package team.easytravel.logic.commands.fixedexpense;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_FIXED_EXPENSE;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.trip.TripManager;

/**
 * Clears the fixed expense list
 */
public class ClearFixedExpenseCommand extends Command {

    public static final String COMMAND_WORD = "clearexpense";
    public static final String MESSAGE_SUCCESS = "Fixed Expenses has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.setFixedExpenseManager(new FixedExpenseManager());
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_FIXED_EXPENSE);
    }

}
