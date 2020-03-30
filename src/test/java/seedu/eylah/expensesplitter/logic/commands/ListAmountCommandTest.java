package seedu.eylah.expensesplitter.logic.commands;

import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.model.ModelManager;
import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAmountCommand.
 */
public class ListAmountCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager (new ReceiptBook(), getTypicalPersonAmountBook(), new UserPrefs());
        expectedModel = new ModelManager(new ReceiptBook(), model.getPersonAmountBook(), new UserPrefs());
    }


    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAmountCommand(), model, ListAmountCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListAmountCommand(), model, ListAmountCommand.MESSAGE_SUCCESS, expectedModel);
    }



}
