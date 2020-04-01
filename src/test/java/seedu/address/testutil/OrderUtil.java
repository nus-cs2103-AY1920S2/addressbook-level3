package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.model.parcel.order.Order;

/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add command string for adding the {@code order}.
     */
    public static String getInsertCommand(Order order) {
        return InsertCommand.COMMAND_WORD + " " + getOrderDetails(order);
    }

    /**
     * Returns an add command string for adding the {@code order}.
     */
    public static String getReturnCommand(Order order) {
        return ReturnCommand.COMMAND_WORD + " " + getOrderDetailsForReturn(order);
    }

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TID + order.getTid().tid + " ");
        sb.append(PREFIX_NAME + order.getName().fullName + " ");
        sb.append(PREFIX_PHONE + order.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + order.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + order.getAddress().value + " ");
        sb.append(PREFIX_DELIVERY_TIMESTAMP + order.getTimestamp().value + " ");
        sb.append(PREFIX_WAREHOUSE + order.getWarehouse().address + " ");
        sb.append(PREFIX_COD + order.getCash().cashOnDelivery + " ");
        sb.append(PREFIX_COMMENT + order.getComment().commentMade + " ");
        sb.append(PREFIX_TYPE + order.getItemType().itemType + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getOrderDetailsForReturn(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TID + order.getTid().tid + " ");
        sb.append(PREFIX_NAME + order.getName().fullName + " ");
        sb.append(PREFIX_PHONE + order.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + order.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + order.getAddress().value + " ");
        sb.append(PREFIX_RETURN_TIMESTAMP + order.getTimestamp().value + " ");
        sb.append(PREFIX_WAREHOUSE + order.getWarehouse().address + " ");
        sb.append(PREFIX_COMMENT + order.getComment().commentMade + " ");
        sb.append(PREFIX_TYPE + order.getItemType().itemType + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditParcelDescriptor}'s details.
     */
    public static String getEditOrderDescriptorDetails(EditCommand.EditParcelDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTid().ifPresent(Tid -> sb.append(PREFIX_TID).append(Tid.tid).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getTimeStamp().ifPresent(timeStamp -> sb.append(PREFIX_DELIVERY_TIMESTAMP)
                .append(timeStamp.value).append(" "));
        descriptor.getWarehouse().ifPresent(warehouse -> sb.append(PREFIX_WAREHOUSE)
                .append(warehouse.address).append(" "));
        descriptor.getCash().ifPresent(cash -> sb.append(PREFIX_COD).append(cash.cashOnDelivery).append(" "));
        descriptor.getComment().ifPresent(comment -> sb.append(PREFIX_COMMENT).append(comment.commentMade).append(" "));
        descriptor.getItemType().ifPresent(itemType -> sb.append(PREFIX_TYPE).append(itemType.itemType).append(" "));

        return sb.toString();
    }
}
