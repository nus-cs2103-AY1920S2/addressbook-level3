//@@author fatin99

package tatracker.logic.commands.student;

import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_T04;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithStudents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;
import tatracker.testutil.student.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddStudentCommand}.
 */
public class AddStudentCommandIntegrationTest {

    private Model model;

    private Group testGroup;
    private Module testModule;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTrackerWithStudents(), new UserPrefs());

        testModule = new Module(VALID_MODULE_CS2030);
        testGroup = new Group(VALID_GROUP_T04);

        testModule.addGroup(testGroup);
        model.addModule(testModule);
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        // TODO: Fix Immutability of modules list when using copy constructor [ModelManager]

        Model expectedModel = new ModelManager(getTypicalTaTrackerWithStudents(), new UserPrefs());

        Module expectedModule = new Module(VALID_MODULE_CS2030);
        Group expectedGroup = new Group(VALID_GROUP_T04);

        expectedModule.addGroup(expectedGroup);
        expectedModel.addModule(expectedModule);

        expectedModel.addStudent(validStudent, expectedGroup.getIdentifier(),
                expectedModule.getIdentifier());

        AddStudentCommand command = new AddStudentCommand(
                validStudent, testGroup.getIdentifier(), testModule.getIdentifier());

        String expectedFeedback = String.format(
                AddStudentCommand.MESSAGE_ADD_STUDENT_SUCCESS,
                validStudent.getName(),
                validStudent.getMatric(),
                testModule.getIdentifier(),
                testGroup.getIdentifier());

        assertCommandSuccess(command, model, expectedFeedback, expectedModel, Action.GOTO_STUDENT);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student student = new StudentBuilder().build();
        model.addStudent(student, testGroup.getIdentifier(), testModule.getIdentifier());

        assertCommandFailure(new AddStudentCommand(student, testGroup.getIdentifier(),
                        testModule.getIdentifier()),
                model, MESSAGE_DUPLICATE_STUDENT);
    }

}
