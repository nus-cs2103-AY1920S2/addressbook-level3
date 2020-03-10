package seedu.address.model.ingredient;

public class Vegetable extends Ingredient {

    public Vegetable(double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.SOLID;
    }
}
