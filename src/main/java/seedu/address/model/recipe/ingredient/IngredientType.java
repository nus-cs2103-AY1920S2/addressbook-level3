package seedu.address.model.recipe.ingredient;

/**
 * IngredientType contains the four main categories that the ingredients can belong to.
 */
public enum IngredientType {
    GRAIN("grain"), VEGETABLE("vegetable"), PROTEIN("protein"), OTHER("other");

    private String value;
    IngredientType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
