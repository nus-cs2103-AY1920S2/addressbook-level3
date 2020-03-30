package seedu.eylah.expensesplitter.logic.commands;

//import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;

import org.junit.jupiter.api.BeforeEach;

import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.model.ModelManager;
import seedu.eylah.expensesplitter.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAmountCommand.
 */
public class ListAmountCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager (null, getTypicalPersonAmountBook(), new UserPrefs());
        expectedModel = new ModelManager(null, model.getPersonAmountBook(), new UserPrefs());
    }

    /*
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAmountCommand(), model, ListAmountCommand.MESSAGE_SUCCESS, expectedModel);
    }

     */

}
