package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.Model;

/**
 * Clears the CouponStash.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "CouponStash has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all entries from the coupon stash.\n"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, String commandText) {
        requireNonNull(model);
        model.setCouponStash(new CouponStash(), commandText);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
