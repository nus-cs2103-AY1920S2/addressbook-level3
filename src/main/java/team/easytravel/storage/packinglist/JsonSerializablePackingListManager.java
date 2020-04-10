package team.easytravel.storage.packinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;

/**
 * The type Json serializable packing list manager.
 */
@JsonRootName(value = "packingListManager")
public class JsonSerializablePackingListManager {
    public static final String MESSAGE_DUPLICATE_ITEM = "Packing list contains duplicate items.";

    private final List<JsonAdaptedPackingListItem> packingList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePackingListManager} with the given fixed expenses.
     */
    @JsonCreator
    public JsonSerializablePackingListManager(
            @JsonProperty("packingList") List<JsonAdaptedPackingListItem> packingList) {
        this.packingList.addAll(packingList);
    }

    /**
     * Converts a given {@code ReadOnlyPackingListManager} into this class for Jackson use.
     */
    public JsonSerializablePackingListManager(ReadOnlyPackingListManager source) {
        packingList.addAll(
                source.getUniquePackingList()
                        .stream()
                        .map(JsonAdaptedPackingListItem::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts this JsonSerializablePackingListManager into the model's {@code PackingListManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PackingListManager toModelType() throws IllegalValueException {
        PackingListManager packingListManager = new PackingListManager();
        for (JsonAdaptedPackingListItem jsonAdaptedPackingListItem : packingList) {
            PackingListItem item = jsonAdaptedPackingListItem.toModelType();
            if (packingListManager.hasPackingListItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            packingListManager.addPackingListItem(item);
        }
        return packingListManager;
    }
}
