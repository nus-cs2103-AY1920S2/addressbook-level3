package team.easytravel.logic.commands.fixedexpense;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_FIXED_EXPENSE;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_FIXED_EXPENSES;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;

/**
 * Lists all fixed expenses to the user.
 */
public class ListFixedExpenseCommand extends Command {

    public static final String COMMAND_WORD = "listexpense";

    public static final String MESSAGE_SUCCESS = "Listed all fixed expenses.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.updateFilteredFixedExpenseList(PREDICATE_SHOW_ALL_FIXED_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_FIXED_EXPENSE);
    }
}
