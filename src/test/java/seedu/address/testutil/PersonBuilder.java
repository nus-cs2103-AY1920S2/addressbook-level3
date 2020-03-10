package seedu.address.testutil;

import seedu.address.model.profile.Name;
import seedu.address.model.profile.Person;
import seedu.address.model.profile.course.Course;

/**
 * A utility class to help with building Person objects.
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
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public PersonBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    public Person build() {
        return new Person(name, course, specialisation);
    }

}
