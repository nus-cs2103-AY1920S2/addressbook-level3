package team.easytravel.ui.packinglisttab;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.ui.TabPanel;

/**
 * Panel containing the list of items.
 */
public class PackingListTabPanel extends TabPanel {
    public static final String TAB_NAME = "Packing List Tab";

    private static final String FXML = "packinglisttab/PackingListTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PackingListTabPanel.class);

    @FXML
    private ListView<PackingListItem> packingListView;

    public PackingListTabPanel(ObservableList<PackingListItem> packingList) {
        super(FXML);
        packingListView.setItems(packingList);
        packingListView.setCellFactory(listView -> new PackingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code PackingListCard}.
     */
    class PackingListViewCell extends ListCell<PackingListItem> {
        @Override
        protected void updateItem(PackingListItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PackingListCard(item, getIndex() + 1).getRoot());
            }
        }
    }

}
