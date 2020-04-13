package seedu.address.logic.commands.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliPrefix.WALLET_COMMAND_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Income;

/**
 * Adds an income to the wallet.
 */
public class WalletIncomeCommand extends Command {
    public static final String COMMAND_WORD = "income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an income to the wallet. "
            + "Parameters: "
            + PREFIX_NAME + "<name> "
            + PREFIX_AMOUNT + "<amount> (must be positive) "
            + "[" + PREFIX_DATE + "<date:dd/mm/yyyy>] "
            + "[" + PREFIX_TAG + "<tag>]\n"
            + "Example: " + WALLET_COMMAND_TYPE + " "
            + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103T TA "
            + PREFIX_AMOUNT + "1000 "
            + PREFIX_DATE + "10/10/2019 "
            + PREFIX_TAG + "Work";

    public static final String MESSAGE_SUCCESS = "New income added: %1$s";
    public static final String MESSAGE_INCOME_EXECUTION = "Adding income %1$s...";

    private static final Logger logger = LogsCenter.getLogger(WalletExpenseCommand.class);

    private final Income toAdd;

    /**
     * Creates a WalletIncomeCommand to add the specified {@code Income}
     */
    public WalletIncomeCommand(Income income) {
        requireNonNull(income);
        toAdd = income;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info(String.format(MESSAGE_INCOME_EXECUTION, toAdd));
        model.addIncome(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WalletIncomeCommand // instanceof handles nulls
                && toAdd.equals(((WalletIncomeCommand) other).toAdd));
    }
}
