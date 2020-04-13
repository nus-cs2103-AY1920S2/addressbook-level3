package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HEALTHY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;
import static seedu.address.testutil.TypicalRecipes.getTypicalCookbook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.exceptions.DuplicateRecipeException;
import seedu.address.testutil.RecipeBuilder;

public class CookbookTest {
    private final Cookbook cookbook = new Cookbook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cookbook.getRecipeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cookbook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCookbook_replacesData() {
        Cookbook newData = getTypicalCookbook();
        cookbook.resetData(newData);
        assertEquals(newData, cookbook);
    }

    @Test
    public void resetData_withDuplicateRecipes_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Recipe editedCarbonara = new RecipeBuilder(CARBONARA).withTags(VALID_TAG_HEALTHY)
                .build();
        List<Recipe> newRecipes = Arrays.asList(CARBONARA, editedCarbonara);
        CookbookTest.CookbookStub newData = new CookbookTest.CookbookStub(newRecipes);

        assertThrows(DuplicateRecipeException.class, () -> cookbook.resetData(newData));
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cookbook.hasRecipe(null));
    }

    @Test
    public void hasRecipe_recipeNotInCookbook_returnsFalse() {
        assertFalse(cookbook.hasRecipe(CARBONARA));
    }

    @Test
    public void hasRecipe_recipeInCookbook_returnsTrue() {
        cookbook.addRecipe(CARBONARA);
        assertTrue(cookbook.hasRecipe(CARBONARA));
    }

    @Test
    public void hasRecipe_recipeWithSameIdentityFieldsInCookbook_returnsTrue() {
        cookbook.addRecipe(CARBONARA);
        Recipe editedCarbonara = new RecipeBuilder(CARBONARA).withTags(VALID_TAG_HEALTHY)
                .build();
        assertTrue(cookbook.hasRecipe(editedCarbonara));
    }

    @Test
    public void getRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cookbook.getRecipeList().remove(0));
    }

    /**
     * A stub ReadOnlyCookbook whose persons list can violate interface constraints.
     */
    private static class CookbookStub implements ReadOnlyCookbook {
        private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();

        CookbookStub(Collection<Recipe> recipes) {
            this.recipes.setAll(recipes);
        }

        @Override
        public ObservableList<Recipe> getRecipeList() {
            return recipes;
        }
    }
}
