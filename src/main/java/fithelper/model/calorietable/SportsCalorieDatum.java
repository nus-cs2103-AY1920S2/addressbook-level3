package fithelper.model.calorietable;

/**
 * An entry storing the name of a sports and its calorie consumption (range) per hour.
 */
public class SportsCalorieDatum extends CalorieDatum {
    public SportsCalorieDatum(String name, String calorie) {
        super(name, calorie);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n"
                + "Calorie (70kg): " + getCalorie() + " cal / hour\n";
    }
}
