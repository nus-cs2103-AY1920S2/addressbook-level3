package seedu.address.logic.commands.student;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.students.ListStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;

/**
 * Contains integration tests (interaction with the Model) and unit tests for listStudentCommand.
 */
public class ListStudentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
        expectedModel = new ModelManager(model.getStudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListStudentCommand(), model, ListStudentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListStudentCommand(), model, ListStudentCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
