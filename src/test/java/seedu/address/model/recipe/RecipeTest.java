package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;

public class RecipeTest {
    private static final RecipeName VALID_RECIPE_NAME = new RecipeName("Recipe");
    private static final RecipeDescription VALID_RECIPE_DESCRIPTION = new RecipeDescription("Description");
    private static final UniqueIngredientList VALID_INGREDIENTS = new UniqueIngredientList();
    private static final UniqueStepList VALID_STEPS = new UniqueStepList();
    private static final Set<Tag> VALID_TAGS = new HashSet<>();

    @Test
    public void constructor_validInput() {
        Recipe recipe = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION);
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);

        assertEquals(recipe, new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION));
        assertEquals(recipeOverloaded, new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Recipe(null, VALID_RECIPE_DESCRIPTION));
        assertThrows(NullPointerException.class, () -> new Recipe(VALID_RECIPE_NAME, null));
        assertThrows(NullPointerException.class, () -> new Recipe(null, null));

        assertThrows(NullPointerException.class, () -> new Recipe(null, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS));
        assertThrows(NullPointerException.class, () -> new Recipe(VALID_RECIPE_NAME, null, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS));
        assertThrows(NullPointerException.class, () -> new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, null,
            VALID_STEPS, VALID_TAGS));
        assertThrows(NullPointerException.class, () -> new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
            VALID_INGREDIENTS, null, VALID_TAGS));
        assertThrows(NullPointerException.class, () -> new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
            VALID_INGREDIENTS, VALID_STEPS, null));
        assertThrows(NullPointerException.class, () -> new Recipe(null, null, null,
                null, null));
    }

    @Test
    public void getName_validInput() {
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);
        assertEquals(recipeOverloaded.getName(), VALID_RECIPE_NAME);
    }

    @Test
    public void getDescription_validInput() {
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);
        assertEquals(recipeOverloaded.getDescription(), VALID_RECIPE_DESCRIPTION);
    }

    @Test
    public void getIngredients_validInput() {
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);
        assertEquals(recipeOverloaded.getIngredients(), VALID_INGREDIENTS.asUnmodifiableObservableList());
    }

    @Test
    public void getSteps_validInput() {
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);
        assertEquals(recipeOverloaded.getSteps(), VALID_STEPS);
    }

    @Test
    public void getTags_validInput() {
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);
        assertEquals(recipeOverloaded.getTags(), VALID_TAGS);
    }

    @Test
    public void isSameRecipe_validInput() {
        Recipe recipe = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION);
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);

        assertTrue(recipe.isSameRecipe(recipe));
        assertTrue(recipeOverloaded.isSameRecipe(recipeOverloaded));
        assertTrue(recipe.isSameRecipe(recipeOverloaded));
    }

    @Test
    public void equalsMethod() {
        Recipe recipe = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION);
        Recipe recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, VALID_TAGS);

        assertEquals(recipe, recipe);
        assertEquals(recipeOverloaded, recipeOverloaded);
        assertEquals(recipe, recipeOverloaded);
        assertNotEquals(recipe, null);

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Easy"));

        recipeOverloaded = new Recipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION, VALID_INGREDIENTS,
            VALID_STEPS, tags);
        assertNotEquals(recipe, recipeOverloaded);
    }
}
