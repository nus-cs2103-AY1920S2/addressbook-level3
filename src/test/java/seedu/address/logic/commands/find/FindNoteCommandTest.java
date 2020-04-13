package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNote.FINISH_CS_2103;
import static seedu.address.testutil.TypicalNote.FINISH_HOMEWORK;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Item;
import seedu.address.model.item.Note;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModelStub;

public class FindNoteCommandTest {

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindNoteCommand(null));
    }

    @Test
    public void execute_emptyPredicate_noNoteFound() {
        FindNoteCommandTest.ModelStubAcceptingNoteAdded modelStub =
                new FindNoteCommandTest.ModelStubAcceptingNoteAdded();
        modelStub.addNote(FINISH_HOMEWORK);
        NameContainsKeywordsPredicate emptyPredicate = preparePredicate(" ");
        modelStub.updateFilteredItemList(emptyPredicate);
        CommandResult commandResult = new FindNoteCommand(emptyPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 0, "Notes"), commandResult.getFeedbackToUser());
        assertEquals(Collections.emptyList(), modelStub.getFilteredNoteList());
    }

    @Test
    public void execute_oneKeyword_oneNoteFound() {
        FindNoteCommandTest.ModelStubAcceptingNoteAdded modelStub =
                new FindNoteCommandTest.ModelStubAcceptingNoteAdded();
        modelStub.addNote(FINISH_HOMEWORK);
        NameContainsKeywordsPredicate onePredicate = preparePredicate("Homework");
        modelStub.updateFilteredItemList(onePredicate);
        CommandResult commandResult = new FindNoteCommand(onePredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 1, "Notes"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(FINISH_HOMEWORK), modelStub.getFilteredNoteList());
    }

    @Test
    public void execute_multipleKeywords_multipleNotesFound() {
        FindNoteCommandTest.ModelStubAcceptingNoteAdded modelStub =
                new FindNoteCommandTest.ModelStubAcceptingNoteAdded();
        modelStub.addNote(FINISH_HOMEWORK);
        modelStub.addNote(FINISH_CS_2103);
        NameContainsKeywordsPredicate twoPredicate = preparePredicate("Finish Homework");
        modelStub.updateFilteredItemList(twoPredicate);
        CommandResult commandResult = new FindNoteCommand(twoPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 2, "Notes"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(FINISH_HOMEWORK, FINISH_CS_2103), modelStub.getFilteredNoteList());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNoteCommand findFirstCommand = new FindNoteCommand(firstPredicate);
        FindNoteCommand findSecondCommand = new FindNoteCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindNoteCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * A Model stub that always accept the Note being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ObservableList<Note> notes = FXCollections.observableArrayList();
        final FilteredList<Note> filteredNote = new FilteredList<>(notes);

        @Override
        public void addNote(Note note) {
            notes.add(note);
            updateFilteredNoteList(PREDICATE_SHOW_ALL_ITEMS);
        }

        @Override
        public ObservableList<Note> getFilteredNoteList() {
            return filteredNote;
        }

        @Override
        public void updateFilteredNoteList(Predicate<Item> predicate) {
            requireNonNull(predicate);
            filteredNote.setPredicate(predicate);
        }
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
