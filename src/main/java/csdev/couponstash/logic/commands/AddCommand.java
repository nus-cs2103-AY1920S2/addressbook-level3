package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_LIMIT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_PROMO_CODE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_START_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.savings.PercentageAmount;

/**
 * Adds a coupon to CouponStash.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a coupon to CouponStash.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PROMO_CODE + "PROMO CODE "
            + PREFIX_EXPIRY_DATE + "EXPIRY DATE "
            + PREFIX_SAVINGS + "SAVINGS "
            + "[" + PREFIX_START_DATE + "START DATE]"
            + "[" + PREFIX_SAVINGS + "SAVINGS]..."
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "The Deck Chicken Rice "
            + PREFIX_PROMO_CODE + "JIFANFTW "
            + PREFIX_EXPIRY_DATE + "30-08-2020 "
            + PREFIX_SAVINGS + "50" + PercentageAmount.PERCENT_SUFFIX + " "
            + PREFIX_START_DATE + "1-8-2020 "
            + PREFIX_LIMIT + "5 "
            + PREFIX_TAG + "Value "
            + PREFIX_TAG + "ChickenRice";

    public static final String MESSAGE_SUCCESS = "New coupon added: %1$s";
    public static final String MESSAGE_DUPLICATE_COUPON = "This coupon already exists in CouponStash";

    private final Coupon toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Coupon}
     */
    public AddCommand(Coupon coupon) {
        requireNonNull(coupon);
        toAdd = coupon;
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);

        if (model.hasCoupon(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_COUPON);
        }

        if (!toAdd.getExpiryDate().isAfterOrEqual(toAdd.getStartDate())) {
            throw new CommandException(StartDate.MESSAGE_CONSTRAINTS);
        }

        model.addCoupon(toAdd, commandText);


        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
