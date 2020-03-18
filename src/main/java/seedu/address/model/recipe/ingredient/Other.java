package seedu.address.model.recipe.ingredient;

/**
 * Represents Other Ingredients in the recipe book. These ingredients include spices, liquids and condiments.
 */

public class Other extends Ingredient {

    public Other(String name, double quantity) {
        super(name, quantity);
        super.unit = Unit.COUNTABLE;
        super.ingredientType = IngredientType.OTHER;
    }

    public void setUnit(Unit unit) { //todo: allow users to input their own unit for 'Other' Ingredient
        super.unit = unit;
    }
}
