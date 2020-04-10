package team.easytravel.model.listmanagers.activity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;
import team.easytravel.model.util.uniquelist.UniqueListElement;

/**
 * Represents a Activity in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Activity implements UniqueListElement {

    // Identity fields
    private final Title title;
    private final Duration duration;
    private final Location location;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Optional<DateTime> scheduledDateTime;

    /**
     * Every field must be present and not null.
     */
    public Activity(Title title, Duration duration, Location location, Set<Tag> tags,
                    Optional<DateTime> scheduledDateTime) {
        CollectionUtil.requireAllNonNull(title, duration, location, tags, scheduledDateTime);
        this.title = title;
        this.duration = duration;
        this.location = location;
        this.tags.addAll(tags);
        this.scheduledDateTime = scheduledDateTime;
    }

    public Title getTitle() {
        return title;
    }

    public Duration getDuration() {
        return duration;
    }

    public Location getLocation() {
        return location;
    }


    public Optional<DateTime> getScheduledDateTime() {
        return scheduledDateTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both accommodation bookings has the same identity fields.
     * This defines a weaker notion of equality between two accommodation bookings.
     */
    @Override
    public boolean isSame(UniqueListElement other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return title.equals(otherActivity.title)
                && duration.equals(otherActivity.duration)
                && location.equals(otherActivity.location);
    }

    /**
     * Returns true if both Activities have the same identity and data fields.
     * This defines a stronger notion of equality between two Activities.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return title.equals(otherActivity.title)
                && duration.equals(otherActivity.duration)
                && location.equals(otherActivity.location)
                && getTags().equals(otherActivity.getTags())
                && scheduledDateTime.equals(otherActivity.scheduledDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration, location, tags, scheduledDateTime);
    }

    @Override
    public String toString() {
        return "Activity - Title: " + title
                + " Duration: " + duration
                + " Is Scheduled: " + scheduledDateTime.isPresent()
                + " Tags: "
                + getTags()
                .stream()
                .map(Tag::toString)
                .collect(Collectors.joining(" "));
    }
}

