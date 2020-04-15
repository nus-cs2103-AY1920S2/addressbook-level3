package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.customer.TypicalCustomers.ALICE;
import static seedu.address.testutil.customer.TypicalCustomers.getTypicalInventorySystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.customer.ClearCustomerCommand;
import seedu.address.logic.commands.product.ClearProductCommand;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.exceptions.DuplicatePersonException;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.customer.CustomerBuilder;

public class InventorySystemTest {

    private final InventorySystem addressBook = new InventorySystem();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null, null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        InventorySystem newData = getTypicalInventorySystem();
        addressBook.resetData(newData, ClearCustomerCommand.COMMAND_WORD);
        addressBook.resetData(newData, ClearProductCommand.COMMAND_WORD);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two customers with the same identity fields
        Customer editedAlice = new CustomerBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Customer> newCustomers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCustomers);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData,
                ClearCustomerCommand.COMMAND_WORD));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyInventorySystem whose customers list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyInventorySystem {
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();
        private final ObservableList<Product> products = FXCollections.observableArrayList();

        AddressBookStub(Collection<Customer> customers) {
            this.customers.setAll(customers);
        }

        @Override
        public ObservableList<Customer> getPersonList() {
            return customers;
        }

        @Override
        public ObservableList<Product> getProductList() {
            return products;
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return null;
        }
    }

}
