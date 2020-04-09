package csdev.couponstash.logic.commands;

import static csdev.couponstash.commons.util.DateUtil.MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE;
import static csdev.couponstash.commons.util.DateUtil.MESSAGE_START_DATE_EXPIRY_DATE_CONSTRAINT;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.EditCommand.EditCouponDescriptor;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.EditCouponDescriptorBuilder;
import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedExceptUsageUnfilteredList_success() {
        Coupon comparedCoupon = model.getFilteredCouponList().get(0);
        Coupon editedCoupon = new CouponBuilder()
                .withUsage(comparedCoupon.getUsage().value)
                .build();
        EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(editedCoupon).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());

        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(comparedCoupon, editedCoupon, "");
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCoupon = Index.fromOneBased(model.getFilteredCouponList().size());
        Coupon lastCoupon = model.getFilteredCouponList().get(indexLastCoupon.getZeroBased());

        CouponBuilder couponInList = new CouponBuilder(lastCoupon);
        Coupon editedCoupon = couponInList.withName(CommandTestUtil.VALID_NAME_BOB)
                .withPromoCode(CommandTestUtil.VALID_PROMO_CODE_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();

        EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
                .withPromoCode(CommandTestUtil.VALID_PROMO_CODE_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastCoupon, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());

        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(lastCoupon, editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand =
                new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, new EditCommand.EditCouponDescriptor());
        Coupon editedCoupon = model.getFilteredCouponList().get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());

        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        Coupon editedCoupon = new CouponBuilder(couponInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_limitEqualToUsage_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_SECOND_COUPON);

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        Coupon editedCoupon = new CouponBuilder(couponInFilteredList)
                .withLimit(couponInFilteredList.getUsage().value).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder().withLimit(couponInFilteredList.getUsage().value)
                        .build());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_limitLowerThanUsage_failure() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        int currentUsageValue = TypicalCoupons.getTypicalCoupons().get(targetIndex.getZeroBased()).getUsage().value;
        int newLimitValue = currentUsageValue - 1;

        Coupon secondCoupon = model.getFilteredCouponList().get(TypicalIndexes.INDEX_SECOND_COUPON.getZeroBased());
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(secondCoupon)
                .withLimit(newLimitValue)
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_COUPON, descriptor);

        assertCommandFailure(editCommand, model,
                String.format(EditCommand.MESSAGE_LIMIT_LESS_THAN_USAGE, currentUsageValue));
    }

    @Test
    public void execute_duplicateCouponUnfilteredList_failure() {
        Coupon secondCoupon = model.getFilteredCouponList().get(TypicalIndexes.INDEX_SECOND_COUPON.getZeroBased());
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(secondCoupon).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COUPON);
    }

    @Test
    public void execute_duplicateCouponFilteredList_failure() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);

        // edit coupon in filtered list into a duplicate in CouponStash
        Coupon couponInList = model.getCouponStash().getCouponList()
                .get(TypicalIndexes.INDEX_SECOND_COUPON.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder(couponInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COUPON);
    }

    @Test
    public void execute_invalidCouponIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCouponList().size() + 1);
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of CouponStash
     */
    @Test
    public void execute_invalidCouponIndexFilteredList_failure() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        // ensures that outOfBoundIndex is still in bounds of CouponStash list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCouponStash().getCouponList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCouponDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    //Editing Start Date
    @Test
    public void execute_startDateAfterOriginalExpiryDate_failure() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        LocalDate ed = TypicalCoupons.getTypicalCoupons().get(targetIndex.getZeroBased()).getExpiryDate().getDate();
        String sd = DateUtil.formatDateToString(ed.plusDays(1));

        Coupon secondCoupon = model.getFilteredCouponList().get(TypicalIndexes.INDEX_SECOND_COUPON.getZeroBased());
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(secondCoupon)
                .withStartDate(sd)
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_COUPON, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_START_DATE_EXPIRY_DATE_CONSTRAINT);
    }

    //Editing Remind Date
    @Test
    public void execute_remindDateAfterOriginalExpiryDate_failure() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        LocalDate ed = TypicalCoupons.getTypicalCoupons().get(targetIndex.getZeroBased()).getExpiryDate().getDate();
        String rd = DateUtil.formatDateToString(ed.plusDays(1));

        Coupon secondCoupon = model.getFilteredCouponList().get(TypicalIndexes.INDEX_SECOND_COUPON.getZeroBased());
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(secondCoupon)
                .withRemindDate(rd)
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_COUPON, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE);
    }

    @Test
    public void execute_startDateEqualsOriginalExpiryDate_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_SECOND_COUPON);

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        Coupon editedCoupon = new CouponBuilder(couponInFilteredList)
                .withStartDate(couponInFilteredList.getExpiryDate().value).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder().withStartDate(couponInFilteredList.getExpiryDate().value)
                        .build());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_startDateBeforeOriginalExpiryDate_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_SECOND_COUPON);

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        ExpiryDate ed = couponInFilteredList.getExpiryDate();
        LocalDate sd = ed.getDate().minusDays(1);
        String sdString = DateUtil.formatDateToString(sd);
        Coupon editedCoupon = new CouponBuilder(couponInFilteredList)
                .withStartDate(sdString).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder().withStartDate(sdString)
                        .build());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //Editing Start Date and Expiry Date
    @Test
    public void execute_startDateBeforeExpiryDate_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_SECOND_COUPON);

        // New StartDate later than old ExpiryDate

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        LocalDate oldEd = couponInFilteredList.getExpiryDate().getDate();
        LocalDate newSd = oldEd.plusDays(7).minusDays(1);
        LocalDate newEd = oldEd.plusDays(7);
        String sdString = DateUtil.formatDateToString(newSd);
        String edString = DateUtil.formatDateToString(newEd);

        Coupon editedCoupon = new CouponBuilder(couponInFilteredList)
                .withStartDate(sdString).withExpiryDate(edString).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder().withStartDate(sdString).withExpiryDate(edString)
                        .build());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);



        // New ExpiryDate earlier than old StartDate
        couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        LocalDate oldSd = couponInFilteredList.getStartDate().getDate();
        newEd = oldSd.minusDays(7);
        newSd = oldSd.minusDays(7).minusDays(1);
        sdString = DateUtil.formatDateToString(newSd);
        edString = DateUtil.formatDateToString(newEd);

        editedCoupon = new CouponBuilder(couponInFilteredList)
                .withStartDate(sdString).withExpiryDate(edString)
                .withRemindDate(DateUtil.formatDateToString(newEd.minusDays(3))).build();
        editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder()
                        .withStartDate(sdString)
                        .withExpiryDate(edString)
                        .withRemindDate(DateUtil.formatDateToString(newEd.minusDays(3)))
                        .build());
        expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //Editing Expiry Date
    @Test
    public void execute_expiryDateBeforeOriginalStartDate_failure() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        LocalDate sd = TypicalCoupons.getTypicalCoupons().get(targetIndex.getZeroBased()).getStartDate().getDate();
        String ed = DateUtil.formatDateToString(sd.minusDays(1));

        Coupon secondCoupon = model.getFilteredCouponList().get(TypicalIndexes.INDEX_SECOND_COUPON.getZeroBased());
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(secondCoupon)
                .withExpiryDate(ed)
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_COUPON, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_START_DATE_EXPIRY_DATE_CONSTRAINT);
    }

    @Test
    public void execute_expiryDateEqualsOriginalStartDate_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_SECOND_COUPON);

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        StartDate sd = couponInFilteredList.getStartDate();
        Coupon editedCoupon = new CouponBuilder(couponInFilteredList)
                .withExpiryDate(sd.value)
                .withRemindDate(DateUtil.formatDateToString(sd.getDate().minusDays(3)))
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder()
                        .withExpiryDate(couponInFilteredList.getStartDate().value)
                        .withRemindDate(sd.value)
                        .build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }



    @Test
    public void execute_expiryDateAfterOriginalStartDate_success() {
        CommandTestUtil.showCouponAtIndex(model, TypicalIndexes.INDEX_SECOND_COUPON);

        Coupon couponInFilteredList = model.getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        StartDate sd = couponInFilteredList.getStartDate();
        LocalDate ed = sd.getDate().plusDays(1);
        String edString = DateUtil.formatDateToString(ed);
        Coupon editedCoupon = new CouponBuilder(couponInFilteredList)
                .withExpiryDate(edString)
                .build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON,
                new EditCouponDescriptorBuilder()
                        .withExpiryDate(
                                DateUtil.formatDateToString(couponInFilteredList.getStartDate().getDate().plusDays(1))
                        )
                        .withRemindDate(
                                DateUtil.formatDateToString(couponInFilteredList.getStartDate().getDate().minusDays(3))
                        )
                        .build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COUPON_SUCCESS, editedCoupon.getName());
        Model expectedModel = new ModelManager(new CouponStash(model.getCouponStash()), new UserPrefs());
        expectedModel.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }




    @Test
    public void equals() {
        final EditCommand standardCommand =
                new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditCouponDescriptor copyDescriptor = new EditCommand.EditCouponDescriptor(CommandTestUtil.DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new EditCommand(TypicalIndexes.INDEX_SECOND_COUPON, CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, CommandTestUtil.DESC_BOB)));
    }

}
