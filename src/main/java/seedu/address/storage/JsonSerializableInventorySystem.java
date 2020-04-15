package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.InventorySystem;
import seedu.address.model.ReadOnlyInventorySystem;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.storage.customer.JsonAdaptedCustomer;
import seedu.address.storage.product.JsonAdaptedProduct;
import seedu.address.storage.transaction.JsonAdaptedTransaction;

/**
 * An Immutable InventorySystem that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableInventorySystem {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate customer(s).";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "Products list contains duplicate product(s).";

    private final List<JsonAdaptedCustomer> persons = new ArrayList<>();
    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();
    private final List<JsonAdaptedProduct> products = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInventorySystem} with the given persons, transactions and products.
     */
    @JsonCreator
    public JsonSerializableInventorySystem(@JsonProperty("persons") List<JsonAdaptedCustomer> persons,
                                           @JsonProperty("transactions") List<JsonAdaptedTransaction> transactions,
                                           @JsonProperty("products") List<JsonAdaptedProduct> products) {
        this.persons.addAll(persons);
        this.transactions.addAll(transactions);
        this.products.addAll(products);
    }

    /**
     * Converts a given {@code ReadOnlyInventorySystem} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventorySystem}.
     */
    public JsonSerializableInventorySystem(ReadOnlyInventorySystem source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
        transactions.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
        products.addAll(source.getProductList().stream().map(JsonAdaptedProduct::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code InventorySystem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public InventorySystem toModelType() throws IllegalValueException {
        InventorySystem addressBook = new InventorySystem();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : persons) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (addressBook.hasPerson(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(customer);
        }

        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            if (addressBook.hasTransaction(transaction)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            addressBook.addTransaction(transaction);
        }

        for (JsonAdaptedProduct jsonAdaptedProduct : products) {
            Product product = jsonAdaptedProduct.toModelType();
            if (addressBook.hasProduct(product)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PRODUCT);
            }
            addressBook.addProduct(product);
        }

        return addressBook;
    }

}
