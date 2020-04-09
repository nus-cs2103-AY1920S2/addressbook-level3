package seedu.address.testutil.product;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COSTPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALES;

import seedu.address.logic.commands.product.AddProductCommand;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.model.product.Product;

/**
 * A utility class for Product.
 */
public class ProductUtil {

    /**
     * Returns an add command string for adding the {@code product}.
     */
    public static String getAddCommand(Product product) {
        return AddProductCommand.COMMAND_WORD + " " + getProductDetails(product);
    }

    /**
     * Returns the part of command string for the given {@code product}'s details.
     */
    public static String getProductDetails(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + product.getDescription().value + " ");
        sb.append(PREFIX_COSTPRICE + product.getCostPrice().value + " ");
        sb.append(PREFIX_PRICE + product.getPrice().value + " ");
        sb.append(PREFIX_QUANTITY + String.valueOf(product.getQuantity().getValue()) + " ");
        sb.append(PREFIX_SALES + String.valueOf(product.getMoney().value) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditProductDescriptor}'s details.
     */
    public static String getEditProductDescriptorDetails(EditProductCommand.EditProductDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description
            -> sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
        descriptor.getCostPrice().ifPresent(costPrice
            -> sb.append(PREFIX_COSTPRICE).append(costPrice.value).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));
        descriptor.getQuantity().ifPresent(quantity
            -> sb.append(PREFIX_QUANTITY).append(quantity.getValue()).append(" "));
        descriptor.getMoney().ifPresent(sales -> sb.append(PREFIX_SALES).append(sales.value).append(" "));
        return sb.toString();
    }
}
