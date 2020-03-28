package seedu.address.logic.commands.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COSTPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.model.util.QuantityThreshold;

/**
 * Edits the details of an existing product in the address book.
 */
public class EditProductCommand extends Command {

    public static final String COMMAND_WORD = "editp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the product identified "
            + "by the index number used in the displayed product list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_COSTPRICE + "COST PRICE]"
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_SALES + "SALES] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Black watch ";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Product: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the product list.";

    private final Index index;
    private final EditProductDescriptor editProductDescriptor;

    /**
     * @param index of the product in the filtered product list to edit
     * @param editProductDescriptor details to edit the product with
     */
    public EditProductCommand(Index index, EditProductDescriptor editProductDescriptor) {
        requireNonNull(index);
        requireNonNull(editProductDescriptor);

        this.index = index;
        this.editProductDescriptor = new EditProductDescriptor(editProductDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToEdit = lastShownList.get(index.getZeroBased());
        Product editedProduct = createEditedProduct(productToEdit, editProductDescriptor);

        if (!productToEdit.isSameProduct(editedProduct) && model.hasProduct(editedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct));
    }

    /**
     * Creates and returns a {@code Product} with the details of {@code productToEdit}
     * edited with {@code editProductDescriptor}.
     */
    public static Product createEditedProduct(Product productToEdit, EditProductDescriptor editProductDescriptor) {
        assert productToEdit != null;

        Description updatedDescription = editProductDescriptor.getDescription().orElse(productToEdit.getDescription());
        CostPrice updatedCostPrice = editProductDescriptor.getCostPrice().orElse(productToEdit.getCostPrice());
        Price updatedPrice = editProductDescriptor.getPrice().orElse(productToEdit.getPrice());
        Quantity updatedQuantity = editProductDescriptor.getQuantity().orElse(productToEdit.getQuantity());
        Money updatedSales = editProductDescriptor.getSales().orElse(productToEdit.getSales());
        UUID updatedId = editProductDescriptor.getId().orElse(productToEdit.getId());
        QuantityThreshold updatedThreshold = editProductDescriptor.getThreshold().orElse(productToEdit.getThreshold());

        return new Product(updatedDescription, updatedCostPrice, updatedPrice, updatedQuantity,
                updatedSales, updatedThreshold, updatedId);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProductCommand)) {
            return false;
        }

        // state check
        EditProductCommand e = (EditProductCommand) other;
        return index.equals(e.index)
                && editProductDescriptor.equals(e.editProductDescriptor);
    }

    /**
     * Stores the details to edit the product with. Each non-empty field value will replace the
     * corresponding field value of the product.
     */
    public static class EditProductDescriptor {
        private Description description;
        private CostPrice costPrice;
        private Price price;
        private Quantity quantity;
        private Money sales;
        private UUID id;
        private QuantityThreshold threshold;

        public EditProductDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProductDescriptor(EditProductDescriptor toCopy) {
            setDescription(toCopy.description);
            setCostPrice(toCopy.costPrice);
            setPrice(toCopy.price);
            setQuantity(toCopy.quantity);
            setSales(toCopy.sales);
            setId(toCopy.id);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, costPrice, price, quantity, sales);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCostPrice(CostPrice costPrice) {
            this.costPrice = costPrice;
        }

        public Optional<CostPrice> getCostPrice() {
            return Optional.ofNullable(costPrice);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setSales(Money sales) {
            this.sales = sales;
        }

        public Optional<Money> getSales() {
            return Optional.ofNullable(sales);
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public Optional<UUID> getId() {
            return Optional.ofNullable(id);
        }

        public void setThreshold(QuantityThreshold threshold) {
            this.threshold = threshold;
        }

        public Optional<QuantityThreshold> getThreshold() {
            return Optional.ofNullable(threshold);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditProductDescriptor)) {
                return false;
            }

            // state check
            EditProductDescriptor e = (EditProductDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getCostPrice().equals(e.getCostPrice())
                    && getPrice().equals(e.getPrice())
                    && getQuantity().equals(e.getQuantity())
                    && getSales().equals(e.getSales())
                    && getId().equals(e.getId());
        }
    }
}
