package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.item.Internship;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Role;
import seedu.address.model.item.field.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Internship objects.
 */
public class InternshipBuilder {
    public static final String DEFAULT_NAME = "Duke";
    public static final String DEFAULT_FROM = "03-2020";
    public static final String DEFAULT_TO = "06-2020";
    public static final String DEFAULT_ROLE = "Software engineering intern";
    public static final String DEFAULT_DESCRIPTION = "Did some work, made some money";
    public static final String[] DEFAULT_TAGS = {"java", "backend"};


    private Name name;
    private Set<Tag> tags = new HashSet<>();
    private Time from;
    private Time to;
    private Description description;
    private Role role;

    public InternshipBuilder() {
        name = new Name(DEFAULT_NAME);
        from = new Time(DEFAULT_FROM);
        to = new Time(DEFAULT_TO);
        role = new Role(DEFAULT_ROLE);
        description = new Description(DEFAULT_DESCRIPTION);
        tags.addAll(Arrays.stream(DEFAULT_TAGS).map(Tag::new).collect(Collectors.toList()));
    }

    public InternshipBuilder(Internship internshipToCopy) {
        name = internshipToCopy.getName();
        from = internshipToCopy.getFrom();
        to = internshipToCopy.getTo();
        role = internshipToCopy.getRole();
        description = internshipToCopy.getDescription();
        tags = new HashSet<>(internshipToCopy.getTags());
    }

    /**
     * Creates a InternshipBuilder with a certain name.
     * @param name String name
     * @return InternshipBuilder with new name
     */
    public InternshipBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Creates a InternshipBuilder with a certain role.
     * @param role String role
     * @return InternshipBuilder with new role
     */
    public InternshipBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Creates a InternshipBuilder with a certain to.
     * @param to String to
     * @return InternshipBuilder with new to
     */
    public InternshipBuilder withTo(String to) {
        this.to = new Time(to);
        return this;
    }

    /**
     * Creates a InternshipBuilder with a certain from.
     * @param from String from
     * @return InternshipBuilder with new from
     */
    public InternshipBuilder withFrom(String from) {
        this.from = new Time(from);
        return this;
    }


    /**
     * Creates a InternshipBuilder with a certain description.
     * @param description String description
     * @return InternshipBuilder with new description
     */
    public InternshipBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Internship} that we are building.
     */
    public InternshipBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Internship build() {
        return new Internship(name, role, from, to, description, tags);
    }
}
