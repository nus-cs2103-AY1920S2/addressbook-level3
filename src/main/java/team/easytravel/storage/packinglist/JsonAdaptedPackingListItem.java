package team.easytravel.storage.packinglist;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;

/**
 * Jackson-friendly version of {@link PackingListItem}.
 */
public class JsonAdaptedPackingListItem {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    private final String name;
    private final Integer quantity;
    private final String category;
    private final boolean isCheck;

    /**
     * Constructs a {@code JsonAdaptedPackingListItem} with the given details.
     */
    @JsonCreator
    public JsonAdaptedPackingListItem(@JsonProperty("name") String name,
                                      @JsonProperty("quantity") Integer quantity,
                                      @JsonProperty("category") String category,
                                      @JsonProperty("isCheck") boolean isCheck) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.isCheck = isCheck;
    }

    /**
     * Converts a given {@code PackingListItem} into this class for Jackson use.
     */
    public JsonAdaptedPackingListItem(PackingListItem source) {
        name = source.getItemName().value;
        quantity = source.getQuantity().value;
        category = source.getItemCategory().value;
        isCheck = source.getIsChecked();
    }

    /**
     * Converts this Jackson-friendly adapted packing list item object into the model's {@code PackingListItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted packing list item.
     */
    public PackingListItem toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidName(name)) {
            throw new IllegalValueException(ItemName.MESSAGE_CONSTRAINTS);
        }
        final ItemName modelItemName = new ItemName(name);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemCategory.class.getSimpleName()));
        }
        if (!ItemCategory.isValidItemCategory(category)) {
            throw new IllegalValueException(ItemCategory.MESSAGE_CONSTRAINTS);
        }
        final ItemCategory modelCategory = new ItemCategory(category);

        return new PackingListItem(modelItemName, modelQuantity, modelCategory, isCheck);
    }
}
