package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalResume.ME_RESUME;
import static seedu.address.testutil.TypicalResume.SE_RESUME;

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
import seedu.address.model.item.Resume;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModelStub;

public class FindResumeCommandTest {

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindResumeCommand(null));
    }

    @Test
    public void execute_emptyPredicate_noResumeFound() {
        FindResumeCommandTest.ModelStubAcceptingResumeAdded modelStub =
                new FindResumeCommandTest.ModelStubAcceptingResumeAdded();
        modelStub.addResume(SE_RESUME);
        NameContainsKeywordsPredicate emptyPredicate = preparePredicate(" ");
        modelStub.updateFilteredItemList(emptyPredicate);
        CommandResult commandResult = new FindResumeCommand(emptyPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 0, "Resumes"), commandResult.getFeedbackToUser());
        assertEquals(Collections.emptyList(), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_oneResumeFound() {
        FindResumeCommandTest.ModelStubAcceptingResumeAdded modelStub =
                new FindResumeCommandTest.ModelStubAcceptingResumeAdded();
        modelStub.addResume(SE_RESUME);
        NameContainsKeywordsPredicate onePredicate = preparePredicate("Software Engineering");
        modelStub.updateFilteredItemList(onePredicate);
        CommandResult commandResult = new FindResumeCommand(onePredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 1, "Resumes"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(SE_RESUME), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_oneKeyword_multipleResumesFound() {
        FindResumeCommandTest.ModelStubAcceptingResumeAdded modelStub =
                new FindResumeCommandTest.ModelStubAcceptingResumeAdded();
        modelStub.addResume(SE_RESUME);
        modelStub.addResume(ME_RESUME);
        NameContainsKeywordsPredicate twoPredicate = preparePredicate("Engineering");
        modelStub.updateFilteredItemList(twoPredicate);
        CommandResult commandResult = new FindResumeCommand(twoPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 2, "Resumes"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(SE_RESUME, ME_RESUME), modelStub.getFilteredItemList());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindResumeCommand findFirstCommand = new FindResumeCommand(firstPredicate);
        FindResumeCommand findSecondCommand = new FindResumeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindResumeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * A Model stub that always accept the Resume being added.
     */
    private class ModelStubAcceptingResumeAdded extends ModelStub {
        final ObservableList<Item> resumes = FXCollections.observableArrayList();
        final FilteredList<Item> filteredList = new FilteredList<>(resumes);

        @Override
        public void addResume(Resume resume) {
            resumes.add(resume);
            updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            return filteredList;
        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {
            requireNonNull(predicate);
            filteredList.setPredicate(predicate);
        }
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
