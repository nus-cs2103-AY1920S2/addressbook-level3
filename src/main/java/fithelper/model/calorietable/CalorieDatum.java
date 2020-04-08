package fithelper.model.calorietable;

import java.util.Objects;

/**
 * An entry about calorie intake/consumption of a food/sports.
 */
public abstract class CalorieDatum {
    private String name;
    private String calorie;

    public CalorieDatum(String name, String calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public String getCalorie() {
        return calorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof CalorieDatum) {
            CalorieDatum other = (CalorieDatum) o;
            return other.getName().equals(this.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
