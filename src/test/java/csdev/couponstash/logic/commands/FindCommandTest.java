package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.logic.commands.FindCommand.MESSAGE_COUPONS_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.NameContainsKeywordsPredicate;
import csdev.couponstash.testutil.TypicalCoupons;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different coupon -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCouponFound() {
        String expectedMessage = String.format(MESSAGE_COUPONS_FOUND, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCouponList());
    }

    @Test
    public void execute_multipleKeywords_multipleCouponsFound() {
        String expectedMessage = String.format(MESSAGE_COUPONS_FOUND, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);

        expectedModel.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(TypicalCoupons.CARL, TypicalCoupons.FIONA, TypicalCoupons.ELLE),
                model.getFilteredCouponList()
        );
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
