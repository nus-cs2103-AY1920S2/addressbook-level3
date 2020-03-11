package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddCommand;
<<<<<<< HEAD
import seedu.address.model.profile.Person;
=======
import seedu.address.model.profile.Profile;
>>>>>>> 90d899e4767de5feb6458cd9c34c6dedfafc7df9

/**
 * A utility class for Profile.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code profile}.
     * @param profile
     */
    public static String getNewCommand(Profile profile) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(profile);
    }

    /**
     * Returns the part of command string for the given {@code profile}'s details.
     */
    public static String getPersonDetails(Profile profile) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + profile.getName().fullName + " ");
        sb.append(PREFIX_COURSE + profile.getCourse().toString() + " ");
        return sb.toString();
    }

}
