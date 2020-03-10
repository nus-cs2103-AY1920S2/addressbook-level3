package seedu.expensela.testutil;

import static seedu.expensela.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.expensela.model.tag.Tag;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Transaction transaction) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(transaction);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + transaction.getName().transactionName + " ");
        sb.append(PREFIX_PHONE + transaction.getAmount().toString() + " ");
        sb.append(PREFIX_ADDRESS + transaction.getDate().value + " ");
        transaction.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.transactionName).append(" "));
        descriptor.getAmount().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.toString()).append(" "));
        descriptor.getDate().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
