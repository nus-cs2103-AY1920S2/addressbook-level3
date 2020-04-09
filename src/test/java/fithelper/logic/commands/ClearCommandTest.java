package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;

import org.junit.jupiter.api.Test;

import fithelper.model.FitHelper;
import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;

public class ClearCommandTest {

    @Test
    public void execute_emptyFitHelper_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFitHelper_success() {
        Model model = new ModelManager(getTypicalFitHelper(), new UserProfile(), new WeightRecords());
        Model expectedModel = new ModelManager(getTypicalFitHelper(), new UserProfile(), new WeightRecords());
        expectedModel.setFitHelper(new FitHelper());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

