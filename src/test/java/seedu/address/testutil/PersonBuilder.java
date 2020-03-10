package seedu.address.testutil;

import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;

/**
 * A utility class to help with building Profile objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_COURSE = "Computer Science";
    public static final String DEFAULT_SPEC = "Software Engineering";

    private Name name;
    private Course course;
    private String specialisation;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        course = new Course(DEFAULT_COURSE);
        specialisation = DEFAULT_SPEC;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code profileToCopy}.
     */
    public PersonBuilder(Profile profileToCopy) {
        name = profileToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Profile} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code Profile} that we are building.
     */
    public PersonBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    public Profile build() {
        return new Profile(name, course, specialisation);
    }

}
