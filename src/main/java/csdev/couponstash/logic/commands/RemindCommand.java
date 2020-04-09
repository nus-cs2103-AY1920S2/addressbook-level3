package csdev.couponstash.logic.commands;

import java.time.LocalDate;
import java.util.List;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.ui.RemindWindow;

/**
 * The only reason for this class's existence is just so we can attribute the showRemind method
 * correctly to our un-contactable group mate. We would have moved this somewhere else otherwise.
 */
public class RemindCommand {
    /**
     * This method is to check all coupon's remind date against today's and
     * formulate the entire coupons that has to be reminded today as a string
     * @param list - the current coupon's list
     */
    public static void showRemind(List<Coupon> list) {
        String remindMessage = "";
        int count = 1;
        boolean remindFlag = false;

        for (Coupon temp : list) {
            if (temp.getRemindDate().getDate().equals(LocalDate.now())) {
                remindFlag = true;
                remindMessage = remindMessage + count + ". "
                        + temp.getName().toString()
                        + " (Starts on " + temp.getStartDate().toString() + ")"
                        + " (Expires on "
                        + temp.getExpiryDate().toString()
                        + ")" + "\n";
                count = count + 1;
            }
        }
        if (remindFlag) {
            RemindWindow.displayRemind(remindMessage);
        }
    }
}
