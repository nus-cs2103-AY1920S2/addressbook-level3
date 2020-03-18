package seedu.zerotoone.logic.commands;

import static seedu.zerotoone.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.zerotoone.testutil.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseList(), new UserPrefs());
        expectedModel = new ModelManager(model.getExerciseList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
