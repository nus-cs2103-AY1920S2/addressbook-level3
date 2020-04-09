package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.logic.commands.CommandTestUtil.showEntryAtIndex;
import static fithelper.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;

import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFitHelper(), new UserProfile(), new WeightRecords());
        expectedModel = new ModelManager(model.getFitHelper(), new UserProfile(), new WeightRecords());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(null), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);
        assertCommandSuccess(new ListCommand(null), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

