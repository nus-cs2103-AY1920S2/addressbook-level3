package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternship.NINJA_VAN;
import static seedu.address.testutil.TypicalInternship.PAYPAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Item;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModelStub;

public class FindInternshipCommandTest {

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindInternshipCommand(null));
    }

    @Test
    public void execute_emptyPredicate_noInternshipFound() {
        FindInternshipCommandTest.ModelStubAcceptingInternshipAdded modelStub =
                new FindInternshipCommandTest.ModelStubAcceptingInternshipAdded();
        modelStub.addInternship(NINJA_VAN);
        NameContainsKeywordsPredicate emptyPredicate = preparePredicate(" ");
        modelStub.updateFilteredItemList(emptyPredicate);
        CommandResult commandResult = new FindInternshipCommand(emptyPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 0, "Internships"), commandResult.getFeedbackToUser());
        assertEquals(Collections.emptyList(), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_oneKeyword_oneInternshipFound() {
        FindInternshipCommandTest.ModelStubAcceptingInternshipAdded modelStub =
                new FindInternshipCommandTest.ModelStubAcceptingInternshipAdded();
        modelStub.addInternship(NINJA_VAN);
        NameContainsKeywordsPredicate onePredicate = preparePredicate("Ninja");
        modelStub.updateFilteredItemList(onePredicate);
        CommandResult commandResult = new FindInternshipCommand(onePredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 1, "Internships"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(NINJA_VAN), modelStub.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_multipleInternshipsFound() {
        FindInternshipCommandTest.ModelStubAcceptingInternshipAdded modelStub =
                new FindInternshipCommandTest.ModelStubAcceptingInternshipAdded();
        modelStub.addInternship(NINJA_VAN);
        modelStub.addInternship(PAYPAL);
        NameContainsKeywordsPredicate twoPredicate = preparePredicate("Van PayPal");
        modelStub.updateFilteredItemList(twoPredicate);
        CommandResult commandResult = new FindInternshipCommand(twoPredicate).execute(modelStub);
        assertEquals(String.format(Messages.MESSAGE_ITEMS_LISTED, 2, "Internships"), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(NINJA_VAN, PAYPAL), modelStub.getFilteredItemList());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindInternshipCommand findFirstCommand = new FindInternshipCommand(firstPredicate);
        FindInternshipCommand findSecondCommand = new FindInternshipCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindInternshipCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * A Model stub that always accept the Internship being added.
     */
    private class ModelStubAcceptingInternshipAdded extends ModelStub {
        final ObservableList<Item> internships = FXCollections.observableArrayList();
        final FilteredList<Item> filteredList = new FilteredList<>(internships);

        @Override
        public void addInternship(Internship internship) {
            internships.add(internship);
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
