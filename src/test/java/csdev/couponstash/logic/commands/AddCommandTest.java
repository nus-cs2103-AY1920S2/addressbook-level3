package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static csdev.couponstash.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.testutil.Assert;
import csdev.couponstash.testutil.PersonBuilder;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.AddressBook;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ReadOnlyAddressBook;
import csdev.couponstash.model.ReadOnlyUserPrefs;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Coupon validCoupon = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validCoupon).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCoupon), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCoupon), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Coupon validCoupon = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validCoupon);
        ModelStub modelStub = new ModelStubWithPerson(validCoupon);

        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Coupon alice = new PersonBuilder().withName("Alice").build();
        Coupon bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different coupon -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Coupon coupon) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Coupon coupon) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Coupon target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Coupon target, Coupon editedCoupon) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Coupon> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Coupon> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single coupon.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Coupon coupon;

        ModelStubWithPerson(Coupon coupon) {
            requireNonNull(coupon);
            this.coupon = coupon;
        }

        @Override
        public boolean hasPerson(Coupon coupon) {
            requireNonNull(coupon);
            return this.coupon.isSamePerson(coupon);
        }
    }

    /**
     * A Model stub that always accept the coupon being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Coupon> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Coupon coupon) {
            requireNonNull(coupon);
            return personsAdded.stream().anyMatch(coupon::isSamePerson);
        }

        @Override
        public void addPerson(Coupon coupon) {
            requireNonNull(coupon);
            personsAdded.add(coupon);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
