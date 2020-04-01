package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.Parcel.returnorder.ReturnOrder;

/**
 * A utility class for Return.
 */
public class ReturnUtil {

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getReturnDetails(ReturnOrder returnOrder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TID + returnOrder.getTid().tid + " ");
        sb.append(PREFIX_NAME + returnOrder.getName().fullName + " ");
        sb.append(PREFIX_PHONE + returnOrder.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + returnOrder.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + returnOrder.getAddress().value + " ");
        sb.append(PREFIX_RETURN_TIMESTAMP + returnOrder.getTimestamp().value + " ");
        sb.append(PREFIX_WAREHOUSE + returnOrder.getWarehouse().address + " ");
        sb.append(PREFIX_COMMENT + returnOrder.getComment().commentMade + " ");
        sb.append(PREFIX_TYPE + returnOrder.getItemType().itemType + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditParcelDescriptor}'s details.
     */
    public static String getEditReturnOrderDescriptorDetails(EditCommand.EditParcelDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTid().ifPresent(Tid -> sb.append(PREFIX_TID).append(Tid.tid).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getTimeStamp().ifPresent(timeStamp -> sb.append(PREFIX_RETURN_TIMESTAMP)
            .append(timeStamp.value).append(" "));
        descriptor.getWarehouse().ifPresent(warehouse -> sb.append(PREFIX_WAREHOUSE)
            .append(warehouse.address).append(" "));
        descriptor.getComment().ifPresent(comment -> sb.append(PREFIX_COMMENT).append(comment.commentMade).append(" "));
        descriptor.getItemType().ifPresent(itemType -> sb.append(PREFIX_TYPE).append(itemType.itemType).append(" "));

        return sb.toString();
    }
}
