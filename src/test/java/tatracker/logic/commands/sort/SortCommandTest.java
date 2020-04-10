//@@author aakanksha-rai

package tatracker.logic.commands.sort;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SortCommandTest {

    @Test
    public void equals() {
        SortCommand sortCommand =
                new SortCommand(SortType.ALPHABETIC);

        SortCommand sortCommandCopy =
                new SortCommand(SortType.ALPHABETIC);

        SortCommand sortNewTypeCommand =
                new SortCommand(SortType.RATING_ASC);

        // same object -> returns true
        assertTrue(sortCommand.equals(sortCommand));

        // same values -> returns true
        assertTrue(sortCommand.equals(sortCommandCopy));

        // different types -> returns false
        assertFalse(sortCommand.equals(1));

        // null -> returns false
        assertFalse(sortCommand == null);

        // different types -> returns false
        assertFalse(sortCommand.equals(sortNewTypeCommand));
    }

}
