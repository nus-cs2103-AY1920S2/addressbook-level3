package seedu.foodiebot.model.canteen;

import static seedu.foodiebot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.foodiebot.model.tag.Tag;

/**
 * Represents a Canteen in FoodieBot. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Canteen {

    // Identity fields
    private final Name name;
    private final int numberOfStalls;
    private final int distance;
    private final String blockName;

    // Data fields
    private final Set<Tag> cuisines = new HashSet<>();

    /** Every field must be present and not null. */
    public Canteen(Name name, int numberOfStalls, int distance, String blockName, Set<Tag> tags) {
        requireAllNonNull(name, numberOfStalls, tags);
        this.name = name;
        this.numberOfStalls = numberOfStalls;
        this.distance = distance;
        this.blockName = blockName;
        this.cuisines.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public int getNumberOfStalls() {
        return numberOfStalls;
    }

    public int getDistance() {
        return distance;
    }

    public String getBlockName() {
        return blockName;
    }

    public Set<Tag> getCuisines() {
        return cuisines;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(cuisines);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCanteen(Canteen otherCanteen) {
        if (otherCanteen == this) {
            return true;
        }

        return otherCanteen != null
                && otherCanteen.getName().equals(getName())
                && (otherCanteen.getDistance() == (getDistance())
                        || otherCanteen.getNumberOfStalls() == (getNumberOfStalls()));
    }

    /**
     * Returns true if both canteens have the same identity and data fields. This defines a stronger
     * notion of equality between two canteens.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Canteen)) {
            return false;
        }

        Canteen otherCanteen = (Canteen) other;
        return otherCanteen.getName().equals(getName())
                && otherCanteen.getDistance() == (getDistance())
                && otherCanteen.getNumberOfStalls() == (getNumberOfStalls())
                && otherCanteen.getDistance() == (getDistance())
                && otherCanteen.getBlockName().equals(getBlockName())
                && otherCanteen.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, distance, numberOfStalls, cuisines);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(" NumberOfStalls: ")
                .append(getNumberOfStalls())
                .append(" Distance: ")
                .append(getDistance())
                .append(" NearestBlockName: ")
                .append(getBlockName())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
