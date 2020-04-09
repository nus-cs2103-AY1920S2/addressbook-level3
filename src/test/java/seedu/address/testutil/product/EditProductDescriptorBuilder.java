package seedu.address.testutil.product;

import java.util.UUID;

import seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.QuantityThreshold;

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
        descriptor.setId(product.getId());
        descriptor.setDescription(product.getDescription());
        descriptor.setCostPrice(product.getCostPrice());
        descriptor.setPrice(product.getPrice());
        descriptor.setQuantity(product.getQuantity());
        descriptor.setSales(product.getMoney());
        descriptor.setThreshold(product.getThreshold());
    }

    /**
     * Sets the {@code UUID} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withId(UUID id) {
        descriptor.setId(id);
        return this;
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
    public EditProductDescriptorBuilder withCostPrice(String costPrice) {
        descriptor.setCostPrice(new CostPrice(costPrice));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new ProductQuantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Sales} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withSales(String sales) {
        descriptor.setSales(new Money(sales));
        return this;
    }

    /**
     * Sets the {@code QuantityThreshold} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withThreshold(String threshold) {
        descriptor.setThreshold(new QuantityThreshold(threshold));
        return this;
    }

    public EditProductDescriptor build() {
        return descriptor;
    }
}
