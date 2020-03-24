package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.model.Model;

/**
 * Lists all coupons in the CouponStash to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all coupons";


    @Override
    public CommandResult execute(Model model, String commandText) {
        requireNonNull(model);
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
