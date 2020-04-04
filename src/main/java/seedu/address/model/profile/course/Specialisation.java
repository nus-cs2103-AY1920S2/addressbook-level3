package seedu.address.model.profile.course;

/**
 * Represents a Specialisation's name
 * Guarantees:
 */
public class Specialisation {

    public final String specialisationName;

    /**
     * Constructs a {@code Specialisation}.
     *
     * @param specialisationName A valid specialisation.
     */
    public Specialisation(String specialisationName) {
        String formattedSpecialisationName = format(specialisationName);
        this.specialisationName = formattedSpecialisationName;
    }

    /**
     * Returns formatted specialisation
     */
    public String format(String specialisationName) {
        specialisationName = specialisationName.trim();
        specialisationName = specialisationName.toUpperCase();
        return specialisationName;
    }

    // TODO: Check valid specialisation
    public static boolean isValid(CourseName courseName, String specialisationName) {
        return AcceptedSpecialisation.isValid(courseName, specialisationName);
    }

    @Override
    public String toString() {
        if (specialisationName.isEmpty()) {
            return "";
        } else {
            return specialisationName;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Specialisation // instanceof handles nulls
                && specialisationName.equals(((Specialisation) other).specialisationName)); // state check
    }

}
