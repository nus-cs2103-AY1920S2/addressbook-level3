//@@author fatin99

package tatracker.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static tatracker.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.ModelStub.ModelStubAcceptingStudentAdded;
import tatracker.model.ModelStub.ModelStubWithStudent;
import tatracker.model.student.Student;
import tatracker.testutil.student.StudentBuilder;

public class AddStudentCommandTest {

    private Student testStudent = new StudentBuilder().build();
    private String testGroup = "W17-4";
    private String testModule = "CS2103T";

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null, testGroup, testModule));
    }

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(testStudent, null, testModule));
    }

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(testStudent, testGroup, null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddStudentCommand(validStudent, testGroup, testModule).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_ADD_STUDENT_SUCCESS,
                validStudent.getName().fullName,
                validStudent.getMatric().value,
                testModule,
                testGroup),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(validStudent, testGroup, testModule);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_STUDENT, ()
            -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice, testGroup, testModule);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob, testGroup, testModule);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice, testGroup, testModule);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand == null);

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
