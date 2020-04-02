package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_ARCHIVE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_USAGE;
import static java.util.Objects.requireNonNull;

import java.time.YearMonth;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.parser.Prefix;
import csdev.couponstash.model.Model;

/**
 * Lists all coupons in the CouponStash to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all %s coupons!";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Lists the coupons in CouponStash. "
                    + "You can view 3 types of lists: \n"
                    + "1. List of active coupons (without archived coupons)\n"
                    + "2. List of archived coupons\n"
                    + "3. List of used coupons.\n"
                    + "Parameters: The list you want to view.\n"
                    + "Examples: \n"
                    + COMMAND_WORD + "(list of active coupon)\n"
                    + COMMAND_WORD + " " + PREFIX_ARCHIVE + " (list of archived coupons)\n"
                    + COMMAND_WORD + " " + PREFIX_USAGE + " (list of used coupons)\n";

    private Prefix prefixToList;

    public ListCommand() {
        this.prefixToList = new Prefix("");
    }

    public ListCommand(Prefix prefixToList) {
        requireNonNull(prefixToList);
        this.prefixToList = prefixToList;
    }

    @Override
    public CommandResult execute(Model model, String commandText) {
        requireNonNull(model);
        model.updateMonthView(DateUtil.formatYearMonthToString(YearMonth.now()));
        String view = "";
        if (prefixToList.toString().isEmpty()) {
            model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);
            view = "active";
        } else if (prefixToList.equals(PREFIX_ARCHIVE)) {
            model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ARCHIVED_COUPONS);
            view = "archived";
        } else if (prefixToList.equals(PREFIX_USAGE)) {
            model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_USED_COUPONS);
            view = "used";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, view));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // state check
        ListCommand e = (ListCommand) other;
        return prefixToList.equals(((ListCommand) other).prefixToList);
    }
}
