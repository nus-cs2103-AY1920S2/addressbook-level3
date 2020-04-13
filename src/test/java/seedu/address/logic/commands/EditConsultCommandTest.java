package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CONSULT1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CONSULT2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConsults.getTypicalConsultTAble;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.EditConsultCommand;
import seedu.address.logic.parser.consults.ClearConsultCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.StudentTAble;
import seedu.address.testutil.ConsultBuilder;
import seedu.address.testutil.EditConsultDescriptorBuilder;

public class EditConsultCommandTest {

    private Model model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), getTypicalConsultTAble(),
        new TutorialTAble(), new ModTAble(), new ReminderTAble());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws ParseException {
        Consult editedConsult = new ConsultBuilder().build();
        EditConsultCommand.EditConsultDescriptor descriptor = new EditConsultDescriptorBuilder(editedConsult).build();
        EditConsultCommand editConsultCommand = new EditConsultCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditConsultCommand.MESSAGE_EDIT_CONSULT_SUCCESS, editedConsult);

        Model expectedModel = new ModelManager(new StudentTAble(model.getStudentTAble()),
            new UserPrefs(), model.getConsultTAble(), new TutorialTAble(), new ModTAble(), new ReminderTAble());
        expectedModel.setConsult(model.getFilteredConsultList().get(0), editedConsult);

        assertCommandSuccess(editConsultCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditConsultCommand editConsultCommand = new EditConsultCommand(INDEX_FIRST,
            new EditConsultCommand.EditConsultDescriptor());
        Consult editedConsult = model.getFilteredConsultList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditConsultCommand.MESSAGE_EDIT_CONSULT_SUCCESS, editedConsult);

        Model expectedModel = new ModelManager(new StudentTAble(model.getStudentTAble()),
            new UserPrefs(), model.getConsultTAble(), new TutorialTAble(), new ModTAble(), new ReminderTAble());

        assertCommandSuccess(editConsultCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateConsultUnfilteredList_failure() {
        Consult firstConsult = model.getFilteredConsultList().get(INDEX_FIRST.getZeroBased());
        EditConsultCommand.EditConsultDescriptor descriptor = new EditConsultDescriptorBuilder(firstConsult).build();
        EditConsultCommand editStudentCommand = new EditConsultCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editStudentCommand, model, EditConsultCommand.MESSAGE_DUPLICATE_CONSULT);
    }

    @Test
    public void execute_invalidConsultIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConsultList().size() + 1);
        EditConsultCommand.EditConsultDescriptor descriptor = new EditConsultDescriptorBuilder().build();
        EditConsultCommand editConsultCommand = new EditConsultCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editConsultCommand, model, Messages.MESSAGE_INVALID_CONSULT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditConsultCommand standardCommand = new EditConsultCommand(INDEX_FIRST, DESC_CONSULT1);

        // same values -> returns true
        EditConsultCommand.EditConsultDescriptor copyDescriptor =
            new EditConsultCommand.EditConsultDescriptor(DESC_CONSULT1);
        EditConsultCommand commandWithSameValues = new EditConsultCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearConsultCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditConsultCommand(INDEX_SECOND, DESC_CONSULT1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditConsultCommand(INDEX_FIRST, DESC_CONSULT2)));
    }

}
