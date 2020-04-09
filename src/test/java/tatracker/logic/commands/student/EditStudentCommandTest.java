package tatracker.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
import static tatracker.logic.commands.CommandTestUtil.DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertStudentCommandSuccess;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_FIRST_STUDENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_NONEXISTENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_SECOND_STUDENT;
import static tatracker.testutil.TypicalTaTracker.getTypicalGroup;
import static tatracker.testutil.TypicalTaTracker.getTypicalModule;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithStudents;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;
import tatracker.testutil.student.EditStudentDescriptorBuilder;
import tatracker.testutil.student.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditStudentCommand.
 */
public class EditStudentCommandTest {

    private Model model = new ModelManager(getTypicalTaTrackerWithStudents(), new UserPrefs());
    private String typicalGroupCode = getTypicalGroup().getIdentifier();
    private String typicalModuleCode = getTypicalModule().getIdentifier();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(MATRIC_FIRST_STUDENT,
                typicalModuleCode, typicalGroupCode, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                editedStudent.getName().fullName,
                editedStudent.getMatric().value,
                typicalModuleCode,
                typicalGroupCode);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()),
                editedStudent, typicalGroupCode, typicalModuleCode);

        assertStudentCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedStudent = studentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(lastStudent.getMatric(),
                typicalModuleCode, typicalGroupCode, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                editedStudent.getName().fullName,
                editedStudent.getMatric().value,
                typicalModuleCode,
                typicalGroupCode);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.setStudent(lastStudent, editedStudent);

        assertStudentCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditStudentCommand editStudentCommand = new EditStudentCommand(MATRIC_FIRST_STUDENT,
                typicalModuleCode, typicalGroupCode, new EditStudentDescriptor());

        Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());

        String expectedMessage = String.format(MESSAGE_NOT_EDITED);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());

        assertCommandFailure(editStudentCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidMatricUnfilteredList_failure() {
        Matric nonexistentMatric = MATRIC_NONEXISTENT;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(nonexistentMatric,
                typicalModuleCode, typicalGroupCode, descriptor);

        assertCommandFailure(editStudentCommand, model, String.format(
                Messages.MESSAGE_INVALID_STUDENT,
                nonexistentMatric,
                typicalGroupCode,
                typicalModuleCode));
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand = new EditStudentCommand(MATRIC_FIRST_STUDENT,
                typicalModuleCode, typicalGroupCode, DESC_AMY);

        // same values -> returns true
        EditStudentDescriptor copyDescriptor = new EditStudentDescriptor(DESC_AMY);
        EditStudentCommand commandWithSameValues = new EditStudentCommand(MATRIC_FIRST_STUDENT,
                typicalModuleCode, typicalGroupCode, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different matric -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(MATRIC_SECOND_STUDENT,
                typicalModuleCode, typicalGroupCode, DESC_AMY)));

        // different group -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(MATRIC_FIRST_STUDENT,
                typicalModuleCode, new Group("G05").getIdentifier(), DESC_AMY)));

        // different module -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(MATRIC_FIRST_STUDENT,
                new Module("CS3242").getIdentifier(), typicalGroupCode, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(MATRIC_FIRST_STUDENT,
                typicalModuleCode, typicalGroupCode, DESC_BOB)));
    }

}
