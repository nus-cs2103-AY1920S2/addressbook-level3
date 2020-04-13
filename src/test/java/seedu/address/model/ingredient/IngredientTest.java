package seedu.address.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_NAME_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_ALMOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_BANANA;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.BANANA;

import org.junit.jupiter.api.Test;

import seedu.address.model.ingredient.exceptions.IncompatibleIngredientException;
import seedu.address.model.ingredient.exceptions.NonPositiveIngredientQuantityException;
import seedu.address.testutil.IngredientBuilder;

public class IngredientTest {

    @Test
    public void add_compatibleIngredient_success() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        Ingredient expectedIngredient = new IngredientBuilder(APPLE)
                .withQuantity(APPLE.getQuantity().add(editedApple.getQuantity()).toString()).build();
        assertEquals(expectedIngredient, APPLE.add(editedApple));
    }

    @Test
    public void add_ingredientWithDifferentUnit_throwsIncompatibleIngredientException() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertThrows(IncompatibleIngredientException.class, () -> APPLE.add(editedApple));
    }

    @Test
    public void add_differentIngredient_throwsIncompatibleIngredientException() {
        assertThrows(IncompatibleIngredientException.class, () -> APPLE.add(BANANA));
    }

    @Test
    public void subtract_compatibleIngredient_success() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        Ingredient expectedIngredient = new IngredientBuilder(APPLE)
                .withQuantity(APPLE.getQuantity().subtract(editedApple.getQuantity()).toString()).build();
        assertEquals(expectedIngredient, APPLE.subtract(editedApple));
    }

    @Test
    public void subtract_equalIngredientQuantity_throwsNonPositiveIngredientQuantityException() {
        assertThrows(NonPositiveIngredientQuantityException.class, () -> APPLE.subtract(APPLE));
    }

    @Test
    public void subtract_largerIngredientQuantity_throwsNonPositiveIngredientQuantityException() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertThrows(NonPositiveIngredientQuantityException.class, () -> editedApple.subtract(APPLE));
    }

    @Test
    public void subtract_ingredientWithDifferentUnit_throwsIncompatibleIngredientException() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertThrows(IncompatibleIngredientException.class, () -> APPLE.subtract(editedApple));
    }

    @Test
    public void subtract_differentIngredient_throwsIncompatibleIngredientException() {
        assertThrows(IncompatibleIngredientException.class, () -> APPLE.subtract(BANANA));
    }

    @Test
    public void asProportionOf_compatibleIngredient_success() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertEquals(APPLE.getQuantity().asProportionOf(BANANA.getQuantity()), APPLE.asProportionOf(editedApple));
    }

    @Test
    public void asProportionOf_ingredientWithDifferentUnit_throwsIncompatibleIngredientException() {
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertThrows(IncompatibleIngredientException.class, () -> APPLE.asProportionOf(editedApple));
    }

    @Test
    public void asProportionOf_differentIngredient_throwsIncompatibleIngredientException() {
        assertThrows(IncompatibleIngredientException.class, () -> APPLE.asProportionOf(BANANA));
    }

    @Test
    public void isCompatibleWith() {
        // same object -> returns true
        assertTrue(APPLE.isCompatibleWith(APPLE));

        // null -> returns false
        assertFalse(APPLE.isCompatibleWith(null));

        // different name -> returns false
        Ingredient editedApple = new IngredientBuilder(APPLE).withName(VALID_INGREDIENT_NAME_BANANA).build();
        assertFalse(APPLE.isCompatibleWith(editedApple));

        // same name, different quantity value -> returns true
        editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertTrue(APPLE.isCompatibleWith(editedApple));

        // same name, different quantity unit -> returns false
        editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertFalse(APPLE.isCompatibleWith(editedApple));
    }

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        assertTrue(APPLE.isSameIngredient(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameIngredient(null));

        // different name -> returns false
        Ingredient editedApple = new IngredientBuilder(APPLE).withName(VALID_INGREDIENT_NAME_BANANA).build();
        assertFalse(APPLE.isSameIngredient(editedApple));

        // same name, different quantity value -> returns true
        editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // same name, different quantity unit -> returns true
        editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertTrue(APPLE.isSameIngredient(editedApple));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ingredient appleCopy = new IngredientBuilder(APPLE).build();
        assertTrue(APPLE.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different ingredient -> returns false
        assertFalse(APPLE.equals(BANANA));

        // different name -> returns false
        Ingredient editedApple = new IngredientBuilder(APPLE).withName(VALID_INGREDIENT_NAME_BANANA).build();
        assertFalse(APPLE.equals(editedApple));

        // same name, different quantity value -> returns false
        editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertFalse(APPLE.equals(editedApple));

        // same name, different quantity unit -> returns false
        editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertFalse(APPLE.equals(editedApple));
    }
}
