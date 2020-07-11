package csdev.couponstash.logic.commands;

import java.time.LocalDate;
import java.util.List;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.CliSyntax;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;

import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;

import javafx.collections.ObservableList;

/**
 * This class represents the "saved" command
 * in Coupon Stash. The "saved" command shows the
 * user how much he/she has saved on a certain date,
 * or over a range of time represented by two dates.
 * Coupon Stash will look through the Coupons
 * to produce the final "saved" amount displayed
 */
public class SavedCommand extends Command {
    // the word used to run this command
    public static final String COMMAND_WORD = "saved";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows "
            + "all the savings accumulated in Coupons stored in the "
            + "Coupon Stash (if no date is provided), savings earned "
            + "on a particular date in D-M-YYYY format, or savings "
            + "earned between two specified dates (inclusive).\n\n"
            + "Parameters: "
            + "[" + CliSyntax.PREFIX_DATE + "DATE] or "
            + "[" + CliSyntax.PREFIX_START_DATE + "START_DATE "
            + CliSyntax.PREFIX_EXPIRY_DATE + "END_DATE]\n\n"
            + "Example: " + COMMAND_WORD + " 17-6-2020 ";
    public static final String MESSAGE_FUTURE_DATE = "Savings cannot be"
            + " shown for future dates!";
    public static final String MESSAGE_INVALID_DATE_RANGE = "End date must"
            + " be later than start date!";
    public static final String MESSAGE_ONLY_ONE_DATE_OF_RANGE = "To specify a"
            + " range of dates, both start date and end date must be provided!\n"
            + MESSAGE_USAGE;
    public static final String MESSAGE_UNEXPECTED_EXTRA_WORDS = "Did you mean to"
            + " specify a date, or a range of dates?\n\n" + MESSAGE_USAGE;

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final boolean hasDate;

    /**
     * Constructs a SavedCommand with the given dates.
     * If no dates are given, the SavedCommand will execute
     * over all the Coupons in the Stash. If one date is given,
     * SavedCommand will execute only for that date. If two dates
     * are given, SavedCommand will sum all the Savings between
     * those two dates (inclusive).
     *
     * @param dates The dates given to the SavedCommand
     *              by the user, maximum of two.
     */
    public SavedCommand(LocalDate... dates) {
        if (dates.length == 1) {
            this.startDate = dates[0];
            this.endDate = dates[0];
            this.hasDate = true;
        } else if (dates.length == 2) {
            // assume that the dates are arranged in order
            this.startDate = dates[0];
            this.endDate = dates[1];
            this.hasDate = true;
        } else {
            // the length is 0
            // could also be more than 2, but this should not happen
            // as the parser would not have allowed more than 2 dates
            this.startDate = null;
            this.endDate = null;
            this.hasDate = false;
        }
    }

    /**
     * Executes the SavedCommand with a given Model representing
     * the current state of the Coupon Stash application
     * @param model {@code Model} which the command should operate on.
     * @return Returns the CommandResult that encompasses the
     *     message that should be shown to the user, as well as
     *     any other external actions that should occur.
     * @throws CommandException CommandException is thrown when
     *     the date provided is in the future, according to the
     *     system time retrieved by the application (most
     *     likely an error by the user, as you cannot see
     *     what you have saved in the future)
     */
    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        ObservableList<Coupon> couponsList = model.getCouponStash().getCouponList();
        StringBuilder moneySaved = new StringBuilder();
        PureMonetarySavings pms = new PureMonetarySavings();
        if (!this.hasDate) {
            // add up all the Savings to get total Savings
            for (Coupon c : couponsList) {
                PureMonetarySavings toBeAdded = c.getTotalSavings();
                pms = pms.add(toBeAdded);
            }
            moneySaved.append("In total, you have saved ");
        } else {
            // sum up over the range of dates
            LocalDate today = LocalDate.now();
            if (this.startDate.isAfter(today)) {
                throw new CommandException(SavedCommand.MESSAGE_FUTURE_DATE);
            }
            // add up Savings for the dates given
            for (Coupon c : couponsList) {
                DateSavingsSumMap savingsMap = c.getSavingsMap();

                for (LocalDate ld = startDate;
                     ld.isBefore(endDate) || ld.isEqual(endDate);
                     ld = ld.plusDays(1)) {

                    PureMonetarySavings toBeAdded = savingsMap.get(ld);
                    if (toBeAdded != null) {
                        pms = pms.add(toBeAdded);
                    }
                }
            }
            moneySaved.append("You saved ");
        }
        // add the custom money symbol to the String
        moneySaved.append(model.getStashSettings().getMoneySymbol());

        // to be appended at the end of the final message, based on
        // which duration was specified by the command
        String durationString = getDurationString();

        // get monetary amount
        moneySaved.append(String.format("%.2f", pms.getMonetaryAmountAsDouble()));

        // get saveables
        List<Saveable> saveables = pms.getListOfSaveables();
        if (!saveables.isEmpty()) {
            moneySaved.append(" as well as earned ");
            saveables.sort(Saveable::compareTo);
            for (Saveable sv : saveables) {
                moneySaved.append(sv.toString()).append(", ");
            }
            return new CommandResult(
                    moneySaved.substring(0, moneySaved.length() - 2) + durationString + ".");
        } else {
            return new CommandResult(moneySaved.append(durationString).append(".").toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof SavedCommand) {
            SavedCommand sc = (SavedCommand) o;
            if (this.hasDate && sc.hasDate) {
                assert this.startDate != null;
                assert this.endDate != null;

                return this.startDate.equals(sc.startDate) && this.endDate.equals(sc.endDate);
            } else {
                return !this.hasDate && !sc.hasDate;
            }
        } else {
            return false;
        }
    }

    /**
     * Based on the start date and end date of this
     * SavedCommand, create a String that describes the
     * range of dates (for use in command message).
     *
     * @return Returns a String describing the range of
     *         dates used in this SavedCommand.
     */
    private String getDurationString() {
        if (this.startDate == null || this.endDate == null) {
            return "";
        } else if (this.startDate.equals(this.endDate)) {
            return " on " + SavedCommand.formatDate(this.startDate);
        } else {
            return " between " + SavedCommand.formatDate(this.startDate)
                    + " and " + SavedCommand.formatDate(this.endDate);
        }
    }

    /**
     * Uses the formatter for the calendar (with full month
     * name) to format LocalDates in the SavedCommand message.
     *
     * @param ld The LocalDate to be formatted.
     * @return String holding the formatted date.
     */
    protected static String formatDate(LocalDate ld) {
        return ld.format(DateUtil.DAY_MONTH_YEAR_FORMATTER_FOR_CALENDAR);
    }
}
