package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.returnorder.ReturnOrder;

/**
 * Functions for filtering the list
 */
public class ListUtil {
    private static LocalDate dateToday = LocalDate.now();
    private static LocalDateTime timeToday = LocalDateTime.now();

    /**
     * Overloaded method for Delivery Order.
     * Checks if the Order is to be delivered today using the isBeforeDeadline() method.
     * @param order Delivery Order to be delivered
     * @return boolean True if is Today's order, false if not
     *
     */
    public static boolean isToday(Order order) {
        requireNonNull(order);
        return isBeforeDeadline(order.getTimestamp().getOrderTimeStamp());
    }

    /**
     * Overloaded method for Return Order.
     * Checks if the Order is to be returned today using the isBeforeDeadline() method.
     * @param order Return Order to be returned
     * @return boolean True if is Today's order, false if not
     *
     */
    public static boolean isToday(ReturnOrder order) {
        requireNonNull(order);
        return isBeforeDeadline(order.getTimestamp().getOrderTimeStamp());
    }

    /**
     * Check if the Order's LocalDateTime has past the current Date and Time Today.
     * @param ldt Parse in the LocalDateTime object and compare with the Date and Time now
     * @return Boolean A Boolean of whether the order has past its deadline
     */
    public static boolean isBeforeDeadline(LocalDateTime ldt) {
        requireNonNull(ldt);
        LocalDate orderDate = ldt.toLocalDate();
        return orderDate.equals(dateToday) && ldt.isAfter(timeToday);
    }
}
