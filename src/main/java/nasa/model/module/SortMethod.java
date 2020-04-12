package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

import nasa.model.activity.Deadline;
import nasa.model.activity.Event;

/* @@author don-tay */
/**
 * Represents a SortMethod in NASA.
 * Event and deadline have separate comparators, given by {@link #generateDeadlineComparator(String)}
 * and {@link #generateEventComparator(String)} respectively.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSortMethod(String)}
 */
public class SortMethod {

    public static final String MESSAGE_CONSTRAINTS = "Sort method must be either name, date, or priority.";
    public static final Comparator STUB_COMPARATOR = (a, b) -> 0; //Stub comparator
    private final String sortMethodString;
    private final Comparator<Deadline> deadlineComparator;
    private final Comparator<Event> eventComparator;

    /**
     * Constructs a {@code SortMethod}.
     * @param method A valid method of sorting.
     */
    public SortMethod(String method) {
        requireNonNull(method);
        checkArgument(isValidSortMethod(method), MESSAGE_CONSTRAINTS);
        this.sortMethodString = method;
        this.deadlineComparator = generateDeadlineComparator(method);
        this.eventComparator = generateEventComparator(method);

    }

    /**
     * Returns true if a given string is a valid method of sorting.
     */
    public static boolean isValidSortMethod(String test) {
        return (test.equals("name") || test.equals("date") || test.equals("priority"));
    }

    /**
     * Returns the string value of the sort method.
     * @return The string representation of the method of sorting.
     */
    public String getSortMethodString() {
        return sortMethodString;
    }

    /**
     * Returns the DeadlineComparator of the sort method.
     * @return The Comparator used to sort the deadline list.
     */
    public Comparator<Deadline> getDeadlineComparator() {
        return deadlineComparator;
    }

    /**
     * Returns the EventComparator of the sort method.
     * @return The EventComparator used to sort the event list.
     */
    public Comparator<Event> getEventComparator() {
        return eventComparator;
    }

    /**
     * Generates and returns the DeadlineComparator used to sort the deadline list.
     * Default deadline comparator, sorts in ascending order.
     * Lexicographically smallest(a-z), earliest due date, highest priority at the top of the module deadline list.
     * @return The DeadlineComparator of this instance of {@code SortMethod}.
     */
    public Comparator<Deadline> generateDeadlineComparator(String method) {
        Comparator<Deadline> nameSorter = Comparator.comparing(l -> l.getName().toString(),
                String.CASE_INSENSITIVE_ORDER);
        Comparator<Deadline> dateSorter = Comparator.comparing(l -> l.getDueDate().toString());
        Comparator<Deadline> prioritySorter = Comparator.comparing(l -> l.getPriority().toString(),
                Comparator.reverseOrder());

        switch (method) {
        case "name":
            return nameSorter;
        case "date":
            return dateSorter;
        case "priority":
            return prioritySorter;
        default:
            throw new IllegalStateException("Unexpected value: " + getSortMethodString());
        }
    }

    /**
     * Generates and returns the EventComparator used to sort the event list.
     * Default event comparator, sorts in ascending order.
     * Lexicographically smallest(a-z), earliest start date, at the top of the module event list.
     * Will not sort based on priority.
     * @return The DeadlineComparator of this instance of {@code SortMethod}.
     */
    public Comparator<Event> generateEventComparator(String method) {
        Comparator<Event> nameSorter = Comparator.comparing(l -> l.getName().toString(),
                String.CASE_INSENSITIVE_ORDER);
        Comparator<Event> dateSorter = Comparator.comparing(l -> l.getStartDate().toString());
        Comparator<Event> prioritySorter = STUB_COMPARATOR;

        switch (method) {
        case "name":
            return nameSorter;
        case "date":
            return dateSorter;
        case "priority":
            return prioritySorter;
        default:
            throw new IllegalStateException("Unexpected value: " + getSortMethodString());
        }
    }

    @Override
    public String toString() {
        return getSortMethodString();
    }

}
