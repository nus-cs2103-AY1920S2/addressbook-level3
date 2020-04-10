package team.easytravel.logic.commands.packinglisttest;

import static java.util.Objects.requireNonNull;

import team.easytravel.model.ModelStub;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;

/**
 * The type Model stub with item.
 */
public class ModelStubWithItem extends ModelStub {
    private final PackingListItem packingListItem;

    /**
     * Instantiates a new Model stub with item.
     *
     * @param packingListItem the packing list item
     */
    public ModelStubWithItem(PackingListItem packingListItem) {
        requireNonNull(packingListItem);
        this.packingListItem = packingListItem;
    }

    @Override
    public boolean hasTrip() {
        return true;
    }

    @Override
    public boolean hasPackingListItem(PackingListItem packingListItem) {
        requireNonNull(packingListItem);
        return this.packingListItem.isSame(packingListItem);
    }
}
