package seedu.address.logic.commands.wallet;
import static seedu.address.logic.parser.CliPrefix.WALLET_COMMAND_TYPE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.transaction.WalletPredicate;

/**
 * Finds and lists all transactions in wallet book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class WalletFindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions with description containing "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: <prefix><keywords>...\n"
            + "Example: " + WALLET_COMMAND_TYPE + " "
            + COMMAND_WORD + " n/rice water chicken\n"
            + WALLET_COMMAND_TYPE + " "
            + COMMAND_WORD + " $/12 5\n"
            + WALLET_COMMAND_TYPE + " "
            + COMMAND_WORD + " d/11/11/2011 22/02/2020\n"
            + WALLET_COMMAND_TYPE + " "
            + COMMAND_WORD + " t/food shopping\n";

    public static final String WRONG_AMT = "Amount to find must be a positive integer and not be too large"
            + " (not exceeding ninety quadrillion dollars)\";\n"
            + "Example: " + COMMAND_WORD + " " + WALLET_COMMAND_TYPE + " $/5 10";

    public static final String ONLY_ONE_PARAMETER_ALLOWED = "Only 1 parameter is allowed.\n"
            + "Example: \n" + WALLET_COMMAND_TYPE + " " + COMMAND_WORD + " $/5 10\n"
            + WALLET_COMMAND_TYPE + " " + COMMAND_WORD + " n/chicken noodles\n"
            + "This is not allowed: " + WALLET_COMMAND_TYPE + " " + COMMAND_WORD + " n/rice $/4 d/03/03/2020 t/food";

    private static final Logger logger = LogsCenter.getLogger(WalletFindCommand.class);

    private final WalletPredicate predicate;

    public WalletFindCommand(WalletPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredTransactionList(predicate);
        logger.info("Executing Wallet Find Command");
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    private void requireNonNull(Model model) {
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WalletFindCommand // instanceof handles nulls
                && predicate.equals(((WalletFindCommand) other).predicate)); // state check
    }
}
