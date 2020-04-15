package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Money;

/**
 * Predicts sales for the next month based on sales in the previous three months.
 */
public class PredictCommand extends Command {

    public static final String COMMAND_WORD = "predict";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Predicts sales for the next month based on sales in the previous three months. "
            + "\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Predicted sales for next month: $%1$s";
    public static final String MESSAGE_NUMBER_FORMAT = "Price of product is invalid";
    public static final String MESSAGE_NO_PRODUCTS = "At least one product is required";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getFilteredProductList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PRODUCTS);
        }

        Money revenue = predictRevenue(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, revenue));
    }

    /**
     * Predicts the revenue for the next month based on past three months.
     * @param model
     * @return calculated sales
     * @throws CommandException
     */
    private Money predictRevenue(Model model) throws CommandException {
        List<Transaction> transactionList = model.getFilteredTransactionList();
        LocalDate todayDate = LocalDate.now();

        Money firstMonthRevenue = calculateRevenueForMonth(transactionList, todayDate);
        Money secondMonthRevenue = calculateRevenueForMonth(transactionList, todayDate.minusMonths(1));
        Money thirdMonthRevenue = calculateRevenueForMonth(transactionList, todayDate.minusMonths(2));
        int predictedRevenue = (firstMonthRevenue.value
                + secondMonthRevenue.value
                + thirdMonthRevenue.value) / 3;

        return new Money(predictedRevenue);
    }

    /**
     * Calculate the revenue in a given month.
     * @param transactionList
     * @param date
     * @return revenue for that month
     * @throws CommandException
     */
    private Money calculateRevenueForMonth(List<Transaction> transactionList, LocalDate date)
            throws CommandException {
        int revenue = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);
            LocalDateTime transactionDateTime = transaction.getDateTime().value;
            Month transactionMonth = transactionDateTime.getMonth();

            if (transactionMonth == date.getMonth()) {
                try {
                    int price = transaction.getMoney().value;
                    revenue += price;
                } catch (Exception e) {
                    throw new CommandException(MESSAGE_NUMBER_FORMAT);
                }
            }
        }
        return new Money(revenue);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PredictCommand); // instanceof handles nulls
    }
}
