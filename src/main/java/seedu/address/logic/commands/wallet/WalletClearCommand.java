package seedu.address.logic.commands.wallet;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.Wallet;

/**
 * Clears the wallet.
 */
public class WalletClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_CLEAR_EXECUTION = "Clearing transactions...";
    public static final String MESSAGE_SUCCESS = "Wallet has been cleared!";

    private static final Logger logger = LogsCenter.getLogger(WalletClearCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info(MESSAGE_CLEAR_EXECUTION);
        model.setWallet(new Wallet());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
