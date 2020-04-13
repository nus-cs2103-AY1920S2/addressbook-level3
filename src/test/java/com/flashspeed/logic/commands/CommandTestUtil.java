package com.flashspeed.logic.commands;

import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Library;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.Deck;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DECK_NAME_KOREAN_1 = "Korean 1";
    public static final String VALID_DECK_NAME_KOREAN_2 = "Korean 2";

    public static final String VALID_CARD_NAME_KOREAN_1_HELLO = "안녕하세요 : hello";
    public static final String VALID_CARD_NAME_KOREAN_1_YES = "네 : yes";
    public static final String VALID_CARD_NAME_KOREAN_1_NO = "아니요 : no";
    public static final String VALID_CARD_NAME_KOREAN_2_WEEKENDS = "주말 : weekends";
    public static final String VALID_CARD_NAME_KOREAN_2_WEEKDAYS = "평일 : weekdays";
    public static final String VALID_CARD_NAME_KOREAN_2_SUNDAY = "일요일 : sunday";

    public static final String INVALID_DECK_NAME = " ";

    public static final String INVALID_CARD_NAME_COLONS_FRONT = ":안녕하세요 : hello";
    public static final String INVALID_CARD_NAME_COLONS_BACK = "안녕하세요 : hello:";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Library expectedLibrary = new Library(actualModel.getLibrary());
        List<Deck> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDeckList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedLibrary, actualModel.getLibrary());
        assertEquals(expectedFilteredList, actualModel.getFilteredDeckList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the deck at the given {@code targetIndex} in the
     * {@code model}'s library.
     */
    public static void showDeckAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeckList().size());

        Deck deck = model.getFilteredDeckList().get(targetIndex.getZeroBased());
        final String deckName = deck.getName().toString();
        model.updateFilteredDeckList((x) -> deckName.equals(x) ? true : false);

        assertEquals(1, model.getFilteredDeckList().size());
    }
}
