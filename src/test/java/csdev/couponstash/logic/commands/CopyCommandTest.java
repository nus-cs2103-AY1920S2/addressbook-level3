package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.logic.commands.CommandTestUtil.showCouponAtIndex;
import static csdev.couponstash.testutil.TypicalCoupons.AMY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.CouponStashParser;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.MoneySymbolStub;
import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.TypicalIndexes;

/**
 * Contains unit tests for {@code CopyCommand}.
 */
public class CopyCommandTest {

    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Coupon couponToCopy = model.getFilteredCouponList().get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        CopyCommand copyCommand = new CopyCommand(TypicalIndexes.INDEX_FIRST_COUPON);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_COUPON_SUCCESS, couponToCopy.getName());
        ModelManager expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        try {
            assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
        } catch (HeadlessException he) {
            //Catching Headless Exception on Travis CI because Travis has no keyboard
            assertEquals(1, 1);
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCouponList().size() + 1);
        CopyCommand copyCommand = new CopyCommand(outOfBoundIndex);
        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredListWithNoChange_success() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);
        Coupon couponToCopy = model.getFilteredCouponList().get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        CopyCommand copyCommand = new CopyCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_COUPON_SUCCESS, couponToCopy.getName());
        Model expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        showCouponAtIndex(expectedModel, TypicalIndexes.INDEX_FIRST_COUPON);
        try {
            assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
        } catch (HeadlessException he) {
            //Catching Headless Exception on Travis CI because Travis has no keyboard
            assertEquals(1, 1);
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        // ensures that outOfBoundIndex is still in bounds of CouponStash list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCouponStash().getCouponList().size());

        CopyCommand copyCommand = new CopyCommand(outOfBoundIndex);
        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_messageCopiedToClipboard_equalsToActualMessage() {
        Coupon couponToCopy = model.getFilteredCouponList().get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        CopyCommand copyCommand = new CopyCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        String actualMessage = copyCommand.getCopiedString(couponToCopy);
        try {
            copyCommand.execute(model);
            assertEquals(getClipboardContent(), actualMessage);
        } catch (HeadlessException he) {
            //Catching Headless Exception on Travis CI because Travis has no keyboard
            assertEquals(1, 1);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_couponFromCopyCommand_validAddCommand() {
        ModelManager expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        Coupon expectedCoupon = new CouponBuilder(AMY).build();
        model.addCoupon(expectedCoupon, "");
        Index lastIndex = Index.fromZeroBased(model.getFilteredCouponList().size() - 1);
        CopyCommand copyCommand = new CopyCommand(lastIndex);
        Coupon couponToCopy = model.getFilteredCouponList().get(lastIndex.getZeroBased());
        String exportString = copyCommand.getCopiedString(couponToCopy);
        CouponStashParser command = new CouponStashParser(
                new MoneySymbolStub("$"));
        try {
            command.parseCommand(exportString).execute(expectedModel);
        } catch (ParseException pe) {
            throw new AssertionError("Execution of command should not fail.", pe);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
        Coupon actualCoupon = expectedModel.getFilteredCouponList().get(lastIndex.getZeroBased());
        assertTrue(actualCoupon.isSameCoupon(expectedCoupon));
    }

    @Test
    public void equals() {
        CopyCommand exportFirstCommand = new CopyCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        CopyCommand exportSecondCommand = new CopyCommand(TypicalIndexes.INDEX_SECOND_COUPON);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        CopyCommand exportFirstCommandCopy = new CopyCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    public static String getClipboardContent() {
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor dataFlavor = DataFlavor.stringFlavor;
        try {
            if (systemClipboard.isDataFlavorAvailable(dataFlavor)) {
                Object text = systemClipboard.getData(dataFlavor);
                return (String) text;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
