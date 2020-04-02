package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.logic.commands.CommandTestUtil.showCouponAtIndex;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.parser.CliSyntax;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;

import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private String expectedMessage;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
        expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsActiveList() {
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "active");
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsArchivedList() {
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "archived");
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ARCHIVED_COUPONS);
        assertCommandSuccess(new ListCommand(CliSyntax.PREFIX_ARCHIVE), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsUsedList() {
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "used");
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_USED_COUPONS);
        assertCommandSuccess(new ListCommand(CliSyntax.PREFIX_USAGE), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsActiveList() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "active");
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ACTIVE_COUPONS);
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsArchivedList() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "archived");
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ARCHIVED_COUPONS);
        assertCommandSuccess(new ListCommand(CliSyntax.PREFIX_ARCHIVE), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsUsedList() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "used");
        expectedModel.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_USED_COUPONS);
        assertCommandSuccess(new ListCommand(CliSyntax.PREFIX_USAGE), model, expectedMessage, expectedModel);
    }

    @Test
    public void testEquals() {
        ListCommand listActive = new ListCommand();
        ListCommand listArchived = new ListCommand(CliSyntax.PREFIX_ARCHIVE);

        // same object -> returns true
        assertTrue(listActive.equals(listActive));

        // same values -> returns true
        ListCommand listActiveCopy = new ListCommand();
        assertTrue(listActive.equals(listActiveCopy));

        // different types -> returns false
        assertFalse(listActive.equals(1));

        // null -> returns false
        assertFalse(listActive.equals(null));

        // different coupon -> returns false
        assertFalse(listActive.equals(listArchived));
    }
}
