package csdev.couponstash.model.coupon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

/**
 * Tests that a {@code Coupon}'s {@code ExpiryDate} is before the date given.
 */
public class DateIsBeforePredicate implements Predicate<Coupon> {
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d-M-yyyy");
    private final String date;

    public DateIsBeforePredicate(String date) {
        this.date = date;
    }

    @Override
    public boolean test(Coupon coupon) {
        LocalDate ed = coupon.getExpiryDate().getDate();
        LocalDate givenDate = LocalDate.parse(date, dateFormatter);
        return ed.isBefore(givenDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsBeforePredicate // instanceof handles nulls
                && date.equals(((DateIsBeforePredicate) other).date)); // state check
    }

}


