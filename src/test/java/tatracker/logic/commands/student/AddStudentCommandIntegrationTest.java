package tatracker.logic.commands.student;

import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
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

    private Group testGroup = new Group("W17-4");
    private Module testModule = new Module("CS2103T");

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
        model.addModule(testModule);
        model.addGroup(testGroup, testModule);
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        Model expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.addStudent(validStudent);

        assertCommandSuccess(new AddStudentCommand(validStudent, testGroup, testModule), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent), expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = model.getTaTracker().getStudentList().get(0);

        assertCommandFailure(new AddStudentCommand(studentInList, testGroup, testModule),
                model, AddStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

}
