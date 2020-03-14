package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.person.AssignedCourse;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Course objects.
 */
public class CourseBuilder {

    public static final String DEFAULT_NAME = "Java Programming";
    public static final String DEFAULT_ID = "102";

    private Name name;
    private ID id;
    private Set<Tag> tags;

    public CourseBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CourseBuilder with the data of {@code courseToCopy}.
     */
    public CourseBuilder(Course courseToCopy) {
        name = courseToCopy.getName();
        id = courseToCopy.getId();
        tags = new HashSet<>(courseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Assignment} that we are building.
     */
    public CourseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Assignment} that we are building.
     */
    public CourseBuilder withID(String id) {
        this.id = new ID(id);
        return this;
    }

    public Course build() {
        return new Course(name, id, tags);
    }

}
