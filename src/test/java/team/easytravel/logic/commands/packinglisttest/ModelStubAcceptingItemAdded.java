package team.easytravel.logic.commands.packinglisttest;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import team.easytravel.model.ModelStub;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;

/**
 * The type Model stub accepting item added.
 */
public class ModelStubAcceptingItemAdded extends ModelStub {
    /**
     * The Packing list items added.
     */
    final ArrayList<PackingListItem> packingListItemsAdded = new ArrayList<>();

    @Override
    public boolean hasTrip() {
        return true;
    }

    @Override
    public boolean hasPackingListItem(PackingListItem packingListItem) {
        requireNonNull(packingListItem);
        return packingListItemsAdded.stream().anyMatch(packingListItem::isSame);
    }

    @Override
    public void addPackingListItem(PackingListItem packingListItem) {
        requireNonNull(packingListItem);
        packingListItemsAdded.add(packingListItem);
    }

    @Override
    public ReadOnlyPackingListManager getPackingListManager() {
        return new PackingListManager();
    }
}
