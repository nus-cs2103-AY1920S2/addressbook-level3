package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.results.CommandResult;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("", "feedback", "int");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("", "feedback", "int")));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different dataToUser value -> false
        assertFalse(commandResult.equals(new CommandResult("different",
                "feedback", "int")));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("", "different", "int")));

        // different display type -> returns false
        assertFalse(commandResult.equals(new CommandResult("", "feedback", "proj")));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("", "feedback", "int");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("", "feedback", "int").hashCode());

        // different dataToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("different", "feedback", "int").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("", "different", "int").hashCode());

        // different display type -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("", "different", "proj").hashCode());
    }
}
