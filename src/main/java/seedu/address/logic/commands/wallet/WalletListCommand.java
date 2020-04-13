package seedu.address.logic.commands.wallet;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all transactions in the address book to the user.
 */
public class WalletListCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Listed all transactions";
    public static final String COMMAND_WORD = "list";

    private static final Logger logger = LogsCenter.getLogger(WalletListCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        logger.info("Executing wallet list command");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
