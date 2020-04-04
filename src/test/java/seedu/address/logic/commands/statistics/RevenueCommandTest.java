package seedu.address.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.DateTime;
import seedu.address.testutil.transaction.DateTimeBuilder;

public class RevenueCommandTest {
    @Test
    public void constructor_nullRevenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RevenueCommand(null, null));
    }

    @Test
    public void equals() {
        DateTime startDate = new DateTimeBuilder().build();
        DateTime endDate = new DateTimeBuilder(DateTimeBuilder.DEFAULT_END_DATE).build();

        DateTime otherStartDate = new DateTimeBuilder(DateTimeBuilder.DEFAULT_OTHER_START_DATE).build();
        DateTime otherEndDate = new DateTimeBuilder(DateTimeBuilder.DEFAULT_OTHER_END_DATE).build();

        RevenueCommand revenueCommand = new RevenueCommand(startDate, endDate);
        RevenueCommand otherRevenueCommand = new RevenueCommand(otherStartDate, otherEndDate);

        // same object -> returns true
        assertTrue(revenueCommand.equals(revenueCommand));

        // same values -> returns true
        RevenueCommand revenueCommandCopy = new RevenueCommand(startDate, endDate);
        assertTrue(revenueCommand.equals(revenueCommandCopy));

        // different types -> returns false
        assertFalse(revenueCommand.equals(1));

        // null -> returns false
        assertFalse(revenueCommand.equals(null));

        // different product -> returns false
        assertFalse(revenueCommand.equals(otherRevenueCommand));
    }
}
