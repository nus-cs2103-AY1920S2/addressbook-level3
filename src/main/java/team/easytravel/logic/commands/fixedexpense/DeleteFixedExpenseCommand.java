package team.easytravel.logic.commands.fixedexpense;

import static java.util.Objects.requireNonNull;

import java.util.List;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.trip.TripManager;

/**
 * Deletes a fixed expense in the list.
 */
public class DeleteFixedExpenseCommand extends Command {

    public static final String COMMAND_WORD = "deleteexpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the fixed expense identified by the index number used in the displayed fixed expense list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FIXEDEXPENSE_SUCCESS = "Deleted Fixed Expense: %1$s";

    private final Index targetIndex;

    public DeleteFixedExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<FixedExpense> lastShownList = model.getFilteredFixedExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        int currentBudget = model.getBudget();
        FixedExpense fixedExpenseToDelete = lastShownList.get(targetIndex.getZeroBased());
        int updatedBudget = (int) (currentBudget - Double.parseDouble(fixedExpenseToDelete.getAmount().value));
        model.deleteFixedExpense(fixedExpenseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FIXEDEXPENSE_SUCCESS, fixedExpenseToDelete + "\n"
        + "Your remaining budget is: " + updatedBudget));
    }
}
