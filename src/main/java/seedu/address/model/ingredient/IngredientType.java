package seedu.address.model.ingredient;

public enum IngredientType {
    GRAIN("grain"), VEGETABLE("vegetable"), PROTEIN("protein"), OTHER("other");

    public String value;
    IngredientType(String value) {
        this.value = value;
    }
}
