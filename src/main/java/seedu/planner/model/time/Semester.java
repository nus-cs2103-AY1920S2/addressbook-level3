package seedu.planner.model.time;

/**
 * Represents a semester at NUS.
 */
public enum Semester {
    ONE("Semester 1"),
    TWO("Semester 2"),
    SPECIAL_TERM_ONE("Special Semester 1"),
    SPECIAL_TERM_TWO("Special Semester 2");

    public static final String MESSAGE_CONSTRAINTS = "Semester must be one of the following: "
            + getConcatenatedString();
    private final String fullName;

    Semester(String fullName) {
        this.fullName = fullName;
    }

    private static String getConcatenatedString() {
        return "1, 2, st1, st2";
    }

    public String getFullName() {
        return this.fullName;
    }
}
