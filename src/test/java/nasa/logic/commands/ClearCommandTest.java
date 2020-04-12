package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import org.junit.jupiter.api.Test;

import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyNasaBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyNasaBook_success() {
        Model model = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new HistoryBook<>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new HistoryBook<>(),
                new UserPrefs());
        expectedModel.setNasaBook(new NasaBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
