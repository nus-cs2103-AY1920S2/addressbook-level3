package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_ALMOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_BANANA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.getTypicalCart;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

public class CartTest {

    private final Cart cart = new Cart();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cart.getIngredientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cart.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCart_replacesData() {
        Cart newData = getTypicalCart();
        cart.resetData(newData);
        assertEquals(newData, cart);
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cart.hasIngredient(null));
    }

    @Test
    public void hasIngredient_ingredientNotInCart_returnsFalse() {
        assertFalse(cart.hasIngredient(APPLE));
    }

    @Test
    public void hasIngredient_ingredientInAddressBook_returnsTrue() {
        cart.addIngredient(APPLE);
        assertTrue(cart.hasIngredient(APPLE));
    }

    @Test
    public void hasIngredient_compatibleIngredientInCart_returnsTrue() {
        cart.addIngredient(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertTrue(cart.hasIngredient(editedApple));
    }

    @Test
    public void hasIngredient_incompatibleIngredientInCart_returnsFalse() {
        cart.addIngredient(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertFalse(cart.hasIngredient(editedApple));
    }

    @Test
    public void getIngredientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cart.getIngredientList().remove(0));
    }

    /**
     * A stub ReadOnlyCart whose ingredient list can violate interface constraints.
     */
    private static class CartStub implements ReadOnlyCart {
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

        CartStub(Collection<Ingredient> ingredients) {
            this.ingredients.setAll(ingredients);
        }

        @Override
        public ObservableList<Ingredient> getIngredientList() {
            return ingredients;
        }
    }

}
