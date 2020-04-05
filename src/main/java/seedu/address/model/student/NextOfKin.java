package seedu.address.model.student;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *  Class representing Next of kin details of students.
 */
public class NextOfKin {

    public static final String MESSAGE_CONSTRAINTS =
            "NOK should be in this format: [name]-[relationship]-[contact] e.g, Jim-Father-93259589 "
                    + "\n 1. Names should only contain alphanumeric characters and spaces, and it should not be blank "
                    + "\n 2. Relationship of NOK should be family members only "
                    + "\n 3. Phone numbers should only contain numbers, and it should be at least 3 digits long";
    private static final String VALIDATION_REGEX_NOKNAME = "[\\p{Alnum}][\\p{Alnum} ]*";
    private static final String VALIDATION_REGEX_NOKCONTACT = "\\d{3,}";
    public final String value;

    private final String detailsOfNok;
    private final String nameOfNok;
    private final String relationshipOfNok;
    private final String contactOfNok;

    public NextOfKin(String detailsOfNok) {
        checkArgument(isValidNok(detailsOfNok), MESSAGE_CONSTRAINTS);
        if (detailsOfNok.equals("Insert NOK details here!")) {
            this.detailsOfNok = detailsOfNok;
            this.nameOfNok = "Insert NOK name here!";
            this.relationshipOfNok = "Insert NOK relationship here!";
            this.contactOfNok = "Insert NOK contact here!";
            value = detailsOfNok;
        } else {
            this.detailsOfNok = detailsOfNok;
            this.nameOfNok = this.detailsOfNok.split("-")[0];
            this.relationshipOfNok = this.detailsOfNok.split("-")[1];
            this.contactOfNok = this.detailsOfNok.split("-")[2];
            value = detailsOfNok;
        }
    }



    public String getNameOfNok() {
        return this.nameOfNok.trim();
    }

    public String getRelationshipOfNok() {
        return this.relationshipOfNok.trim();
    }

    public String getContactOfNok() {
        return this.contactOfNok.trim();
    }

    /**
     * Returns true if Nok name is valid
     * @param test String of Nok name
     * @return true or false depending on validation
     */
    public static boolean isValidNokName(String test) {
        if (test.equals("Insert NOK details here!")) {
            return true;
        } else {
            return test.matches(VALIDATION_REGEX_NOKNAME);
        }
    }

    /**
     * Returns true if Nok relationship is valid
     * @param test String of Nok relationship eg, father/mother
     * @return true or false depending on validation
     */
    public static boolean isValidNokRelationship(String test) {
        String lowerCaseTest = test.trim().toLowerCase();
        switch (lowerCaseTest) {
        case "Insert NOK details here!":
            return true;
        case "father":
            return true;
        case "mother":
            return true;
        case "sister":
            return true;
        case "brother":
            return true;
        case "grandfather":
            return true;
        case "grandmother":
            return true;
        default:
            return false;
        }
    }

    /**
     * Returns true if Nok contact is valid
     * @param test String of Nok contact
     * @return true or false depending on validation
     */
    public static boolean isValidNokContact(String test) {
        if (test.equals("Insert NOK details here!")) {
            return true;
        } else {
            return test.trim().matches(VALIDATION_REGEX_NOKCONTACT);
        }
    }

    /**
     * Returns true if Nok details are valid
     * @param test String of Nok details eg, jim-father-94342983
     * @return true or false depending on validation
     */
    public static boolean isValidNok(String test) {
        if (test.equals("Insert NOK details here!")) {
            return true;
        } else {
            if (!test.contains("-")) {
                return false;
            } else if (test.split("-").length != 3) {
                return false;
            } else {
                String nameOfNok = test.split("-")[0];
                String relationshipOfNok = test.split("-")[1];
                String contactOfNok = test.split("-")[2];
                return isValidNokName(nameOfNok) && isValidNokContact(contactOfNok)
                        && isValidNokRelationship(relationshipOfNok);
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
