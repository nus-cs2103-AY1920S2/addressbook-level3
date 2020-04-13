package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_INTERNSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.logic.commands.add.AddCommand;
import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.model.item.Internship;

/**
 * A utility class for Internship
 */
public class InternshipUtil {

    /**
     * Returns an add command string for adding the {@code internship}.
     * @param internship the {@code internship} to be added.
     * @return the command string for adding the {@code internship}.
     */
    public static String getAddCommand(Internship internship) {
        return AddCommand.COMMAND_WORD + " " + ITEM_TYPE_INTERNSHIP + " " + getInternshipDetails(internship);
    }

    public static String getEditCommand(int index, Internship internship) {
        return EditCommand.COMMAND_WORD + " " + index + " "
                + ITEM_TYPE_INTERNSHIP + " " + getInternshipDetails(internship);
    }

    /**
     * Returns the part of command string for the given {@code internship}'s details.
     * @param internship the {@code internship} to be added.
     * @return the part of command string for the given {@code internship}'s internship details.
     */
    public static String getInternshipDetails(Internship internship) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + internship.getName().fullName + " ");
        sb.append(PREFIX_ROLE + internship.getRole().value + " ");
        sb.append(PREFIX_FROM + internship.getFrom().value + " ");
        sb.append(PREFIX_TO + internship.getTo().value + " ");
        sb.append(PREFIX_DESCRIPTION + internship.getDescription().value + " ");
        internship.getTags().stream().map(x -> x.tagName).map(x -> (PREFIX_TAG + x + " ")).forEach(sb::append);
        return sb.toString();
    }
}
