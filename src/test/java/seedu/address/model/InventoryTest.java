package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_ALMOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_BANANA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.getTypicalInventory;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

public class InventoryTest {

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getIngredientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> inventory.hasIngredient(null));
    }

    @Test
    public void hasIngredient_ingredientNotInInventory_returnsFalse() {
        assertFalse(inventory.hasIngredient(APPLE));
    }

    @Test
    public void hasIngredient_ingredientInAddressBook_returnsTrue() {
        inventory.addIngredient(APPLE);
        assertTrue(inventory.hasIngredient(APPLE));
    }

    @Test
    public void hasIngredient_compatibleIngredientInInventory_returnsTrue() {
        inventory.addIngredient(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertTrue(inventory.hasIngredient(editedApple));
    }

    @Test
    public void hasIngredient_incompatibleIngredientInInventory_returnsFalse() {
        inventory.addIngredient(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertFalse(inventory.hasIngredient(editedApple));
    }

    @Test
    public void getIngredientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> inventory.getIngredientList().remove(0));
    }

    /**
     * A stub ReadOnlyInventory whose ingredient list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

        InventoryStub(Collection<Ingredient> ingredients) {
            this.ingredients.setAll(ingredients);
        }

        @Override
        public ObservableList<Ingredient> getIngredientList() {
            return ingredients;
        }
    }

}
