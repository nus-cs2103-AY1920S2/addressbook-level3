package team.easytravel.model.listmanagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.util.uniquelist.exceptions.DuplicateElementException;
import team.easytravel.testutil.Assert;
import team.easytravel.testutil.TypicalPackingListItem;

class PackingListManagerTest {

    private final PackingListManager packingListManager = new PackingListManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), packingListManager.getUniquePackingList());
    }

    @Test
    public void resetDataNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> packingListManager.resetData(null));
    }

    @Test
    public void resetDataWithValidReadOnlyPackingListManagerReplacesData() {
        PackingListManager newData = TypicalPackingListItem.getTypicalPackingListManager();
        packingListManager.resetData(newData);
        assertEquals(newData, packingListManager);
    }

    @Test
    public void resetDataWithDuplicatePackingListItemThrowsDuplicateElementException() {
        List<PackingListItem> newPackingListItem = Arrays.asList(TypicalPackingListItem.PACKING_LIST_JEANS,
                TypicalPackingListItem.PACKING_LIST_JEANS);
        PackingListManagerStub newData = new PackingListManagerStub(newPackingListItem);
        Assert.assertThrows(DuplicateElementException.class, () -> packingListManager.resetData(newData));
    }

    @Test
    public void hasPackingListItemNullPackingListItemThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> packingListManager
                .hasPackingListItem(null));
    }

    @Test
    public void hasPackingListItemNotInPackingListManagerReturnsFalse() {
        assertFalse(packingListManager.hasPackingListItem(TypicalPackingListItem.PACKING_LIST_JEANS));
    }

    @Test
    public void hasPackingListItemInPackingListManagerReturnsTrue() {
        packingListManager.addPackingListItem(TypicalPackingListItem.PACKING_LIST_SHIRT);
        assertTrue(packingListManager.hasPackingListItem(TypicalPackingListItem.PACKING_LIST_SHIRT));
    }

    @Test
    public void getPackingListItemModifyListThrowsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> packingListManager
                .getUniquePackingList().remove(0));
    }

    /**
     * A stub ReadOnlyPackingListManager whose Packing list can violate interface components.
     */

    private static class PackingListManagerStub implements ReadOnlyPackingListManager {
        private final ObservableList<PackingListItem> packingListItems =
                FXCollections.observableArrayList();

        PackingListManagerStub(Collection<PackingListItem> packingListItems) {
            this.packingListItems.setAll(packingListItems);
        }

        @Override
        public ObservableList<PackingListItem> getUniquePackingList() {
            return packingListItems;
        }
    }
}
