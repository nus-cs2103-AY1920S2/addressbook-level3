package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.testutil.TypicalCoupons;

class SortCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_sortByExpiry() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, PREFIX_EXPIRY_DATE);
        SortCommand command = new SortCommand(PREFIX_EXPIRY_DATE);

        expectedModel.sortCoupons(SortCommand.EXPIRY_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_sortByName() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, PREFIX_NAME);
        SortCommand command = new SortCommand(PREFIX_NAME);

        expectedModel.sortCoupons(SortCommand.NAME_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_sortByRemind() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, PREFIX_REMIND);
        SortCommand command = new SortCommand(PREFIX_REMIND);

        expectedModel.sortCoupons(SortCommand.REMINDER_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.BENSON,
                TypicalCoupons.ALICE,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void testEquals() {
        SortCommand sortByName = new SortCommand(PREFIX_NAME);
        SortCommand sortByExpiry = new SortCommand(PREFIX_EXPIRY_DATE);

        // same object -> returns true
        assertTrue(sortByName.equals(sortByName));

        // same values -> returns true
        SortCommand sortByNameCopy = new SortCommand(PREFIX_NAME);
        assertTrue(sortByName.equals(sortByNameCopy));

        // different types -> returns false
        assertFalse(sortByName.equals(1));

        // null -> returns false
        assertFalse(sortByName.equals(null));

        // different coupon -> returns false
        assertFalse(sortByName.equals(sortByExpiry));
    }
}
