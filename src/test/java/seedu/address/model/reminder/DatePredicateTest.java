package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.ReminderBuilder;

public class DatePredicateTest {

    @Test
    public void equals() {
        LocalDate firstPredicateDate = LocalDate.of(2022, 04, 05);
        LocalDate secondPredicateDate = LocalDate.of(2023, 05, 20);

        DatePredicate firstPredicate = new DatePredicate(firstPredicateDate);
        DatePredicate secondPredicate = new DatePredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DatePredicate firstPredicateCopy = new DatePredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_reminderContainsDate_returnsTrue() throws ParseException {
        // Matching date
        DatePredicate predicate = new DatePredicate(LocalDate.of(2022, 04, 03));
        assertTrue(predicate.test(new ReminderBuilder().withDate("2022-04-03").build()));
    }

    @Test
    public void test_reminderDoesNotContainDate_returnsFalse() throws ParseException {
        // Non-matching date
        DatePredicate predicate = new DatePredicate(LocalDate.of(2022, 10, 20));
        assertFalse(predicate.test(new ReminderBuilder().withDate("2023-10-20").build()));
    }
}
