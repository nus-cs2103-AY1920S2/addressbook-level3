package tatracker.logic.commands.student;

import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_T04;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static tatracker.logic.commands.CommandTestUtil.assertAddStudentCommandSuccess;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.testutil.TypicalStudents.getTypicalTaTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;
import tatracker.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddStudentCommand}.
 */
public class AddStudentCommandIntegrationTest {

    private Model model;

    private Group testGroup;
    private Module testModule;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());

        testModule = new Module(VALID_MODULE_CS2030);
        testGroup = new Group(VALID_GROUP_T04);

        testModule.addGroup(testGroup);
        model.addModule(testModule);
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        // TODO: Fix Immutability of modules list when using copy constructor [ModelManager]

        Model expectedModel = new ModelManager(getTypicalTaTracker(), new UserPrefs());

        Module expectedModule = new Module(VALID_MODULE_CS2030);
        Group expectedGroup = new Group(VALID_GROUP_T04);

        expectedModule.addGroup(expectedGroup);
        expectedModel.addModule(expectedModule);

        expectedModel.addStudent(validStudent, expectedGroup.getIdentifier(),
                expectedModule.getIdentifier());

        assertAddStudentCommandSuccess(new AddStudentCommand(validStudent,
                        testGroup.getIdentifier(), testModule.getIdentifier()), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent,
                        testModule, testGroup), expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student student = new StudentBuilder().build();
        model.addStudent(student, testGroup.getIdentifier(), testModule.getIdentifier());

        assertCommandFailure(new AddStudentCommand(student, testGroup.getIdentifier(),
                        testModule.getIdentifier()),
                model, AddStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

}
