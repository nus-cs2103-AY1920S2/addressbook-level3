package csdev.couponstash.model.coupon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

/**
 * Tests that a {@code Coupon}'s {@code ExpiryDate} is before the date given.
 */
public class DateIsBeforePredicate implements Predicate<Coupon> {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy");
    private final String date;

    public DateIsBeforePredicate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public boolean test(Coupon coupon) {
        LocalDate ed = coupon.getExpiryDate().getDate();
        LocalDate givenDate = LocalDate.parse(date, DATE_FORMATTER);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return ed.isBefore(givenDate) && ed.isAfter(yesterday);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsBeforePredicate // instanceof handles nulls
                && date.equals(((DateIsBeforePredicate) other).date)); // state check
    }
}


