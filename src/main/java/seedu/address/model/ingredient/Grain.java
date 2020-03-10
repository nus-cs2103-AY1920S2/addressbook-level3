package seedu.address.model.ingredient;

public class Grain extends Ingredient {

    public Grain(double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.SOLID;
    }
}
