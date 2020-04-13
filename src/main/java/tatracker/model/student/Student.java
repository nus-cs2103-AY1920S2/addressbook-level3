//@@author fatin99

package tatracker.model.student;

import static tatracker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tatracker.model.tag.Tag;


/**
 * Represents a Student in the Ta-Tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Matric matric;
    private final Name name;

    // Optional fields
    private final Phone phone;
    private final Email email;
    private final Set<Tag> tags;

    //@@author potatocombat

    private final Rating rating;

    //@@author fatin99

    /**
     * Every field must be present and not null.
     */
    public Student(Matric matric, Name name, Phone phone, Email email, Rating rating, Set<Tag> tags) {
        requireAllNonNull(matric, name, phone, email, rating, tags);
        this.matric = matric;
        this.name = name;

        this.phone = phone;
        this.email = email;

        this.tags = new HashSet<>();
        this.tags.addAll(tags);

        //@@author potatocombat

        this.rating = rating;
    }

    //@@author fatin99

    public Matric getMatric() {
        return matric;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    //@@author potatocombat

    public Rating getRating() {
        return rating;
    }

    //@@author fatin99

    /**
     * Returns true if both students of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }
        return otherStudent != null && otherStudent.getMatric().equals(getMatric());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getPhone().equals(getPhone())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getMatric().equals(getMatric())
                && otherStudent.getRating().equals(getRating())
                && otherStudent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, matric, rating, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        // Append identity fields
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Matric: ")
                .append(getMatric());

        // Append optional fields
        builder.append(" Rating: ")
                .append(getRating());

        // Append Tags
        builder.append(" Tags: ");
        getTags().forEach(builder::append);

        return builder.toString();
    }
}
