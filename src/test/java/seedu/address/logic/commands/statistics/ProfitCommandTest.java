package seedu.address.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.DateTime;
import seedu.address.testutil.transaction.DateTimeBuilder;

public class ProfitCommandTest {
    @Test
    public void constructor_nullProfit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProfitCommand(null, null));
    }

    @Test
    public void equals() {
        DateTime startDate = new DateTimeBuilder().build();
        DateTime endDate = new DateTimeBuilder(DateTimeBuilder.DEFAULT_END_DATE).build();

        DateTime otherStartDate = new DateTimeBuilder(DateTimeBuilder.DEFAULT_OTHER_START_DATE).build();
        DateTime otherEndDate = new DateTimeBuilder(DateTimeBuilder.DEFAULT_OTHER_END_DATE).build();

        ProfitCommand profitCommand = new ProfitCommand(startDate, endDate);
        ProfitCommand otherProfitCommand = new ProfitCommand(otherStartDate, otherEndDate);

        // same object -> returns true
        assertTrue(profitCommand.equals(profitCommand));

        // same values -> returns true
        ProfitCommand profitCommandCopy = new ProfitCommand(startDate, endDate);
        assertTrue(profitCommand.equals(profitCommandCopy));

        // different types -> returns false
        assertFalse(profitCommand.equals(1));

        // null -> returns false
        assertFalse(profitCommand.equals(null));

        // different product -> returns false
        assertFalse(profitCommand.equals(otherProfitCommand));
    }
}
