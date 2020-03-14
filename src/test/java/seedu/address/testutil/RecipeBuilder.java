package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.goal.Goal;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Vegetable;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Time;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_TIME = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final Ingredient DEFAULT_INGREDIENT = new Vegetable("Tomato", 100);

    private Name name;
    private Time time;
    private Set<Ingredient> ingredients;
    private Email email;
    private Set<Goal> goals;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        time = new Time(DEFAULT_TIME);
        ingredients = new HashSet<>();
        ingredients.add(DEFAULT_INGREDIENT);
        email = new Email(DEFAULT_EMAIL);
        goals = new HashSet<>();
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        time = recipeToCopy.getTime();
        ingredients = recipeToCopy.getIngredients();
        email = recipeToCopy.getEmail();
        goals = new HashSet<>(recipeToCopy.getGoals());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code goals} into a {@code Set<Goal>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withGoals(String ... goals) {
        this.goals = SampleDataUtil.getGoalSet(goals);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Ingredient} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredients(String ... ingredients) {
        this.ingredients = SampleDataUtil.getIngredientSet(ingredients);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, time, ingredients, email, goals);
    }

}
