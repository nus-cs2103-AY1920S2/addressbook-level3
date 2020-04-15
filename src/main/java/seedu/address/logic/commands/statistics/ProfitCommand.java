package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Money;

/**
 * Displays the profit trend in a selected period.
 */
public class ProfitCommand extends Command {

    public static final String COMMAND_WORD = "profit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the profit trend in a selected period. \n"
            + "Parameters: "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "2020-01-01 10:00 "
            + PREFIX_END_DATE + "2020-12-12 10:01";

    public static final String MESSAGE_SUCCESS = "Profit from %1$s to %2$s: $%3$s";
    public static final String MESSAGE_NUMBER_FORMAT = "Price/ quantity/ cost price of product is invalid";
    public static final String MESSAGE_NO_PRODUCTS = "At least one product is required";
    public static final String MESSAGE_NEGATIVE_PROFIT = "You have made a loss of $%1$s!";
    public static final String MESSAGE_DATE_CONFLICT = "Start date must be after end date";

    private final DateTime startDateTime;
    private final DateTime endDateTime;

    /**
     * Creates an ProfitCommand to display the profit.
     */
    public ProfitCommand(DateTime startDateTime, DateTime endDateTime) {
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getFilteredProductList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PRODUCTS);
        }

        if (startDateTime.value.compareTo(endDateTime.value) > 0) {
            throw new CommandException(MESSAGE_DATE_CONFLICT);
        }

        List<Transaction> transactions = model.getInventorySystem().getTransactionList();
        Money profit = calculateProfit(transactions);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                startDateTime,
                endDateTime,
                profit));
    }

    /**
     * Calculates the profit in a given time period
     * @param transactions
     * @return calculated profit
     * @throws CommandException
     */
    private Money calculateProfit(List<Transaction> transactions) throws CommandException {
        int profit = 0;

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            LocalDateTime transactionDateTime = transaction.getDateTime().value;

            if (transactionDateTime.compareTo(startDateTime.value) >= 0
                    && transactionDateTime.compareTo(endDateTime.value) <= 0) {
                try {
                    int price = transaction.getMoney().value;
                    int quantity = transaction.getQuantity().getValue();
                    int costPrice = Integer.parseInt(transaction.getProduct().getCostPrice().value);
                    profit += (price - costPrice * quantity);
                } catch (Exception e) {
                    throw new CommandException(MESSAGE_NUMBER_FORMAT);
                }
            }
        }

        if (profit < 0) {
            throw new CommandException(String.format(MESSAGE_NEGATIVE_PROFIT, -profit));
        }

        return new Money(profit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfitCommand // instanceof handles nulls
                && startDateTime.equals(((ProfitCommand) other).startDateTime)
                && endDateTime.equals(((ProfitCommand) other).endDateTime));
    }
}
