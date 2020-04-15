package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Birthday birthday;
    private final Organization organization;
    private final ArrayList<Remark> remarks = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, ArrayList<Remark> remarks,
                Birthday birthday, Organization organization, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags); // do I need to put organization here?
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.organization = organization;
        this.remarks.addAll(remarks);
        this.tags.addAll(tags);
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

    public ArrayList<Remark> getRemark() {
        return remarks;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public Organization getOrganization() {
        return organization;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Concatenates all the Person's tags together into one string.
     * This is to be used by the TagsContainsKeywordsPredicate class
     * @return String of all concatenated tags
     */
    public String getTagsForPredicate() {
        String concatenatedTags = "";

        for (Iterator<Tag> it = tags.iterator(); it.hasNext(); ) {
            Tag t = it.next();
            concatenatedTags = concatenatedTags + t.toString().toLowerCase();
        }

        return concatenatedTags;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getRemark().equals(getRemark())
                && otherPerson.getBirthday().equals(getBirthday())
                && otherPerson.getOrganization().equals(getOrganization())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, remarks, birthday, organization, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nPhone: ")
                .append(getPhone())
                .append("\nEmail: ")
                .append(getEmail())
                .append("\nAddress: ")
                .append(getAddress())
                .append("\nRemarks: ");
        getRemark().forEach(builder::append);
        builder.append("\nBirthday: ")
                .append(getBirthday())
                .append("\nOrganization: ")
                .append(getOrganization())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
