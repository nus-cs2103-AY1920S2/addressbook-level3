package team.easytravel.model.listmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.util.uniquelist.UniqueList;

/**
 * The type Packing list manager.
 *
 * @author loycatherine
 */
public class PackingListManager implements ReadOnlyPackingListManager {
    private final UniqueList<PackingListItem> uniquePackingList;

    /**
     * Instantiates a new PackingListManager.
     */
    public PackingListManager() {
        uniquePackingList = new UniqueList<>();
    }

    /**
     * Creates an PackingListManager using the Items in the {@code toBeCopied}
     *
     * @param toBeCopied the to be copied
     */
    public PackingListManager(ReadOnlyPackingListManager toBeCopied) {
        uniquePackingList = new UniqueList<>();
        resetData(toBeCopied);
    }

    // List overwrite operations

    /**
     * Replaces the contents of the packing list with {@code packingListItems}.
     * {@code packingListItems} must not contain duplicate persons.
     *
     * @param packingListItems the packing list items
     */
    public void setPackingListItems(List<PackingListItem> packingListItems) {
        this.uniquePackingList.setElements(packingListItems);
    }

    /**
     * Resets the existing data of this {@code PackingManager} with {@code newData}.
     *
     * @param newData the new data
     */
    public void resetData(ReadOnlyPackingListManager newData) {
        requireNonNull(newData);
        setPackingListItems(newData.getUniquePackingList());
    }

    // PackingListItem-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the address book.
     *
     * @param packingListItem the packing list item
     * @return the boolean
     */
    public boolean hasPackingListItem(PackingListItem packingListItem) {
        requireNonNull(packingListItem);
        return uniquePackingList.contains(packingListItem);
    }

    /**
     * Adds a packing list item to the PackingListManager.
     * The packing list item must not already exist in the PackingListManager.
     *
     * @param packingListItem the packing list item
     */
    public void addPackingListItem(PackingListItem packingListItem) {
        uniquePackingList.add(packingListItem);
    }

    /**
     * Returns the status of the packing list preparation.
     */
    public String getStatus() {
        int numOfItems = uniquePackingList.size();
        if (numOfItems == 0) {
            return "[❗] Packed items: " + "0/0";
        }
        long count = uniquePackingList.stream().filter(PackingListItem::getIsChecked).count();
        if (count == numOfItems) {
            return "[✔] Packed items: " + count + "/" + numOfItems;
        } else {
            return "[❌] Packed items: " + count + "/" + numOfItems;
        }
    }

    /**
     * Replaces the given packing list item {@code target} in the list with {@code editedPackingListItem}.
     * {@code target} must exist in the PackingListManager.
     * The packing list item identity of {@code editedPackingListItem} must not be the same as another existing
     * packing list item in the PackingListManager.
     *
     * @param target                the target
     * @param editedPackingListItem the edited packing list item
     */
    public void setPackingListItem(PackingListItem target, PackingListItem editedPackingListItem) {
        requireNonNull(editedPackingListItem);

        uniquePackingList.setElement(target, editedPackingListItem);
    }

    /**
     * Removes {@code key} from this {@code PackingListManager}.
     * {@code key} must exist in the PackingListManager.
     *
     * @param key the key
     */
    public void removePackingListItem(PackingListItem key) {
        uniquePackingList.remove(key);
    }


    /**
     * Sort packing list observable list.
     *
     * @param cmp the cmp
     * @return the observable list
     */
    public ObservableList<PackingListItem> sortPackingList(Comparator<PackingListItem> cmp) {
        uniquePackingList.sort(cmp);
        return uniquePackingList.asUnmodifiableObservableList();
    }

    // Util methods

    @Override
    public String toString() {
        return uniquePackingList.asUnmodifiableObservableList().size() + " packing list items";
    }

    @Override
    public ObservableList<PackingListItem> getUniquePackingList() {
        return uniquePackingList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PackingListManager // instanceof handles nulls
                && uniquePackingList.equals(((PackingListManager) other).uniquePackingList));
    }

    @Override
    public int hashCode() {
        return uniquePackingList.hashCode();
    }
}
