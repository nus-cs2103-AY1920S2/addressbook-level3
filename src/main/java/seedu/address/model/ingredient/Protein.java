package seedu.address.model.ingredient;

public class Protein extends Ingredient {

    public Protein (double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.SOLID;
    }
}
