package seedu.address.logic.commands.facilitator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FACILITATORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFacilitators.BENSON;
import static seedu.address.testutil.TypicalFacilitators.CARL;
import static seedu.address.testutil.TypicalFacilitators.ELLE;
import static seedu.address.testutil.TypicalFacilitators.FIONA;
import static seedu.address.testutil.TypicalFacilitators.GEORGE;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FacilFindCommand}.
 */
public class FacilFindCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FacilFindCommand findFirstCommand = new FacilFindCommand(firstPredicate);
        FacilFindCommand findSecondCommand = new FacilFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FacilFindCommand findFirstCommandCopy = new FacilFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different facilitator -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFacilitatorFound() {
        String expectedMessage = String.format(MESSAGE_FACILITATORS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FacilFindCommand command = new FacilFindCommand(predicate);
        expectedModel.updateFilteredFacilitatorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFacilitatorList());
    }

    @Test
    public void execute_multipleKeywords_multipleFacilitatorsFound() {
        String expectedMessage = String.format(MESSAGE_FACILITATORS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FacilFindCommand command = new FacilFindCommand(predicate);
        expectedModel.updateFilteredFacilitatorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredFacilitatorList());
    }

    @Test
    public void execute_partialMatchKeywords_multipleFacilitatorsFound() {
        String expectedMessage = String.format(MESSAGE_FACILITATORS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("Be");
        FacilFindCommand command = new FacilFindCommand(predicate);
        expectedModel.updateFilteredFacilitatorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
        assertEquals(Arrays.asList(BENSON, GEORGE), model.getFilteredFacilitatorList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
