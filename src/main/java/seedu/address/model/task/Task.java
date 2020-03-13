package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.ui.MainWindow;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the task list. Guarantees: details are present and not null, field values
 * are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final Priority priority;

    // Data fields
    private final Description description;
    private final Done done;
    private final Set<Tag> tags = new HashSet<>();

    /** Every field must be present and not null. */
    public Task(Name name, Priority priority, Description description, Done done, Set<Tag> tags) {
        requireAllNonNull(name, priority, description, tags);
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.done = done;
        this.tags.addAll(tags);
        timedPrint(10);

    }

    // without done provided
    public Task(Name name, Priority priority, Description description, Set<Tag> tags) {
        requireAllNonNull(name, priority, description, tags);
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.done = new Done();
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public Description getDescription() {
        return description;
    }

    public Done getDone() {
        return done;
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
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName())
                && (otherTask.getPriority().equals(getPriority()));
    }

    public void timedPrint(long delay) {
        MainWindow.triggerReminder("reminder test", delay);
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

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(getName())
                && otherTask.getPriority().equals(getPriority())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
