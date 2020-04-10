package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.TRIP_EDIT;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.Budget;
import team.easytravel.model.trip.TripManager;

/**
 * Edits the details of an existing budget.
 */
public class EditBudgetCommand extends Command {

    public static final String COMMAND_WORD = "editbudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the budget of the current trip."
            + "Parameter: AMOUNT (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1000";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Edited Budget: %1$s";
    public static final String MESSAGE_BUDGET_TOO_LOW = "Edited Budget should be more than expenses";

    private final Budget newBudget;

    /**
     * @param budget details to edit the current budget with
     */
    public EditBudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.newBudget = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }
        Integer amount = newBudget.value;

        if (amount < model.getTotalExpense()) {
            throw new CommandException(String.format(MESSAGE_EDIT_BUDGET_SUCCESS, amount) + "\n"
                    + MESSAGE_BUDGET_TOO_LOW);
        }

        model.setBudget(newBudget);
        return new CommandResult(String.format(MESSAGE_EDIT_BUDGET_SUCCESS, newBudget), TRIP_EDIT);
    }


}
