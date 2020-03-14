package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.order.Order;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Order order) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TID + order.getTID().tid + " ");
        sb.append(PREFIX_NAME + order.getName().fullName + " ");
        sb.append(PREFIX_PHONE + order.getPhone().value + " ");
        sb.append(PREFIX_ADDRESS + order.getAddress().value + " ");
        sb.append(PREFIX_WAREHOUSE + order.getWarehouse().address + " ");
        sb.append(PREFIX_COD + order.getCash().cashOnDelivery + " ");
        order.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        sb.append(PREFIX_COMMENT + order.getComment().commentMade + " ");
        order.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditOrderDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTID().ifPresent(transid -> sb.append(PREFIX_TID).append(transid.tid).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getWarehouse().ifPresent(warehouse -> sb.append(PREFIX_WAREHOUSE)
                .append(warehouse.address).append(" "));
        descriptor.getCash().ifPresent(cash -> sb.append(PREFIX_TID).append(cash.cashOnDelivery).append(" "));
        descriptor.getComment().ifPresent(comment -> sb.append(PREFIX_COMMENT).append(comment.commentMade).append(" "));
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
