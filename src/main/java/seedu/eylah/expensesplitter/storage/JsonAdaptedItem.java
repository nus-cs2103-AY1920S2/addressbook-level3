package seedu.eylah.expensesplitter.storage;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;
import seedu.eylah.expensesplitter.model.person.Amount;

/**
 * Jackson-friendly version of {@link Item}.
 */
public class JsonAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    private final String itemName;
    private final BigDecimal itemPrice;
    private final BigDecimal amountPerPerson;

    /**
     * Constructs a {@code JsonAdaptedItem} with the given item details.
     */
    @JsonCreator
    public JsonAdaptedItem(@JsonProperty("itemName") String itemName, @JsonProperty("itemPrice") BigDecimal itemPrice,
            @JsonProperty("amountPerPerson") BigDecimal amountPerPerson) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.amountPerPerson = amountPerPerson;
    }

    /**
     * Converts a given {@code Item} into this class for Jackson use.
     */
    public JsonAdaptedItem(Item source) {
        itemName = source.getItemName().getItemName();
        itemPrice = source.getItemPrice().getItemPrice();
        amountPerPerson = source.getAmountPerPerson().amount;
    }

    /**
     * Converts this Jackson-friendly adapted item object into the model's {@code Item} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item.
     */
    public Item toModelType() throws IllegalValueException {

        if (itemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidName(itemName)) {
            throw new IllegalValueException(ItemName.MESSAGE_CONSTRAINTS);
        }
        final ItemName modelItemName = new ItemName(itemName);

        if (itemPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemPrice.class.getSimpleName()));
        }
        if (!ItemPrice.isValidPrice(itemPrice)) {
            throw new IllegalValueException(ItemPrice.MESSAGE_CONSTRAINTS);
        }
        final ItemPrice modelItemPrice = new ItemPrice(itemPrice);

        if (amountPerPerson == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amountPerPerson)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmountPerPerson = new Amount(amountPerPerson);

        return new Item(modelItemName, modelItemPrice, modelAmountPerPerson);
    }
}
