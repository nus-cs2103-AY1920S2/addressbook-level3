package fithelper.model.calorietable;

/**
 * An entry storing the name of a food, its calorie intake per serving, and the weight of one serving.
 */
public class FoodCalorieDatum extends CalorieDatum {
    private String weightPerServingInGram;

    public FoodCalorieDatum(String name, String calorie, String weight) {
        super(name, calorie);
        weightPerServingInGram = weight;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n"
                + "Calorie: " + getCalorie() + " per "
                + weightPerServingInGram + "\n";
    }
}
