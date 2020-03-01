package seedu.foodiebot.model.canteen;

import static seedu.foodiebot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.logging.Logger;

import javafx.scene.image.Image;

import seedu.foodiebot.commons.core.LogsCenter;

/**
 * Represents a Stall in FoodieBot. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Stall {

    public static final String IMAGE_FOLDER = "/images/canteen/";
    private static final Logger logger = LogsCenter.getLogger(Stall.class);
    // Identity fields
    private final Name name;
    private final String canteenName;
    private final int stallNumber;
    private final String stallImageName;
    private final String cuisine;
    private final String overallPriceRating;

    /**
     * Every field must be present and not null.
     */
    public Stall(
        Name name, String canteenName, int stallNumber, String stallImageName,
        String cuisine, String overallPriceRating) {
        requireAllNonNull(name, canteenName, stallNumber, stallImageName, cuisine, overallPriceRating);
        this.name = name;
        this.canteenName = canteenName;
        this.stallNumber = stallNumber;
        this.stallImageName = stallImageName;
        this.cuisine = cuisine;
        this.overallPriceRating = overallPriceRating;
    }

    public Name getName() {
        return name;
    }

    public String getStallName() {
        return canteenName;
    }

    public int getStallNumber() {
        return stallNumber;
    }

    public String getStallImageName() {
        return stallImageName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getOverallPriceRating() {
        return overallPriceRating;
    }

    public Image getStallImage() {
        String mImageUrl = IMAGE_FOLDER + canteenName + "/" + stallImageName;
        Image image = new Image(Stall.class.getResourceAsStream((mImageUrl)));
        return image;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStall(Stall otherStall) {
        if (otherStall == this) {
            return true;
        }

        return otherStall != null
            && otherStall.getName().equals(getName())
            && (otherStall.getStallName().equals(getStallName())
            || otherStall.getStallNumber() == (getStallNumber()));
    }

    /**
     * Returns true if both stalls have the same identity and data fields. This defines a stronger
     * notion of equality between two stalls.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Stall)) {
            return false;
        }

        Stall otherStall = (Stall) other;
        return otherStall.getName().equals(getName())
            && otherStall.getStallName().equals(getStallName())
            && otherStall.getStallNumber() == (getStallNumber())
            && otherStall.getCuisine().equals(getCuisine())
            && otherStall.getOverallPriceRating().equals(getOverallPriceRating());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, canteenName, stallNumber, cuisine);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
            .append(getName())
            .append(" StallName: ")
            .append(getStallName())
            .append(" StallNumber: ")
            .append(getStallNumber())
            .append(" StallImageName: ")
            .append(getStallImageName())
            .append(" Cuisine: ")
            .append(getCuisine())
            .append(" OverallPriceRating: ")
            .append(getOverallPriceRating());
        ;
        return builder.toString();
    }
}
