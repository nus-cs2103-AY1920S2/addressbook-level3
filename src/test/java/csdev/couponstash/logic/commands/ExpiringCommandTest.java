package csdev.couponstash.logic.commands;

import static csdev.couponstash.commons.core.Messages.MESSAGE_COUPONS_EXPIRING_ON_DATE;
import static csdev.couponstash.commons.util.DateUtil.YEAR_MONTH_FORMATTER;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.DateIsEqualsPredicate;
import csdev.couponstash.model.coupon.DateIsInMonthYearPredicate;
import csdev.couponstash.testutil.TypicalCoupons;

/**
 * Contains integration tests (interaction with the Model) for {@code ExpiringCommand}.
 */
public class ExpiringCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void equalsDatePredicate() {
        DateIsEqualsPredicate firstPredicate =
                new DateIsEqualsPredicate("30-8-2020");
        DateIsEqualsPredicate secondPredicate =
                new DateIsEqualsPredicate("31-12-2020");

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
    public void equalsMonthYearPredicate() {
        DateIsInMonthYearPredicate firstPredicate =
                new DateIsInMonthYearPredicate(YearMonth.parse("8-2020", YEAR_MONTH_FORMATTER));
        DateIsInMonthYearPredicate secondPredicate =
                new DateIsInMonthYearPredicate(YearMonth.parse("12-2020", YEAR_MONTH_FORMATTER));

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
    public void execute_noCouponsFoundOnDate_success() {
        String inputDate = "1-6-2020";
        String expectedMessage = String.format(Messages.MESSAGE_COUPONS_EXPIRING_ON_DATE, 0, inputDate);
        DateIsEqualsPredicate predicate = prepareDatePredicate(inputDate);
        ExpiringCommand command = new ExpiringCommand(predicate);
        expectedModel.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCouponList());
    }

    @Test
    public void execute_multipleCouponsFoundOnDate_success() {
        String inputDate = "30-8-2020";
        String expectedMessage = String.format(MESSAGE_COUPONS_EXPIRING_ON_DATE, 3, inputDate);
        DateIsEqualsPredicate predicate = prepareDatePredicate(inputDate);
        ExpiringCommand command = new ExpiringCommand(predicate);
        expectedModel.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalCoupons.CARL, TypicalCoupons.FIONA, TypicalCoupons.ELLE),
                model.getFilteredCouponList());
    }

    @Test
    public void execute_noCouponsFoundInMonth_success() {
        String inputDate = "6-2020";
        String expectedMessage = String.format(Messages.MESSAGE_COUPONS_EXPIRING_DURING_YEAR_MONTH, 0, inputDate);
        DateIsInMonthYearPredicate predicate = prepareYearMonthPredicate(inputDate);
        ExpiringCommand command = new ExpiringCommand(predicate);
        expectedModel.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCouponList());
    }

    @Test
    public void execute_multipleCouponsFoundInMonth_success() {
        String inputDate = "8-2020";
        String expectedMessage = String.format(Messages.MESSAGE_COUPONS_EXPIRING_DURING_YEAR_MONTH,
                3, inputDate);
        DateIsInMonthYearPredicate predicate = prepareYearMonthPredicate(inputDate);
        ExpiringCommand command = new ExpiringCommand(predicate);

        expectedModel.sortCoupons(Model.COMPARATOR_NON_ARCHIVED_FIRST);
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_COUPONS);
        expectedModel.updateFilteredCouponList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalCoupons.CARL, TypicalCoupons.FIONA, TypicalCoupons.ELLE),
                model.getFilteredCouponList());
    }

    /**
     * Parses {@code userInput} into a {@code DateIsEqualsPredicate}.
     */
    private DateIsEqualsPredicate prepareDatePredicate(String userInput) {
        return new DateIsEqualsPredicate(userInput);
    }

    /**
     * Parses {@code userInput} into a {@code DateIsInMonthYearPredicate}.
     */
    private DateIsInMonthYearPredicate prepareYearMonthPredicate(String userInput) {
        return new DateIsInMonthYearPredicate(YearMonth.parse(userInput, YEAR_MONTH_FORMATTER));
    }
}
