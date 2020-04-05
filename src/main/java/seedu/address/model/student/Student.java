package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Temperature temperature;
    private final Attendance attendance;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final NextOfKin nok;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, Temperature temperature, Attendance attendance,
                   NextOfKin nok, Remark remark, Set<Tag> tags) {


        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.temperature = temperature;
        this.attendance = attendance;
        this.remark = remark;
        this.tags.addAll(tags);
        this.nok = nok;
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

    public Address getAddress() {
        return address;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public Remark getRemark() {
        return remark;
    }

    public NextOfKin getNok () {
        return nok;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags () {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both students of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent (Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && (otherStudent.getPhone().equals(getPhone()) || otherStudent.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals (Object other) {
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
                && otherStudent.getAddress().equals(getAddress())
                && otherStudent.getTemperature().equals(getTemperature())
                && otherStudent.getAttendance().equals(getAttendance())
                && otherStudent.getTags().equals(getTags());
    }

    @Override
    public int hashCode () {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, temperature, attendance, remark, tags);
    }

    @Override
    public String toString () {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Temperature: ")
                .append(getTemperature())
                .append(" Attendance: ")
                .append(getAttendance())
                .append(" NextOfKin: ")
                .append(getNok())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
