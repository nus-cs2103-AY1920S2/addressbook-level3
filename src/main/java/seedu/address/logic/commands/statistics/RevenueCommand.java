package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.statistics.StartEndDate;
import seedu.address.model.product.Sales;
import seedu.address.model.transaction.Transaction;

/**
 * Displays the revenue trend in a selected period.
 */
public class RevenueCommand extends Command {

    public static final String COMMAND_WORD = "revenue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the revenue trend in a selected period. "
            + "Parameters: "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "2020-01-01 "
            + PREFIX_END_DATE + "2020-12-12";

    public static final String MESSAGE_SUCCESS = "Revenue from %1$s to %2$s: $%3$s";
    public static final String MESSAGE_NUMBER_FORMAT = "Price of product is invalid";

    private final StartEndDate startDate;
    private final StartEndDate endDate;

    /**
     * Creates an RevenueCommand to display the revenue.
     */
    public RevenueCommand(StartEndDate startDate, StartEndDate endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Sales revenue = calculateRevenue(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                                startDate,
                                endDate,
                                revenue));
    }

    /**
     * Calculates the revenue in a given time period
     * @param model
     * @return calculated sales
     * @throws CommandException
     */
    private Sales calculateRevenue(Model model) throws CommandException {
        int revenue = 0;
        List<Transaction> transactionList = model.getFilteredTransactionList();

        for (int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);
            LocalDateTime transactionDateTime = transaction.getDateTime().value;
            Date transactionDate = Date.from(transactionDateTime.atZone(ZoneId.systemDefault()).toInstant());

            if (transactionDate.compareTo(startDate.value) > 0
                    && transactionDate.compareTo(endDate.value) < 0) {
                try {
                    int price = Integer.parseInt(transaction.getMoney().value);
                    revenue += price;
                } catch (Exception e) {
                    throw new CommandException(MESSAGE_NUMBER_FORMAT);
                }
            }
        }

        return new Sales(String.valueOf(revenue));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RevenueCommand // instanceof handles nulls
                && startDate.equals(((RevenueCommand) other).startDate)
                && endDate.equals(((RevenueCommand) other).endDate));
    }
}
