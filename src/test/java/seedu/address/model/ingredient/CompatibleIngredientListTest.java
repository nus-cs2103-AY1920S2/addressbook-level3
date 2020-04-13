package seedu.address.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_ALMOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_QUANTITY_BANANA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.BANANA;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.testutil.IngredientBuilder;

public class CompatibleIngredientListTest {

    private final CompatibleIngredientList compatibleIngredientList = new CompatibleIngredientList();

    @Test
    public void contains_ingredientNotInList_returnsFalse() {
        assertFalse(compatibleIngredientList.contains(APPLE));
    }

    @Test
    public void contains_ingredientInList_returnsTrue() {
        compatibleIngredientList.add(APPLE);
        assertTrue(compatibleIngredientList.contains(APPLE));
    }

    @Test
    public void contains_ingredientWithSameNameAndUnitInList_returnsTrue() {
        compatibleIngredientList.add(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        assertTrue(compatibleIngredientList.contains(editedApple));
    }

    @Test
    public void contains_ingredientWithSameNameAndDifferentUnitInList_returnsFalse() {
        compatibleIngredientList.add(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        assertFalse(compatibleIngredientList.contains(editedApple));
    }

    @Test
    public void add_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> compatibleIngredientList.add(null));
    }

    @Test
    public void add_existingIngredientWithSameUnit_success() {
        compatibleIngredientList.add(APPLE);
        compatibleIngredientList.add(APPLE);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(APPLE.add(APPLE));
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void add_existingIngredientWithDifferentUnit_success() {
        compatibleIngredientList.add(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        compatibleIngredientList.add(editedApple);
        assertTrue(compatibleIngredientList.contains(editedApple));
    }

    @Test
    public void setIngredient_editedIngredientIsSameIngredient_success() {
        compatibleIngredientList.add(APPLE);
        compatibleIngredientList.setIngredient(APPLE, APPLE);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(APPLE);
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasSameIdentity_success() {
        compatibleIngredientList.add(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_BANANA).build();
        compatibleIngredientList.setIngredient(APPLE, editedApple);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(editedApple);
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasDifferentIdentity_success() {
        compatibleIngredientList.add(APPLE);
        compatibleIngredientList.setIngredient(APPLE, BANANA);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(BANANA);
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasNonUniqueIdentity_success() {
        compatibleIngredientList.add(APPLE);
        compatibleIngredientList.add(BANANA);
        compatibleIngredientList.setIngredient(APPLE, BANANA);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(BANANA.add(BANANA));
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void remove_nullIngredient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> compatibleIngredientList.remove((Ingredient) null));
    }

    @Test
    public void remove_ingredientDoesNotExist_throwsIngredientNotFoundException() {
        assertThrows(IngredientNotFoundException.class, () -> compatibleIngredientList.remove(APPLE));
    }

    @Test
    public void remove_existingIngredient_removesIngredient() {
        compatibleIngredientList.add(APPLE);
        compatibleIngredientList.remove(APPLE);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void remove_nullIngredientName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> compatibleIngredientList.remove((IngredientName) null));
    }

    @Test
    public void remove_ingredientNameDoesNotExist_throwsIngredientNotFoundException() {
        assertThrows(IngredientNotFoundException.class, () -> compatibleIngredientList.remove(APPLE.getName()));
    }

    @Test
    public void remove_existingIngredientName_removesIngredient() {
        compatibleIngredientList.add(APPLE);
        Ingredient editedApple = new IngredientBuilder(APPLE).withQuantity(VALID_INGREDIENT_QUANTITY_ALMOND).build();
        compatibleIngredientList.add(editedApple);
        compatibleIngredientList.remove(APPLE.getName());
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void setIngredients_compatibleIngredientList_replacesOwnListWithProvidedCompatibleIngredientList() {
        compatibleIngredientList.add(APPLE);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(BANANA);
        compatibleIngredientList.setIngredients(expectedCompatibleIngredientList);
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }

    @Test
    public void setIngredients_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                compatibleIngredientList.setIngredients((List<Ingredient>) null));
    }

    @Test
    public void setIngredients_list_replacesOwnListWithProvidedList() {
        compatibleIngredientList.add(APPLE);
        List<Ingredient> ingredientList = Collections.singletonList(BANANA);
        compatibleIngredientList.setIngredients(ingredientList);
        CompatibleIngredientList expectedCompatibleIngredientList = new CompatibleIngredientList();
        expectedCompatibleIngredientList.add(BANANA);
        assertEquals(expectedCompatibleIngredientList, compatibleIngredientList);
    }
}
