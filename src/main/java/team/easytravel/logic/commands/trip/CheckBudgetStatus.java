package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.NONE;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;

/**
 * Obtains the budget available.
 */
public class CheckBudgetStatus extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_SUCCESS = "Total budget: $%1$.2f\n"
            + "Total Expense: $%2$.2f\n"
            + "Remaining budget: $%3$.2f\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        double remainingBudget = model.getBudget() - model.getTotalExpense();
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                (double) model.getBudget(), model.getTotalExpense(), remainingBudget), NONE);

    }
}
