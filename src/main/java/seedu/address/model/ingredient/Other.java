package seedu.address.model.ingredient;

public class Other extends Ingredient {

    public Other(double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.COUNTABLE;
    }

    public void setUnit(Unit unit) { //todo: allow users to input their own unit for 'Other' Ingredient
        super.unit = unit;
    }
}
