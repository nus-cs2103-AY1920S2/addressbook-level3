package seedu.address.model.modelFinance;

import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Finance in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Finance extends ModelObject {

    // Identity fields
    private final String ENTITY_NAME = "Finance";
    private final Name name;
    private final ID id;
    private final FinanceType financeType;
    private final Date date;
    private final Amount amount;
    private ID courseid;
    private ID studentid;
    private ID staffid;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * A finance object not tied to any modelObjects.
     */
    public Finance(Name name, FinanceType financeType, Date date, Amount amount, Set<Tag> tags)
            throws ParseException {
        requireAllNonNull(name, financeType, date, amount, tags);
        this.name = name;
        this.id = UuidManager.assignNewUUID(this);
        this.financeType = financeType;
        this.date = date;
        this.amount = amount;
        this.tags.addAll(tags);
        this.courseid = new ID();
        this.studentid = new ID();
        this.staffid = new ID();
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Finance(Name name, ID id, FinanceType financeType, Date date, Amount amount, Set<Tag> tags) {
        requireAllNonNull(name, financeType, date, amount, tags);
        this.name = name;
        this.id = id;
        this.financeType = financeType;
        this.date = date;
        this.amount = amount;
        this.tags.addAll(tags);
        this.courseid = new ID();
        this.studentid = new ID();
        this.staffid = new ID();
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Finance(Name name, FinanceType financeType, Date date, Amount amount, ID courseid, ID studentid, ID staffid, Set<Tag> tags)
            throws ParseException {
        requireAllNonNull(name, financeType, date, amount, tags);
        requireAllNonNull(courseid, studentid, staffid);
        this.name = name;
        this.id = UuidManager.assignNewUUID(this);
        this.financeType = financeType;
        this.date = date;
        this.amount = amount;
        this.tags.addAll(tags);
        this.courseid = courseid;
        this.studentid = studentid;
        this.staffid = staffid;
    }

    /**
     * Overloaded constructor for edited object, loaded from storage, or sample data.
     */
    public Finance(Name name, ID id, FinanceType financeType, Date date, Amount amount, ID courseid, ID studentid, ID staffid, Set<Tag> tags) {
        requireAllNonNull(courseid, studentid, staffid);
        this.name = name;
        this.id = id;
        this.financeType = financeType;
        this.date = date;
        this.amount = amount;
        this.tags.addAll(tags);
        this.courseid = courseid;
        this.studentid = studentid;
        this.staffid = staffid;
    }

    /**
     * Creates and returns a copy of this finance.
     *
     * @return a clone of this instance.
     */
    public Finance clone() {
        return new Finance(name, id, financeType, date, amount, courseid, studentid, staffid, tags);
    }

    /**
     * Get Name of a finance.
     *
     * @return name of this finance.
     */
    public Name getName() {
        return name;
    }

    /**
     * Get ID of a finance.
     *
     * @return ID of this finance.
     */
    public ID getId() {
        return id;
    }

    /**
     * Get type of this finance.
     *
     * @return type of this finance.
     */
    public FinanceType getFinanceType() {
        return financeType;
    }

    /**
     * Get date of this finance.
     *
     * @return date of this finance.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get amount of this finance.
     *
     * @return amount of this finance.
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Get course ID associated with this finance.
     *
     * @return course ID associated with this finance.
     */
    public ID getCourseID() {
        return courseid;
    }

    /**
     * Get student ID associated with this finance.
     *
     * @return student ID associated with this finance.
     */
    public ID getStudentID() {
        return studentid;
    }

    /**
     * Get staff ID associated with this finance.
     *
     * @return staff ID associated with this finance.
     */
    public ID getStaffID() {
        return staffid;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     *
     * @return set of tags of this finance.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both finances of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two finances.
     *
     * @param otherFinance other Finance
     * @return true if both finances same name have at least one other same identity field,
     * false otherwise.
     */
    public boolean weakEquals(ModelObject otherFinance) {
        if (otherFinance == this) {
            return true;
        }

        if (!(otherFinance instanceof Finance)) {
            return false;
        }
        Finance otherFinanceCast = (Finance) otherFinance;
        return otherFinanceCast != null
                && otherFinanceCast.getName().equals(getName())
                && otherFinanceCast.getAmount().equals(getAmount());
    }

    /**
     * Returns true if both finances have the same identity and data fields. This defines a stronger
     * notion of equality between two finances.
     *
     * @param other other finance
     * @return true if both finances have the same identity and data fields, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Finance)) {
            return false;
        }

        Finance otherFinance = (Finance) other;
        return otherFinance.getName().equals(getName())
                && otherFinance.getFinanceType().equals(getFinanceType())
                && otherFinance.getDate().equals(getDate())
                && otherFinance.getAmount().equals(getAmount())
                && otherFinance.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, financeType, date, amount, courseid, studentid, staffid, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" FinanceType: ")
                .append(getFinanceType())
                .append(" Date: ")
                .append(getDate())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
