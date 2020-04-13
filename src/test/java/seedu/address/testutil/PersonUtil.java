package seedu.address.testutil;

import static seedu.address.logic.parser.CliPrefix.PEOPLE_COMMAND_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.people.PeopleAddCommand;
import seedu.address.logic.commands.people.PeopleEditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Loan;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return PEOPLE_COMMAND_TYPE + " " + PeopleAddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        person.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        return sb.toString();
    }

    // @@author cheyannesim
    public static String getDebtDescription(Debt debt) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + debt.getDescription().description + " ");
        sb.append(PREFIX_AMOUNT + debt.getAmount().inDollars() + " ");
        sb.append(PREFIX_DATE + debt.getDate().getInputFormat());
        return sb.toString();
    }
    // @@author

    public static String getLoanDescription(Loan loan) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + loan.getDescription().description + " ");
        sb.append(PREFIX_AMOUNT + loan.getAmount().inDollars() + " ");
        sb.append(PREFIX_DATE + loan.getDate().getInputFormat());
        return sb.toString();
    }
}
