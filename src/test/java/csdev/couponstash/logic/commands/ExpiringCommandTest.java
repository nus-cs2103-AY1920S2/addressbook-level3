package csdev.couponstash.logic.commands;

import static csdev.couponstash.commons.core.Messages.MESSAGE_COUPONS_EXPIRING_BEFORE_DATE;
import static csdev.couponstash.commons.core.Messages.MESSAGE_COUPONS_LISTED_OVERVIEW;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.DateIsBeforePredicate;
import csdev.couponstash.testutil.TypicalCoupons;

/**
 * Contains integration tests (interaction with the Model) for {@code ExpiringCommand}.
 */
public class ExpiringCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void equals() {
        DateIsBeforePredicate firstPredicate =
                new DateIsBeforePredicate("30-8-2020");
        DateIsBeforePredicate secondPredicate =
                new DateIsBeforePredicate("31-12-2020");

        ExpiringCommand expiringFirstCommand = new ExpiringCommand(firstPredicate);
        ExpiringCommand expiringSecondCommand = new ExpiringCommand(secondPredicate);

        // same object -> returns true
        assertTrue(expiringFirstCommand.equals(expiringFirstCommand));

        // same values -> returns true
        ExpiringCommand expiringFirstCommandCopy = new ExpiringCommand(firstPredicate);
        assertTrue(expiringFirstCommand.equals(expiringFirstCommandCopy));

        // different types -> returns false
        assertFalse(expiringFirstCommand.equals(1));

        // null -> returns false
        assertFalse(expiringFirstCommand == null);

        // different coupon -> returns false
        assertFalse(expiringFirstCommand.equals(expiringSecondCommand));
    }

    @Test
    public void execute_noCouponsFound_success() {
        String inputDate = "1-6-2020";
        String expectedMessage = String.format(MESSAGE_COUPONS_LISTED_OVERVIEW, 0) + " Try a later date!";
        DateIsBeforePredicate predicate = preparePredicate(inputDate);
        ExpiringCommand command = new ExpiringCommand(predicate);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCouponList());
    }

    @Test
    public void execute_multipleCouponsFound_success() {
        String inputDate = "1-12-2020";
        String expectedMessage = String.format(MESSAGE_COUPONS_LISTED_OVERVIEW, 3)
                + " " + String.format(MESSAGE_COUPONS_EXPIRING_BEFORE_DATE, inputDate);
        DateIsBeforePredicate predicate = preparePredicate(inputDate);
        ExpiringCommand command = new ExpiringCommand(predicate);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalCoupons.CARL, TypicalCoupons.ELLE, TypicalCoupons.FIONA),
                model.getFilteredCouponList());
    }

    /**
     * Parses {@code userInput} into a {@code DateIsBeforePredicate}.
     */
    private DateIsBeforePredicate preparePredicate(String userInput) {
        return new DateIsBeforePredicate(userInput);
    }
}
