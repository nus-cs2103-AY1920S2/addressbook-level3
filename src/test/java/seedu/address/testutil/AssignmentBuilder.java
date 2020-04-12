package seedu.address.testutil;

import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelObjectTags.Date;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {

    public static final String DEFAULT_NAME = "Data Struct and Algo Assignment 1";
    public static final String DEFAULT_DEADLINE = "2020-12-30";
    public static final String DEFAULT_ID = "102";

    private Name name;
    private ID id;
    private Set<Tag> tags;
    private Date dl;

    public AssignmentBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        dl = new Date(DEFAULT_DEADLINE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the AssignmentBuilder with the data of {@code AssignmentToCopy}.
     */
    public AssignmentBuilder(Assignment AssignmentToCopy) {
        name = AssignmentToCopy.getName();
        id = AssignmentToCopy.getId();
        dl = AssignmentToCopy.getDeadline();
        tags = new HashSet<>(AssignmentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withID(String ID) {
        this.id = new ID(ID);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withDeadline(String dl) {
        this.dl = new Date(dl);
        return this;
    }

    public Assignment build() {
        return new Assignment(name, id, dl, tags);
    }

}
