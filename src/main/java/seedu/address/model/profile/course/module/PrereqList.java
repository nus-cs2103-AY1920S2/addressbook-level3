package seedu.address.model.profile.course.module;

import java.util.ArrayList;

/**
 * Represents a Module's list of prerequisites in the address book.
 * Guarantees:
 */
public class PrereqList {

    public final ArrayList<String> prereqList;

    /**
     * Constructs a {@code PrereqList}.
     *
     * @param prereqs A valid string of prerequisites
     */
    public PrereqList(String prereqs) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        ArrayList<String> prereqList = new ArrayList<>();
        /*
        String[] prereqArray = prereqs.split("");
        for (String prereq : prereqArray) {
            prereqList.add(prereq);
        }
         */
        this.prereqList = prereqList;
    }

    @Override
    public String toString() {
        if (prereqList.size() == 0) {
            return null;
            //return "PreReqList placeholder: This module has no prereqs";
        }

        final StringBuilder builder = new StringBuilder();
        /*
        for (String prereq : prereqList) {
            builder.append(prereq);
         }
         */
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrereqList // instanceof handles nulls
                && prereqList.equals(((PrereqList) other).prereqList)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
