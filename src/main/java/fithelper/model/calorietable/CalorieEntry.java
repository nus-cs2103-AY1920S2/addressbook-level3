package fithelper.model.calorietable;

/**
 * An entry about calorie intake/consumption of a food/sports.
 */
public abstract class CalorieEntry {
    private String name;
    private String calorie;

    public CalorieEntry(String name, String calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public String getCalorie() {
        return calorie;
    }
}
