package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandFailure;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;
import fithelper.model.entry.Entry;
import fithelper.testutil.EntryBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFitHelper(), new UserProfile(), new WeightRecords());
    }

    @Test
    public void execute_newEntry_success() {
        Entry validEntry = new EntryBuilder().build();

        Model expectedModel = new ModelManager(model.getFitHelper(), model.getUserProfile(), model.getWeightRecords());
        expectedModel.addEntry(validEntry);

        CommandTestUtil.assertCommandSuccess(new AddCommand(validEntry), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validEntry), expectedModel);
    }

    @Test
    public void execute_duplicateEntry_throwsCommandException() {
        assertCommandFailure(new AddCommand(model.getFitHelper().getFoodList().get(0)), model,
                AddCommand.MESSAGE_DUPLICATE_ENTRY);
    }

}
