package seedu.address.testutil;

import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code RecipeBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private RecipeBook addressBook;

    public AddressBookBuilder() {
        addressBook = new RecipeBook();
    }

    public AddressBookBuilder(RecipeBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Recipe} to the {@code RecipeBook} that we are building.
     */
    public AddressBookBuilder withPerson(Recipe recipe) {
        addressBook.addPerson(recipe);
        return this;
    }

    public RecipeBook build() {
        return addressBook;
    }
}
