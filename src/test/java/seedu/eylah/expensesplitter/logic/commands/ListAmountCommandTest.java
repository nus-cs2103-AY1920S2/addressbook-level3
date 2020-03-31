package seedu.eylah.expensesplitter.logic.commands;

import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAmountCommand.
 */
public class ListAmountCommandTest {

    private SplitterModel splitterModel;
    private SplitterModel expectedSplitterModel;

    @BeforeEach
    public void setUp() {
        splitterModel = new SplitterModelManager(new ReceiptBook(), getTypicalPersonAmountBook(), new UserPrefs());
        expectedSplitterModel = new SplitterModelManager(new ReceiptBook(), splitterModel.getPersonAmountBook(),
                new UserPrefs());
    }


    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAmountCommand(), splitterModel, ListAmountCommand.MESSAGE_SUCCESS,
                expectedSplitterModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(splitterModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListAmountCommand(), splitterModel, ListAmountCommand.MESSAGE_SUCCESS,
                expectedSplitterModel);
    }



}
