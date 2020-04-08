package seedu.address.commons.util;

import java.time.LocalDate;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * Functions for filtering the list
 */
public class ListUtil {

    private static LocalDate start;
    private static LocalDate end;
    private static boolean isAll;

    private static String allDates = "All Dates";

    /**
     * Initialize the variables for the purpose
     * of filtering the lists
     *
     */
    public static void init() {
        start = ShowCommand.getStartDate();
        end = ShowCommand.getEndDate();
        isAll = ShowCommand.isAll();
    }

    /**
     * Overloaded method for Delivery Orders
     *
     * The method is used by the filter method to
     * filter the dates given by the user
     *
     * @param order Delivery order
     * @return boolean
     */
    public static boolean filterListByDates(Order order) {
        return isAll ? listAll() : isDateInclusive(order);
    }

    /**
     * Overloaded method for Return Orders
     *
     * The method is used by the filter method to
     * filter the dates given by the user
     *
     * @param order Return order
     * @return boolean
     */
    public static boolean filterListByDates(ReturnOrder order) {
        return isAll ? listAll() : isDateInclusive(order);
    }
    /**
     * Overloaded method for Delivery Order.
     * Checks if the Order is to within the start and end date
     * @param order Delivery Order to be delivered
     * @return boolean True if is within the date, false if not
     *
     */
    public static boolean isDateInclusive(Order order) {
        LocalDate date = order.getTimestamp().getOrderTimeStamp().toLocalDate();
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    /**
     * Overloaded method for Return Order.
     * Checks if the Order is to be returned today using the isBeforeDeadline() method.
     * @param order Return Order to be returned
     * @return boolean True if is Today's order, false if not
     *
     */
    public static boolean isDateInclusive(ReturnOrder order) {
        LocalDate date = order.getTimestamp().getOrderTimeStamp().toLocalDate();
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    /**
     * Get all the orders from both lists
     *
     * @return boolean Always return true
     */
    public static boolean listAll() {
        return true;
    }

    public static LocalDate getStartDate() {
        return start;
    }

    public static LocalDate getEndDate() {
        return end;
    }

    public static boolean isAll() {
        return isAll;
    }

    public static String getAllDates() {
        return allDates;
    }
}
