package seedu.expensela.logic.commands;

import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Model;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.MonthlyData;

import static java.util.Objects.requireNonNull;

public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets budget to the value specified in the parameter.\n"
            + "Parameters: BUDGET_AMOUNT (must be a positive with at most 2 decimal points)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Budget is now set to %.2f";

    private final Double budgetValue;

    public BudgetCommand(Double budgetValue) {
        this.budgetValue = budgetValue;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MonthlyData currentData = model.getMonthlyData();
        model.updateMonthlyData(new MonthlyData("1", new Budget(budgetValue.toString()), currentData.getExpense(),
                currentData.getIncome()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, budgetValue));
    }
}
