package seedu.address.testutil.product;

import seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.product.Sales;
import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;

/**
 * A utility class to help with building EditProductDescriptor objects.
 */
public class EditProductDescriptorBuilder {

    private EditProductDescriptor descriptor;

    public EditProductDescriptorBuilder() {
        descriptor = new EditProductDescriptor();
    }

    public EditProductDescriptorBuilder(EditProductDescriptor descriptor) {
        this.descriptor = new EditProductDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProductDescriptor} with fields containing {@code product}'s details
     */
    public EditProductDescriptorBuilder(Product product) {
        descriptor = new EditProductDescriptor();
        descriptor.setDescription(product.getDescription());
        descriptor.setPrice(product.getPrice());
        descriptor.setQuantity(product.getQuantity());
        descriptor.setSales(product.getSales());
    }

    /**
     * Sets the {@code Description} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withDescription(String name) {
        descriptor.setDescription(new Description(name));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withPrice(String phone) {
        descriptor.setPrice(new Price(phone));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withQuantity(String email) {
        descriptor.setQuantity(new Quantity(email));
        return this;
    }

    /**
     * Sets the {@code Sales} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withSales(String address) {
        descriptor.setSales(new Sales(address));
        return this;
    }

    public EditProductDescriptor build() {
        return descriptor;
    }
}
