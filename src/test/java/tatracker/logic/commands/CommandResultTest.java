package tatracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult.Action;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback", Action.NONE);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", Action.NONE)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult == null);

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different", Action.NONE)));

        // different Action value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different", Action.HELP)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback", Action.NONE);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback", Action.NONE)
                .hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different", Action.NONE)
                .hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", Action.HELP)
                .hashCode());
    }
}
