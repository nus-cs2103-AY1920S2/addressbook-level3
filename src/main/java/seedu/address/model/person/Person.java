package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Time time;

    private final ActivityList activityList = new ActivityList(new ArrayList<>());
    private final FrequencyList placeList2;
    private final FrequencyList activityList2;
    private final PlaceList placeList = new PlaceList(new ArrayList<>());
    private final TimeList timeList = new TimeList(new ArrayList<>());
    private final RecentEventList recentEventList;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Time time, PlaceList placeList,
                  ActivityList activityList, TimeList timeList) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.time = time;
        this.placeList.setPlaceList(placeList.getPlaceList());
        this.activityList.setActivityList(activityList.getActivityList());
        this.timeList.setTimeList(timeList.getTimeList());
        placeList2 = new FrequencyList();
        placeList2.generate(this.placeList.getPlaceList());
        activityList2 = new FrequencyList();
        activityList2.generate(this.activityList.getActivityList());
        recentEventList = new RecentEventList();
        recentEventList.generate(this.placeList, this.activityList, this.timeList);
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

    public Time getTime() {
        return time;
    }

    public PlaceList getPlaceList2() {
        return placeList;
    }

    public ActivityList getActivityList2() {
        return activityList;
    }

    public TimeList getTimeList() {
        return timeList;
    }

    public ObservableList<RecentEvent> getRecentEventList() {
        return recentEventList.getList();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is
     * the same. This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger
     * notion of equality between two persons.
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
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getTime().equals(getTime())
                && otherPerson.getPlaceList2().equals(getPlaceList2())
                && otherPerson.getActivityList2().equals(getActivityList2());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Time spent together: ")
                .append(getTime());
        builder.append(" Places been together: ")
                .append(getPlaceList2().toString());
        builder.append(" Activities done together: ")
                .append(getActivityList2().toString());
        return builder.toString();
    }

    public ObservableList<EventDescriptor> getPlaceList() {
        return placeList2.getFrequencyList();
    }

    public ObservableList<EventDescriptor> getActivityList() {
        return activityList2.getFrequencyList();
    }
}
