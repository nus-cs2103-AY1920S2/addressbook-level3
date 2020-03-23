package fithelper.model.calorietable;

/**
 * An entry storing the name of a food, its calorie intake per serving, and the weight of one serving.
 */
public class FoodCalorieEntry extends CalorieEntry {
    private String weightPerServingInGram;

    public FoodCalorieEntry(String name, String calorie, String weight) {
        super(name, calorie);
        weightPerServingInGram = weight;
    }

    @Override
    public String toString() {
        return "Food name: " + getName() + "\n"
                + "Calorie: " + getCalorie() + " cal per serving ("
                + weightPerServingInGram + "g)\n";
    }
}
