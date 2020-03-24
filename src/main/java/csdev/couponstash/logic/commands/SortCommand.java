package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.Prefix;
import csdev.couponstash.model.Model;

/**
 * Creates an SortCommand to sort by specified prefix.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the coupons in CouponStash. "
            + "It is possible to sort by coupon name or expiry date. "
            + "Parameters: "
            + "The field you want to sort by, either "
            + PREFIX_NAME + " or " + PREFIX_EXPIRY_DATE + "\n"
            + "Examples: \n" + COMMAND_WORD + " "
            + PREFIX_NAME + " (sort by name)\n"
            + COMMAND_WORD + " "
            + PREFIX_EXPIRY_DATE + " (sort by expiry date)\n";

    public static final String MESSAGE_SUCCESS = "Successfully sorted by %s";

    private Prefix prefixToSortBy;

    /**
     * Creates a SortCommand that sorts the coupons according to the prefix specified
     * @param prefixToSortBy prefix to sort by
     */
    public SortCommand(Prefix prefixToSortBy) {
        requireNonNull(prefixToSortBy);

        this.prefixToSortBy = prefixToSortBy;
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);

        model.sortCoupons(prefixToSortBy);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, prefixToSortBy.toString())
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return prefixToSortBy.equals(((SortCommand) other).prefixToSortBy);
    }
}
