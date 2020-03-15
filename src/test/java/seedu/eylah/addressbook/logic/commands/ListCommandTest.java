package seedu.eylah.addressbook.logic.commands;

import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.eylah.addressbook.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eylah.addressbook.model.Model;
import seedu.eylah.addressbook.model.ModelManager;
import seedu.eylah.addressbook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
