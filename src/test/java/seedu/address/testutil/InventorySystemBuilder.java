package seedu.address.testutil;

import seedu.address.model.InventorySystem;
import seedu.address.model.customer.Customer;

/**
 * A utility class to help with building InventorySystem objects.
 * Example usage: <br>
 *     {@code InventorySystem ab = new InventorySystemBuilder().withPerson("John", "Doe").build();}
 */
public class InventorySystemBuilder {

    private InventorySystem inventorySystem;

    public InventorySystemBuilder() {
        inventorySystem = new InventorySystem();
    }

    public InventorySystemBuilder(InventorySystem inventorySystem) {
        this.inventorySystem = inventorySystem;
    }

    /**
     * Adds a new {@code Customer} to the {@code InventorySystem} that we are building.
     */
    public InventorySystemBuilder withPerson(Customer customer) {
        inventorySystem.addPerson(customer);
        return this;
    }

    public InventorySystem build() {
        return inventorySystem;
    }
}
