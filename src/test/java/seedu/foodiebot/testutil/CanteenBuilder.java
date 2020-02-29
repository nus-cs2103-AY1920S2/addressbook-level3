package seedu.foodiebot.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.foodiebot.model.canteen.Canteen;
import seedu.foodiebot.model.canteen.Name;
import seedu.foodiebot.model.tag.Tag;
import seedu.foodiebot.model.util.SampleDataUtil;

/**
<<<<<<< HEAD
 * A utility class to help with building Canteen objects.
=======
 * A utility class to help with building Person objects.
>>>>>>> master
 */
public class CanteenBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final int DEFAULT_NUMBEROFSTALLS = 0;
    public static final int DEFAULT_DISTANCE = 0;
    public static final String DEFAULT_BLOCKNAME = "Kent Ridge MRT";

    private Name name;
    private int numberOfStalls;
    private int distance;
    private String blockName;
    private Set<Tag> tags;

    public CanteenBuilder() {
        name = new Name(DEFAULT_NAME);
        numberOfStalls = DEFAULT_NUMBEROFSTALLS;
        distance = DEFAULT_DISTANCE;
        blockName = DEFAULT_BLOCKNAME;
        tags = new HashSet<>();
    }

    /**
<<<<<<< HEAD
     * Initializes the CanteenBuilder with the data of {@code personToCopy}.
=======
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
>>>>>>> master
     */
    public CanteenBuilder(Canteen personToCopy) {
        name = personToCopy.getName();
        numberOfStalls = personToCopy.getNumberOfStalls();
        distance = personToCopy.getDistance();
        blockName = personToCopy.getBlockName();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
<<<<<<< HEAD
     * Sets the {@code Name} of the {@code Canteen} that we are building.
=======
     * Sets the {@code Name} of the {@code Person} that we are building.
>>>>>>> master
     */
    public CanteenBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
<<<<<<< HEAD
     * Sets the {@code distance} of the {@code Canteen} that we are building.
     */
    public CanteenBuilder withDistance(int distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Sets the {@code blockName} of the {@code Canteen} that we are building.
     */
    public CanteenBuilder withNearestBlock(String blockName) {
        this.blockName = blockName;
        return this;
    }



    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Canteen} that we are
=======
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are
>>>>>>> master
     * building.
     */
    public CanteenBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code numberOfStalls} of the {@code Canteen} that we are building.
     */
    public CanteenBuilder withNumberOfStalls(int numberOfStalls) {
        this.numberOfStalls = numberOfStalls;
        return this;
    }

    public Canteen build() {
        return new Canteen(name, numberOfStalls, distance, blockName, tags);
    }
}
