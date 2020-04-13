package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static seedu.address.testutil.TypicalNote.FINISH_CS_2103;
import static seedu.address.testutil.TypicalNote.FINISH_HOMEWORK;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Note;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.NoteBuilder;

public class EditNoteCommandTest {
    private Note sampleEditedNote = FINISH_HOMEWORK;

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditNoteCommand(null, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Note validItem = new NoteBuilder().build();
        ModelStub modelStub = new EditNoteCommandTest.ModelStubWithNote(validItem);

        Index invalidIndex = INDEX_THIRD_ITEM;

        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(validItem).build();

        EditNoteCommand editNoteCommand = new EditNoteCommand(invalidIndex, editNoteDescriptor);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_INDEX, () -> editNoteCommand.execute(modelStub));
    }

    @Test
    public void execute_validIndex_editSuccessful() throws CommandException {
        ModelStubContainingNoteEdited modelStub = new ModelStubContainingNoteEdited();

        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(FINISH_HOMEWORK).build();

        Index validIndex = INDEX_FIRST_ITEM;

        Note toEdit = modelStub.getNoteByIndex(validIndex);
        EditNoteCommand editNoteCommand = new EditNoteCommand(validIndex, editNoteDescriptor);
        assertEquals(Arrays.asList(toEdit), modelStub.notes);

        CommandResult commandResult = editNoteCommand.execute(modelStub);

        assertEquals(String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, sampleEditedNote.getName().fullName),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void create_withNullDescriptorField_editedNote() {
        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptor();

        Index validIndex = INDEX_FIRST_ITEM;
        EditNoteCommand editNoteCommand = new EditNoteCommand(validIndex, editNoteDescriptor);
        assertEquals(sampleEditedNote,
                editNoteCommand.createEditedNote(sampleEditedNote, editNoteDescriptor));
    }

    @Test
    public void equals() {
        Index indexA = Index.fromZeroBased(5);
        Index indexB = Index.fromOneBased(19);

        EditNoteDescriptor editNoteDescriptor = new EditNoteDescriptorBuilder(FINISH_CS_2103).build();

        EditNoteCommand editACommand = new EditNoteCommand(indexA, editNoteDescriptor);
        EditNoteCommand editBCommand = new EditNoteCommand(indexB, editNoteDescriptor);

        // same object -> returns true
        assertTrue(editACommand.equals(editACommand));

        // same value -> returns true
        EditNoteCommand editACommandCopy = new EditNoteCommand(indexA, editNoteDescriptor);
        assertTrue(editACommand.equals(editACommandCopy));

        // different types -> returns false
        assertFalse(editACommand.equals(1));

        // null -> returns false
        assertFalse(editACommand.equals(null));

        // different index -> returns false
        assertFalse(editACommand.equals(editBCommand));
    }

    /**
     * A Model stub that contains a single Note.
     */
    private class ModelStubWithNote extends ModelStub {
        private final Note item;

        ModelStubWithNote(Note item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public boolean hasNote(Note item) {
            requireNonNull(item);
            return this.item.isSame(item);
        }
    }

    /**
     * A Model stub that always contain the Note being edited.
     */
    private class ModelStubContainingNoteEdited extends ModelStub {
        final ArrayList<Note> notes = new ArrayList<>();

        ModelStubContainingNoteEdited() {
            notes.add(new NoteBuilder().build());
        }

        @Override
        public int getNoteListSize() {
            return notes.size();
        }

        @Override
        public Note getNoteByIndex(Index index) {
            return notes.get(index.getZeroBased());
        }

        @Override
        public void addNote(Note note) {
            notes.add(note);
        }

        @Override
        public void setNote(Note target, Note editedNote) {
            int index = notes.indexOf(target);
            notes.set(index, editedNote);
        }
    }
}
