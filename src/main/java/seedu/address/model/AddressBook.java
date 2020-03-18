package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.recipe.Recipe;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final Inventory inventory;
    private final Cookbook cookbook;
    private final Cart cart;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        inventory = new Inventory();
        cookbook = new Cookbook();
        cart = new Cart();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();

        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the ingredient list with {@code ingredients}
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.inventory.setIngredients(ingredients);
    }

    /**
     * Replaces the contents of the recipe list with {@code recipes}
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecipes(List<Recipe> recipes) {
        this.cookbook.setRecipes(recipes);
    }

    /**
     * Replaces the contents of the cart with {@code ingredients}
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setCart(List<Ingredient> ingredients) {
        this.cart.setIngredients(ingredients);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setIngredients(newData.getIngredientList());
        setRecipes(newData.getRecipeList());
        setCart(newData.getCart());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// ingredient-level methods

    /**
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the address book.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return inventory.hasIngredient(ingredient);
    }

    /**
     * Adds a ingredient to the address book.
     * The ingredient must not already exist in the address book.
     */
    public void addIngredient(Ingredient i) {
        inventory.addIngredient(i);
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the address book.
     * {@code editedPerson} must not be the same as an existing ingredient in the address book.
     */
    // public void setIngredient(Ingredient target, Ingredient editedIngredient) { }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeIngredient(Ingredient key) {
        inventory.removeIngredient(key);
    }

    //// recipe-level methods

    /**
     * Returns true if a person with the same identity as {@code recipe} exists in the address book.
     */
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return cookbook.hasRecipe(recipe);
    }

    /**
     * Adds a recipe to the address book.
     * The recipe must not already exist in the address book.
     */
    public void addRecipe(Recipe r) {
        cookbook.addRecipe(r);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the address book.
     * {@code editedRecipe} must not be the same as an existing recipe in the address book.
     */
    // public void setRecipe(Recipe target, Recipe editedRecipe) { }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeRecipe(Recipe key) {
        cookbook.removeRecipe(key);
    }

    public void addCartIngredient(Ingredient sampleCartIngredient) {
        cart.addIngredient(sampleCartIngredient);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Ingredient> getIngredientList() {
        return inventory.getIngredientList();
    }

    @Override
    public ObservableList<Recipe> getRecipeList() {
        return cookbook.getRecipeList();
    }

    @Override
    public ObservableList<Ingredient> getCart() {
        return cart.getIngredientList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && inventory.equals(((AddressBook) other).inventory)
                && cookbook.equals(((AddressBook) other).cookbook));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
