package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SuggestCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SuggestCommand firstSuggestCommand = new SuggestCommand("person");
        SuggestCommand secondSuggestCommand = new SuggestCommand("place");

        // same object -> returns true
        assertTrue(firstSuggestCommand.equals(firstSuggestCommand));

        // same values -> returns true
        SuggestCommand firstSuggestCommandCopy = new SuggestCommand("person");
        assertTrue(firstSuggestCommand.equals(firstSuggestCommandCopy));

        // different types -> returns false
        assertFalse(firstSuggestCommand.equals(1));

        // null -> returns false
        assertFalse(firstSuggestCommand.equals(null));

        // different file -> returns false
        assertFalse(firstSuggestCommand.equals(secondSuggestCommand));
    }

    @Test
    public void execute_zeroParameters_noSuggestionParameterProvided() {
        String emptyParameter = "";
        SuggestCommand suggestCommand = new SuggestCommand(emptyParameter);
        String expectedMessage = SuggestCommand.MESSAGE_INVALID_PARAMETER;
        assertCommandFailure(suggestCommand, model, expectedMessage);
    }

    @Test
    public void execute_wrongParameters_notSuggestionKeyword() {
        String wrongParameter = "wrong";
        SuggestCommand suggestCommand = new SuggestCommand(wrongParameter);
        String expectedMessage = SuggestCommand.MESSAGE_INVALID_PARAMETER;
        assertCommandFailure(suggestCommand, model, expectedMessage);
    }

    @Test
    public void execute_person_personSuggested() {
        SuggestCommand suggestCommand = new SuggestCommand("person");
        String expectedMessage = SuggestCommand.MESSAGE_PERSON_SUGGEST_SUCCESS;
        expectedModel.suggestPerson();
        assertCommandSuccess(suggestCommand, model, expectedMessage, expectedModel);
    }

}
