package csdev.couponstash.logic.commands;

import static csdev.couponstash.commons.util.DateUtil.MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE;
import static csdev.couponstash.commons.util.DateUtil.MESSAGE_START_DATE_EXPIRY_DATE_CONSTRAINT;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.StashSettings;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.ReadOnlyUserPrefs;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.element.ObservableMonthView;
import csdev.couponstash.testutil.CouponBuilder;

import javafx.collections.ObservableList;

public class AddCommandTest {

    @Test
    public void constructor_nullCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_couponAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCouponAdded modelStub = new ModelStubAcceptingCouponAdded();
        Coupon validCoupon = new CouponBuilder().build();

        CommandResult commandResult = new AddCommand(validCoupon).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCoupon.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCoupon), modelStub.couponsAdded);
    }

    @Test
    public void execute_startDateIsAfterExpiryDate_throwsCommandException() {
        Coupon validCoupon = new CouponBuilder().build();
        Coupon invalidCoupon = new CouponBuilder().withExpiryDate("1-8-2020").withStartDate("31-12-2020").build();
        AddCommand addCommand = new AddCommand(invalidCoupon);
        ModelStub modelStub = new ModelStubWithCoupon(validCoupon);

        assertThrows(CommandException.class, MESSAGE_START_DATE_EXPIRY_DATE_CONSTRAINT, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void execute_remindDateIsAfterExpiryDate_throwsCommandException() {
        Coupon validCoupon = new CouponBuilder().build();
        Coupon invalidCoupon = new CouponBuilder().withExpiryDate("1-8-2020").withRemindDate("31-12-2020").build();
        AddCommand addCommand = new AddCommand(invalidCoupon);
        ModelStub modelStub = new ModelStubWithCoupon(validCoupon);

        assertThrows(CommandException.class, MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateCoupon_throwsCommandException() {
        Coupon validCoupon = new CouponBuilder().build();
        AddCommand addCommand = new AddCommand(validCoupon);
        ModelStub modelStub = new ModelStubWithCoupon(validCoupon);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_COUPON, () -> addCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Coupon alice = new CouponBuilder().withName("Alice").build();
        Coupon bob = new CouponBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotNull(addAliceCommand);

        // different coupon -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StashSettings getStashSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStashSettings(StashSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCouponStashFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCouponStashFilePath(Path couponStashFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCoupon(Coupon coupon, String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCouponStash(ReadOnlyCouponStash newData, String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCouponStash getCouponStash() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCoupon(Coupon coupon) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCoupon(Coupon target, String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCoupon(Coupon target, Coupon editedCoupon, String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Coupon> getFilteredCouponList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Coupon> getAllCouponList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCouponList(Predicate<? super Coupon> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableMonthView getMonthView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMonthView(String yearMonth) {
            new AssertionError("This method should not be called.");
        }


        @Override
        public void commitCouponStash(String command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String undoCouponStash() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String redoCouponStash() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCouponStash() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCouponStash() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortCoupons(Comparator<Coupon> cmp) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String setMoneySymbol(String moneySymbol) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single coupon.
     */
    private class ModelStubWithCoupon extends ModelStub {
        private final Coupon coupon;

        ModelStubWithCoupon(Coupon coupon) {
            requireNonNull(coupon);
            this.coupon = coupon;
        }

        @Override
        public boolean hasCoupon(Coupon coupon) {
            requireNonNull(coupon);
            return this.coupon.isSameCoupon(coupon);
        }
    }

    /**
     * A Model stub that always accept the coupon being added.
     */
    private class ModelStubAcceptingCouponAdded extends ModelStub {
        final ArrayList<Coupon> couponsAdded = new ArrayList<>();

        @Override
        public boolean hasCoupon(Coupon coupon) {
            requireNonNull(coupon);
            return couponsAdded.stream().anyMatch(coupon::isSameCoupon);
        }

        @Override
        public void addCoupon(Coupon coupon, String commandText) {
            requireNonNull(coupon);
            couponsAdded.add(coupon);
        }

        @Override
        public ReadOnlyCouponStash getCouponStash() {
            return new CouponStash();
        }
    }

}
