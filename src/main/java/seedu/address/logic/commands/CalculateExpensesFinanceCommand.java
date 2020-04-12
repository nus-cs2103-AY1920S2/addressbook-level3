package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.Finance;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all finances in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CalculateExpensesFinanceCommand extends Command {

    public static final String COMMAND_WORD = "expenses";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Finance> filteredFinances = model.getFilteredFinanceList();
        float totalAmount = 0;

        for (Finance finance : filteredFinances) {
            String amount = finance.getAmount().toString();
            if (amount.substring(0, 1).equals("-")) {
                //Add expenses
                amount = amount.replaceFirst("-", "");
                amount = amount.trim();
                totalAmount -= Float.parseFloat(amount);
            }

            //Ignore earnings
        }

        return new CommandResult("Total expenses: $ " + String.format("%.02f", totalAmount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalculateExpensesFinanceCommand); // instanceof handles nulls
    }
}
