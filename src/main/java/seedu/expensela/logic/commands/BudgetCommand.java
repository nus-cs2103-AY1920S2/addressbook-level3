package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.MonthlyData;

/**
 * Set budget to a specified value in expenseLa
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets budget to the value specified in the parameter.\n"
            + "Parameters: b/BUDGET_AMOUNT (must be a positive with at most 2 decimal points) rc/\n"
            + "Example: " + COMMAND_WORD + " b/1000.00 rc/";

    public static final String MESSAGE_SUCCESS = "Budget is now set to %.2f";

    public static final String MESSAGE_SUCCESS_RECURRING = "Budget is now set to %.2f and it is a recurring budget";

    private final Double budgetValue;
    private final boolean recurring;

    public BudgetCommand(Double budgetValue, boolean recurring) {
        this.budgetValue = budgetValue;
        this.recurring = recurring;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MonthlyData currentData = model.getMonthlyData();
        model.setMonthlyData(new MonthlyData("1", new Budget(budgetValue.toString()), currentData.getExpense(),
                currentData.getIncome()));
        if (recurring) {
            GlobalData globalData = model.getGlobalData();
            globalData.setRecurringBudget(new Budget(budgetValue.toString()));
            model.setGlobalData(globalData);
            return new CommandResult(String.format(MESSAGE_SUCCESS_RECURRING, budgetValue));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, budgetValue));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && budgetValue.equals(((BudgetCommand) other).budgetValue));
    }
}
