package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;

/**
 * Represents a SortMethod in NASA.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSortMethod(String)}
 */
public class SortMethod {

    public static final String MESSAGE_CONSTRAINTS = "Sort method must be either name, date, or priority.";
    private final String sortMethodString;

    /**
     * Constructs a {@code SortMethod}.
     * @param method A valid method of sorting.
     */
    public SortMethod(String method) {
        requireNonNull(method);
        checkArgument(isValidSortMethod(method), MESSAGE_CONSTRAINTS);
        this.sortMethodString = method;
    }

    /**
     * Returns true if a given string is a valid method of sorting.
     */
    public static boolean isValidSortMethod(String test) {
        System.out.println(test);
        boolean isValid = (test.equals("name") || test.equals("date") || test.equals("priority") );
        System.out.println(isValid);
        return isValid;
    }

    /**
     * Returns the string value of the sort method.
     * @return The string representation of the method of sorting.
     */
    public String getSortMethodString() {
        return sortMethodString;
    }

}
