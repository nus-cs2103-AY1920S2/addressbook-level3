package seedu.address.model.profile;

//@@author chanckben
/**
 * Represents a profile's year of study in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Year {

    public static final String MESSAGE_CONSTRAINTS = "Please enter the year in the form Y.S, where Y is the year "
            + "(1 to 8) and S is the semester (1 or 2).";

    public static final String VALIDATION_REGEXES = "[1-8][.][1-2]";

    public final String year;
    public final int semester;
    public final int intYear;

    public Year(String year) {
        this.year = year;
        intYear = Integer.parseInt(year.split("\\.")[0]);
        int intSem = Integer.parseInt(year.split("\\.")[1]);
        semester = 2 * (intYear - 1) + intSem;
    }

    public static boolean isValidCode(String year) {
        return year.matches(VALIDATION_REGEXES);
    }

    public int getSemester() {
        return semester;
    }


    @Override
    public String toString() {
        return year;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && (semester == ((Year) other).semester)); // state check
    }
}
