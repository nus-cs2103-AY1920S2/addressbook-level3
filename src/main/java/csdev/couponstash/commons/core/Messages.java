package csdev.couponstash.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_COUPON_DISPLAYED_INDEX = "The coupon index provided is invalid";
    public static final String MESSAGE_COUPONS_LISTED_OVERVIEW = "%1$d coupons listed!";
    public static final String MESSAGE_COUPONS_EXPIRING_ON_DATE = "Coupon(s) expiring on %s:";
    public static final String MESSAGE_COUPONS_EXPIRING_DURING_YEAR_MONTH = "Coupon(s) expiring during %s:";
    public static final String MESSAGE_NO_COUPONS_EXPIRING = "No coupons expiring on %s! Try another date/ month!";
    public static final String MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE =
            "Please input an earlier date to be reminded, than the coupon's expiry date";
    public static final String MESSAGE_REMIND_DATE_BEFORE_TODAYS =
            "Please input a later date than today's";

}
