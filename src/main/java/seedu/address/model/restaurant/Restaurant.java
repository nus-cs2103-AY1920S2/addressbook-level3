package seedu.address.model.restaurant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Restaurant {
    // for sarah's use
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    // Identity fields
    private final Name name;
    private final Location location;

    // Data fields
    private final Hours hours;
    private final Price price;
    private final Cuisine cuisine;
    private final ArrayList<Remark> remarks = new ArrayList<>();


    public Restaurant(Name name, Location location, Hours hours, Price price, Cuisine cuisine,
                      ArrayList<Remark> remark) {
        requireAllNonNull(name, location);
        this.name = name;
        this.location = location;
        this.hours = hours;
        this.price = price;
        this.cuisine = cuisine;
        this.remarks.addAll(remark);
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Hours getHours() {
        return hours;
    }

    public Price getPrice() {
        return price;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public ArrayList<Remark> getRemark() {
        return remarks;
    }

    /**
     * Returns true if both restaurants of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameRestaurant(Restaurant otherRestaurant) {
        if (otherRestaurant == this) {
            return true;
        }

        return otherRestaurant != null
                && otherRestaurant.getName().equals(getName())
                && (otherRestaurant.getLocation().equals(getLocation()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Restaurant)) {
            return false;
        }

        Restaurant otherRestaurant = (Restaurant) other;
        return otherRestaurant.getName().equals(getName())
                && otherRestaurant.getLocation().equals(getLocation())
                && otherRestaurant.getHours().equals(getHours())
                && otherRestaurant.getPrice().equals(getPrice())
                && otherRestaurant.getCuisine().equals(getCuisine())
                && otherRestaurant.getRemark().equals(getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, hours, price, cuisine, remarks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append(getName())
                .append("\nLocation: ")
                .append(getLocation())
                .append("\nHours: ")
                .append(getHours())
                .append("\nPrice Point: ")
                .append(getPrice())
                .append("\nCuisine: ")
                .append(getCuisine())
                .append("\nRemarks: ");
        getRemark().forEach(builder::append);
        return builder.toString();
    }

}
