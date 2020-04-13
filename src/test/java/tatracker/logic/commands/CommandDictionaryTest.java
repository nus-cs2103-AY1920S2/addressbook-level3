package tatracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.commons.ExitCommand;

public class CommandDictionaryTest {

    @Test
    public void hasFullCommandWord() {
        // Valid entries
        assertTrue(CommandDictionary.hasFullCommandWord("exit")); // Only command word
        assertTrue(CommandDictionary.hasFullCommandWord("session add")); // With sub word

        // Invalid entries
        assertFalse(CommandDictionary.hasFullCommandWord("")); // Blank command word
        assertFalse(CommandDictionary.hasFullCommandWord("garbage")); // Invalid command word
        assertFalse(CommandDictionary.hasFullCommandWord("garbage command")); // Invalid command word with sub word

        assertThrows(NullPointerException.class, () -> CommandDictionary.hasFullCommandWord(null));
    }

    @Test
    public void getDetailsWithFullCommandWord() {
        CommandDetails details = ExitCommand.DETAILS;
        CommandDetails actualDetails = CommandDictionary.getDetailsWithFullCommandWord(
                ExitCommand.DETAILS.getFullCommandWord());
        assertEquals(actualDetails, details);
    }
}
