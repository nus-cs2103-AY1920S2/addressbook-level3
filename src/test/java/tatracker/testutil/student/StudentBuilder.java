package tatracker.testutil.student;

import java.util.HashSet;
import java.util.Set;

import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;
import tatracker.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_MATRIC = "A0193235J";
    public static final int DEFAULT_RATING = 1;

    private Name name;
    private Phone phone;
    private Email email;
    private Matric matric;
    private Rating rating;
    private Set<Tag> tags;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        matric = new Matric(DEFAULT_MATRIC);
        rating = new Rating(DEFAULT_RATING);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        matric = studentToCopy.getMatric();
        rating = studentToCopy.getRating();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Matric} of the {@code Student} that we are building.
     */
    public StudentBuilder withMatric(String matric) {
        this.matric = new Matric(matric);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Student} that we are building.
     */
    public StudentBuilder withRating(int rating) {
        this.rating = new Rating(rating);
        return this;
    }

    public Student build() {
        return new Student(matric, name, phone, email, rating, tags);
    }

}
