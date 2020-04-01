package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

import nasa.model.activity.Activity;

/**
 * Represents a SortMethod in NASA.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSortMethod(String)}
 */
public class SortMethod {

    public static final String MESSAGE_CONSTRAINTS = "Sort method must be either name, date, or priority.";
    private final String sortMethodString;
    private final Comparator<Activity> comparator;

    /**
     * Constructs a {@code SortMethod}.
     * @param method A valid method of sorting.
     */
    public SortMethod(String method) {
        requireNonNull(method);
        checkArgument(isValidSortMethod(method), MESSAGE_CONSTRAINTS);
        this.sortMethodString = method;
        this.comparator = generateComparator(method);
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
     * Returns the Comparator of the sort method.
     * @return The Comparator used to sort the activity list.
     */
    public Comparator<Activity> getComparator() {
        return comparator;
    }

    /**
     * Returns the comparator used to sort the activity list.
     * @return The comparator of this instance of {@code SortMethod}.
     */
    public Comparator<Activity> generateComparator(String method) {
        /*
        Default comparator, sorts in ascending order.
        Lexicographically biggest, latest added, highest priority at the top of the module activity list.
         */
        Comparator<Activity> nameSorter = Comparator.comparing(l -> l.getName().toString(),
                String.CASE_INSENSITIVE_ORDER.reversed());
        Comparator<Activity> dateSorter = Comparator.comparing(l -> l.getDate().getDate(),
                Comparator.reverseOrder());
        Comparator<Activity> prioritySorter = Comparator.comparing(l -> l.getPriority().toString(),
                Comparator.reverseOrder());

        switch (method) {
        case "name":
            System.out.println("Attempting to sort by name");
            return nameSorter;
        case "date":
            System.out.println("Attempting to sort by date");
            return dateSorter;
        case "priority":
            System.out.println("Attempting to sort by priority");
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
