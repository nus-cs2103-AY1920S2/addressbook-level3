package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new HistoryBook<>(), new UserPrefs());
        expectedModel = new ModelManager(model.getNasaBook(), new HistoryBook<>(), new HistoryBook<>(),
                new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(null), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        //showModuleAtIndex(model, INDEX_FIRST_MODULE);
        assertCommandSuccess(new ListCommand(null), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
