package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Finds and lists all transactions in transaction list whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTransactionCommand extends Command {

    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose descriptions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers. "
            + "At least one field must be present in the command. \n"
            + "Parameters: "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER_NAME [CUSTOMER_NAME]...] "
            + "[" + PREFIX_PRODUCT + "PRODUCT_NAME [PRODUCT_NAME]...] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "[" + PREFIX_DATETIME + "DATETIME] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "Bob "
            + PREFIX_PRODUCT + "WaterMelon "
            + PREFIX_MONEY + "30 "
            + PREFIX_DATETIME + "2020-02-20 10:00 ";

    private final Predicate<Transaction> predicate;

    public FindTransactionCommand(Predicate<Transaction> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTransactionCommand // instanceof handles nulls
                && predicate.equals(((FindTransactionCommand) other).predicate)); // state check
    }
}
