package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ItemListParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Recipe's ingredients in the recipe book.
 */
public class IngredientList {

    public static final String MESSAGE_CONSTRAINTS = "Ingredients can take any values for now, and it should not be "
            + "blank";

    /*
     * The first character of the ingredients must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final List<Ingredient> data;

    /**
     * Constructs an {@code IngredientList}.
     */
    public IngredientList(String ingredientListString) {
        requireNonNull(ingredientListString);
        //checkArgument(isValidIngredients(ingredientListString), MESSAGE_CONSTRAINTS);

        List<Ingredient> templist;
        try {
            templist = ItemListParser.parseIngredientsToList(ingredientListString);
        } catch (ParseException e) {
            templist = new ArrayList<Ingredient>();
        }
        this.data = templist;
    }

    /**
     * Returns true if a given string is a valid ingredient list.
     */
    public static boolean isValidIngredients(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Adds an ingredient to the ingredient list.
     *
     * @param ingredient the ingredient to be added.
     */
    public void addIngredient(Ingredient ingredient) {
        data.add(ingredient);
    }

    /**
     * Deletes an ingredient from the ingredient list.
     *
     * @param ingredient the ingredient to be deleted.
     */
    public void deleteIngredient(Ingredient ingredient) {
        data.remove(ingredient);
    }

    @Override
    public String toString() {
        return (String) this.data.stream()
                .map(Ingredient::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    // TODO: delete this since we are not printing to console.

    /**
     * Prints out the ingredients list.
     */
    public void print() {
        for (int i = 0; i < data.size(); i++) {
            System.out.print(data.get(i));
        }
    }
}
