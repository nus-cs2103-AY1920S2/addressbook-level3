package tatracker.logic.commands.student;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static tatracker.logic.commands.CommandTestUtil.DESC_AMY;
// import static tatracker.logic.commands.CommandTestUtil.DESC_BOB;
// import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
// import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
// import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
// import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
// import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
// import static tatracker.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
// import static tatracker.testutil.TypicalStudents.getTypicalTaTracker;
//
// import org.junit.jupiter.api.Test;
//
// import tatracker.commons.core.Messages;
// import tatracker.commons.core.index.Index;
// import tatracker.logic.commands.ClearCommand;
// import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
// import tatracker.model.Model;
// import tatracker.model.ModelManager;
// import tatracker.model.TaTracker;
// import tatracker.model.UserPrefs;
// import tatracker.model.student.Student;
// import tatracker.testutil.EditStudentDescriptorBuilder;
// import tatracker.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditStudentCommand.
 */
public class EditStudentCommandTest {

    // private Model model = new ModelManager(getTypicalTaTracker(), new UserPrefs());

    // @Test
    // public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //     Student editedStudent = new StudentBuilder().build();
    //     EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
    //     EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_STUDENT, descriptor);
    //
    //     String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);
    //
    //     Model expectedModel = new ModelManager(new TaTracker(model.getTaTracker()), new UserPrefs());
    //     expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);
    //
    //     assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    // public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //     Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
    //     Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());
    //
    //     StudentBuilder studentInList = new StudentBuilder(lastStudent);
    //     Student editedStudent = studentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //             .withTags(VALID_TAG_HUSBAND).build();
    //
    //     EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
    //             .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
    //     EditStudentCommand editStudentCommand = new EditStudentCommand(indexLastStudent, descriptor);
    //
    //     String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);
    //
    //     Model expectedModel = new ModelManager(new TaTracker(model.getTaTracker()), new UserPrefs());
    //     expectedModel.setStudent(lastStudent, editedStudent);
    //
    //     assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    // public void execute_noFieldSpecifiedUnfilteredList_success() {
    //     EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_STUDENT,
    //             new EditStudentDescriptor());
    //     Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
    //
    //     String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);
    //
    //     Model expectedModel = new ModelManager(new TaTracker(model.getTaTracker()), new UserPrefs());
    //
    //     assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    // }

    /*
    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withName(VALID_NAME_BOB).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_STUDENT,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new TaTracker(model.getTaTracker()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }*/

    // @Test
    // public void execute_duplicateStudentUnfilteredList_failure() {
    //     Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
    //     EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
    //     EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_SECOND_STUDENT, descriptor);
    //
    //     assertCommandFailure(editStudentCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    // }

    /*
    @Test
    public void execute_duplicateStudentFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        // edit student in filtered list into a duplicate in TA-Tracker
        //Student studentInList = model.getTaTracker().getStudentList().get(INDEX_SECOND_STUDENT.getZeroBased());
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_STUDENT,
                new EditStudentDescriptorBuilder(studentInList).build());

        assertCommandFailure(editStudentCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }*/

    // @Test
    // public void execute_invalidStudentIndexUnfilteredList_failure() {
    //     Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
    //     EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
    //     EditStudentCommand editStudentCommand = new EditStudentCommand(outOfBoundIndex, descriptor);
    //
    //     assertCommandFailure(editStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    // }

    // /**
    //  * Edit filtered list where index is larger than size of filtered list,
    //  * but smaller than size of TA-Tracker
    //  */
    // @Test
    // public void execute_invalidStudentIndexFilteredList_failure() {
    //     showStudentAtIndex(model, INDEX_FIRST_STUDENT);
    //     Index outOfBoundIndex = INDEX_SECOND_STUDENT;
    //     // ensures that outOfBoundIndex is still in bounds of TA-Tracker list
    //     assertTrue(outOfBoundIndex.getZeroBased() < model.getTaTracker().getStudentList().size());
    //
    //     EditStudentCommand editStudentCommand = new EditStudentCommand(outOfBoundIndex,
    //             new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //     assertCommandFailure(editStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    // }

    // @Test
    // public void equals() {
    //     final EditStudentCommand standardCommand = new EditStudentCommand(INDEX_FIRST_STUDENT, DESC_AMY);
    //
    //     // same values -> returns true
    //     EditStudentDescriptor copyDescriptor = new EditStudentDescriptor(DESC_AMY);
    //     EditStudentCommand commandWithSameValues = new EditStudentCommand(INDEX_FIRST_STUDENT, copyDescriptor);
    //     assertTrue(standardCommand.equals(commandWithSameValues));
    //
    //     // same object -> returns true
    //     assertTrue(standardCommand.equals(standardCommand));
    //
    //     // null -> returns false
    //     assertFalse(standardCommand.equals(null));
    //
    //     // different types -> returns false
    //     assertFalse(standardCommand.equals(new ClearCommand()));
    //
    //     // different index -> returns false
    //     assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_SECOND_STUDENT, DESC_AMY)));
    //
    //     // different descriptor -> returns false
    //     assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_FIRST_STUDENT, DESC_BOB)));
    // }

}
