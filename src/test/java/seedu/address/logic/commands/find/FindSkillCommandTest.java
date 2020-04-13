package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSkill.GIT;
import static seedu.address.testutil.TypicalSkill.REACT;

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
import seedu.address.model.item.Skill;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModelStub;

public class FindSkillCommandTest {

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindSkillCommand(null));
    }

    @Test
    public void execute_emptyPredicate_noSkillFound() {
        FindSkillCommandTest.ModelStubAcceptingSkillAdded modelStub =
                new FindSkillCommandTest.ModelStubAcceptingSkillAdded();
        modelStub.addSkill(REACT);
        NameContainsKeywordsPredicate emptyPredicate = preparePredicate(" ");
        modelStub.updateFilteredItemList(emptyPredicate);
        CommandResult commandResult = new FindSkillCommand(emptyPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 0, "Skills"), commandResult.getFeedbackToUser());
        assertEquals(Collections.emptyList(), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_oneKeyword_oneSkillFound() {
        FindSkillCommandTest.ModelStubAcceptingSkillAdded modelStub =
                new FindSkillCommandTest.ModelStubAcceptingSkillAdded();
        modelStub.addSkill(REACT);
        NameContainsKeywordsPredicate onePredicate = preparePredicate("react");
        modelStub.updateFilteredItemList(onePredicate);
        CommandResult commandResult = new FindSkillCommand(onePredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 1, "Skills"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(REACT), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_multipleSkillsFound() {
        FindSkillCommandTest.ModelStubAcceptingSkillAdded modelStub =
                new FindSkillCommandTest.ModelStubAcceptingSkillAdded();
        modelStub.addSkill(REACT);
        modelStub.addSkill(GIT);
        NameContainsKeywordsPredicate twoPredicate = preparePredicate("React Git");
        modelStub.updateFilteredItemList(twoPredicate);
        CommandResult commandResult = new FindSkillCommand(twoPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 2, "Skills"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(REACT, GIT), modelStub.getFilteredItemList());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindSkillCommand findFirstCommand = new FindSkillCommand(firstPredicate);
        FindSkillCommand findSecondCommand = new FindSkillCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindSkillCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * A Model stub that always accept the Skill being added.
     */
    private class ModelStubAcceptingSkillAdded extends ModelStub {
        final ObservableList<Item> skills = FXCollections.observableArrayList();
        final FilteredList<Item> filteredList = new FilteredList<>(skills);

        @Override
        public void addSkill(Skill skill) {
            skills.add(skill);
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
