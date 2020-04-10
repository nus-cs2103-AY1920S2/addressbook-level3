package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalFitBiz;

import org.junit.jupiter.api.Test;

import seedu.address.model.ClientInView;
import seedu.address.model.FitBiz;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyFitBiz_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFitBiz_success() {
        Model model = new ModelManager(getTypicalFitBiz(), new UserPrefs(), new ClientInView());
        Model expectedModel = new ModelManager(getTypicalFitBiz(), new UserPrefs(), new ClientInView());
        expectedModel.setFitBiz(new FitBiz());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
