package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProject.DUKE;
import static seedu.address.testutil.TypicalProject.ORBITAL;

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
import seedu.address.model.item.Project;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModelStub;

public class FindProjectCommandTest {

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindProjectCommand(null));
    }

    @Test
    public void execute_emptyPredicate_noProjectFound() {
        FindProjectCommandTest.ModelStubAcceptingProjectAdded modelStub =
                new FindProjectCommandTest.ModelStubAcceptingProjectAdded();
        modelStub.addProject(ORBITAL);
        NameContainsKeywordsPredicate emptyPredicate = preparePredicate(" ");
        modelStub.updateFilteredItemList(emptyPredicate);
        CommandResult commandResult = new FindProjectCommand(emptyPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 0, "Projects"), commandResult.getFeedbackToUser());
        assertEquals(Collections.emptyList(), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_oneKeyword_oneProjectFound() {
        FindProjectCommandTest.ModelStubAcceptingProjectAdded modelStub =
                new FindProjectCommandTest.ModelStubAcceptingProjectAdded();
        modelStub.addProject(DUKE);
        NameContainsKeywordsPredicate onePredicate = preparePredicate("Duke");
        modelStub.updateFilteredItemList(onePredicate);
        CommandResult commandResult = new FindProjectCommand(onePredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 1, "Projects"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(DUKE), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_oneProjectFound() {
        FindProjectCommandTest.ModelStubAcceptingProjectAdded modelStub =
                new FindProjectCommandTest.ModelStubAcceptingProjectAdded();
        modelStub.addProject(ORBITAL);
        modelStub.addProject(DUKE);
        NameContainsKeywordsPredicate twoPredicate = preparePredicate("Orbital Resume");
        modelStub.updateFilteredItemList(twoPredicate);
        CommandResult commandResult = new FindProjectCommand(twoPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 1, "Projects"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(ORBITAL), modelStub.getFilteredItemList());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindProjectCommand findFirstCommand = new FindProjectCommand(firstPredicate);
        FindProjectCommand findSecondCommand = new FindProjectCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindProjectCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * A Model stub that always accept the Project being added.
     */
    private class ModelStubAcceptingProjectAdded extends ModelStub {
        final ObservableList<Item> projects = FXCollections.observableArrayList();
        final FilteredList<Item> filteredList = new FilteredList<>(projects);

        @Override
        public void addProject(Project project) {
            projects.add(project);
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
