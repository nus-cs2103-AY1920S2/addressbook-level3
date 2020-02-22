package seedu.address.model.food;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Data fields
    private final String name;
    private final String time;
    private final String location;
    private final String weight;
    //private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Food(String name, String time, String location, String weight) {
        requireAllNonNull(name, time, location, weight);
        this.name = name;
        this.time = time;
        this.location = location;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getWeight() {
        return weight;
    }


    /**
     * Returns true if both food have all the same properties.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName())
                && otherFood.getTime().equals(getTime()) && otherFood.getLocation().equals(getWeight());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(" Time: ")
                .append(time.toString())
                .append(" Location: ")
                .append(location)
                .append(" Weight: ")
                .append(weight);
        return builder.toString();
    }

}
