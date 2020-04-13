package seedu.nova.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.testutil.TypicalPersons.CARL;
import static seedu.nova.testutil.TypicalPersons.ELLE;
import static seedu.nova.testutil.TypicalPersons.FIONA;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbFindCommand;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code AbFindCommand}.
 */
public class AbFindCommandTest {

    private final Nova nova = new Nova();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        AbFindCommand findFirstCommand = new AbFindCommand(firstPredicate);
        AbFindCommand findSecondCommand = new AbFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        AbFindCommand findFirstCommandCopy = new AbFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());
        Model expectedModel = new ModelManager(nova, new UserPrefs());

        String expectedMessage = "Found the following: \n";
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        AbFindCommand command = new AbFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());
        Model expectedModel = new ModelManager(nova, new UserPrefs());

        String expectedMessage = "Found the following: \n"
                + "1. Carl Kurz, Phone: 95352563, Email: heinz@example.com, Category: classmate\n"
                + "2. Elle Meyer, Phone: 9482224, Email: werner@example.com, Category: classmate\n"
                + "3. Fiona Kunz, Phone: 9482427, Email: lydia@example.com, Category: classmate\n";
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        AbFindCommand command = new AbFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */

    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
