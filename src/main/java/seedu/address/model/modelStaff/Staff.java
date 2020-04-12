package seedu.address.model.modelStaff;

import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;

import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Staff in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 * A Staff can be a teacher or an admin. This is declared by permission Level.
 * A staff Level can be check by isTeacher() and isAdmin() method.
 */
public class Staff extends ModelObject {
    // Permission level
    public enum Level {
        TEACHER,
        ADMIN
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Level should only be either teacher or admin, and it should not be blank";
    // Identity fields
    private final Name name;
    private final ID id;
    private final Gender gender;
    private final Level level;
    private final Phone phone;
    private final Email email;
    private final Address address;

    // Data fields
    private Set<ID> assignedCoursesID = new HashSet<>();
    private final Salary salary;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Staff(Name name, Gender gender, Level level, Phone phone, Email email, Salary salary, Address address, Set<Tag> tags)
            throws ParseException {
        requireAllNonNull(name, level, phone, email, address, tags);
        this.name = name;
        this.id = UuidManager.assignNewUUID(this);
        this.gender = gender;
        this.level = level;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Override Staff constructor.
     */
    public Staff(Name name, ID id, Gender gender, Level level, Phone phone, Email email, Salary salary, Address address, Set<Tag> tags) {
        requireAllNonNull(name, level, phone, email, address, tags);
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.level = level;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Override Staff constructor.
     */
    public Staff(Name name, ID id, Gender gender, Level level, Phone phone, Email email, Salary salary, Address address, Set<ID> assignedCoursesID, Set<Tag> tags) {
        requireAllNonNull(name, level, phone, email, address, tags);
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.level = level;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.assignedCoursesID.addAll(assignedCoursesID);
        this.tags.addAll(tags);
    }


    /**
     * Creates and returns a copy of this staff.
     *
     * @return a clone of this instance.
     */
    public Staff clone() {
        return new Staff(name, id, gender, level, phone, email,
                salary, address, assignedCoursesID, tags);
    }

    /**
     * Get Name of a staff.
     *
     * @return name of this staff.
     */
    public Name getName() {
        return name;
    }

    /**
     * Get ID of a staff.
     *
     * @return ID of this staff.
     */
    public ID getId() {
        return id;
    }

    /**
     * Get Gender of a staff.
     *
     * @return Gender of this staff.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Indicate whether this staff is assigned to a course having this ID.
     *
     * @return true if this staff is assigned to a course having this ID,
     * false otherwise.
     */
    public boolean containsCourse(ID courseID) {
        return assignedCoursesID.contains(courseID);
    }

    /**
     * Remove this course ID that was assigned to this staff.
     */
    public void removeCourse(ID courseID) {
        this.assignedCoursesID.remove(courseID);
    }

    /**
     * Indicate whether a string is valid for Staff Permission Level.
     *
     * @param test the string to be checked for valid Staff Permission Level.
     * @return true if this string matches "admin" or "teacher", false otherwise.
     */
    public static boolean isValidLevel(String test) {
        String testTrimmed = test.toUpperCase().trim();
        return testTrimmed.equals(String.valueOf(Level.ADMIN))
                || testTrimmed.equals(String.valueOf(Level.TEACHER));
    }

    /**
     * Returns an immutable ID set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     *
     * @return the set of assigned courses of this staff.
     */
    public Set<ID> getAssignedCoursesID() {
        return Collections.unmodifiableSet(assignedCoursesID);
    }

    /**
     * Get a List of String representing the ID for assigned courses of this staff.
     *
     * @return List of String representing assigned courses ID for this staff.
     */
    public List<String> getAssignedCoursesIDString() {
        List<String> IDList = new ArrayList<>();
        for (ID id : assignedCoursesID) {
            IDList.add(id.toString());
        }
        return IDList;
    }

    /**
     * Assign a course ID to a staff (the staff must be a teacher)
     *
     * @param courseID the course ID that is assigned for staff (teacher) teaches
     */
    public void addCourse(ID courseID) {
        if (isTeacher())
            this.assignedCoursesID.add(courseID);
    }

    /**
     * Assign set of courses IDs to a staff (the staff must be a teacher)
     *
     * @param courseID the set of courses ID that are assigned for staff (teacher) teaches
     */
    public void addCourses(Set<ID> courseID) {
        if (isTeacher())
            this.assignedCoursesID.addAll(courseID);
    }

    /**
     * Check if this staff is a teacher.
     *
     * @return true if this is a teacher, false if this is an admin.
     */
    public boolean isTeacher() {
        return level == Level.TEACHER;
    }

    /**
     * Check if this staff is an admin.
     *
     * @return true if this is a teacher, false if this is an admin.
     */
    public boolean isAdmin() {
        return level == Level.ADMIN;
    }

    /**
     * Check if this staff is an admin.
     *
     * @return true if this is a teacher, false if this is an admin.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Get phone of a staff.
     *
     * @return Phone of this staff.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Get Email of a staff.
     *
     * @return Email of this staff.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Get salary of a staff.
     *
     * @return salary of this staff.
     */
    public Salary getSalary() {
        return salary;
    }

    /**
     * Get address of a staff.
     *
     * @return address of this staff.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     *
     * @return a set of unmodifiable tags.
     */
    public Set<Tag> getTags() throws UnsupportedOperationException {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Check if a staff has any Tags.
     *
     * @return true if the staff has at least 1 tag, false otherwise
     */
    public boolean hasTags() {
        return !tags.isEmpty();
    }

    /**
     * Compare if both staffs have the same name and at lest one other identity field
     * that is the same.
     * This defines a weaker notion of equality between two teachers.
     *
     * @return true if both staffs of the same name have at least another similar identity field
     * that is the same, false otherwise.
     */
    @Override
    public boolean weakEquals(ModelObject otherStaff) {
        if (otherStaff == this) {
            return true;
        }
        if (!(otherStaff instanceof Staff)) {
            return false;
        }
        Staff otherStaffCast = (Staff) otherStaff;
        return otherStaffCast.getName().equals(getName())
                && otherStaffCast.getPhone().equals(getPhone())
                && otherStaffCast.getEmail().equals(getEmail())
                && otherStaffCast.getAddress().equals(getAddress());
    }

    /**
     * Compare whether two staffs have the same identify and data fields.
     * This defines a stronger notation of equality between two staffs.
     *
     * @return true if both staffs have the same identity and data fields, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Staff)) {
            return false;
        }

        Staff otherStaff = (Staff) other;
        return otherStaff.getName().equals(getName())
                && otherStaff.getPhone().equals(getPhone())
                && otherStaff.getLevel().equals(getLevel())
                && otherStaff.getId().equals(getId())
                && otherStaff.getGender().equals(getGender())
                && otherStaff.getEmail().equals(getEmail())
                && otherStaff.getSalary().equals(getSalary())
                && otherStaff.getAddress().equals(getAddress())
                && otherStaff.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, salary, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String title = "";
        if (isTeacher()) {
            title = "(Teacher)";
        } else {
            title = "(Admin)";
        }
        builder.append(title);
        builder.append(" Name: ").append(getName());
        if (getPhone().isKnown()) {
            builder.append(" Phone: ").append(getPhone());
        }
        if (getEmail().isKnown()) {
            builder.append(" Email: ").append(getEmail());
        }
        builder.append(" Salary: ").append(getSalary());
        if (getAddress().isKnown()) {
            builder.append(" Address: ").append(getAddress());
        }
        if (hasTags()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
