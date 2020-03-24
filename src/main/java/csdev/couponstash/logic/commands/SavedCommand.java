package csdev.couponstash.logic.commands;

import java.util.List;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;

import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;

import javafx.collections.ObservableList;

/**
 * This class represents the "saved" command
 * in Coupon Stash. The "saved" command shows the
 * user how much he/she has saved from a certain date.
 * Coupon Stash will look through the Coupons
 * to produce the final "saved" amount displayed
 */
public class SavedCommand extends Command {
    // the word used to run this command
    public static final String COMMAND_WORD = "saved";

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
        StringBuilder moneySaved = new StringBuilder("You have saved ");
        // add the custom money symbol to the String
        moneySaved.append(model.getStashSettings().getMoneySymbol());
        // add up all the Savings to get total Savings
        PureMonetarySavings pms = new PureMonetarySavings();
        for (Coupon c : couponsList) {
            PureMonetarySavings toBeAdded = c.getTotalSavings();
            pms = pms.add(toBeAdded);
        }
        // get monetary amount
        moneySaved.append(String.format("%.2f", pms.getMonetaryAmountAsDouble()));

        // get saveables
        List<Saveable> saveables = pms.getListOfSaveables();
        if (!saveables.isEmpty()) {
            moneySaved.append(" as well as earned ");
            for (Saveable sv : saveables) {
                moneySaved.append(sv.toString()).append(", ");
            }
            return new CommandResult(
                    moneySaved.substring(0, moneySaved.length() - 2) + ".", false, false);
        } else {
            return new CommandResult(moneySaved.append(".").toString(), false, false);
        }
    }
}
