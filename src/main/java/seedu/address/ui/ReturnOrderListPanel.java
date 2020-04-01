package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * Panel containing the list of persons.
 */
public class ReturnOrderListPanel extends UiPart<Region> {
    private static final String FXML = "ReturnOrderListPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(ReturnOrderListPanel.class);

    @FXML
    private ListView<ReturnOrder> returnOrderListView;

    public ReturnOrderListPanel(ObservableList<ReturnOrder> returnOrderList) {
        super(FXML);
        returnOrderListView.setItems(returnOrderList);
        returnOrderListView.setCellFactory(listView -> new ReturnOrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code OrderCard}.
     */
    class ReturnOrderListViewCell extends ListCell<ReturnOrder> {
        @Override
        protected void updateItem(ReturnOrder returnOrder, boolean empty) {
            super.updateItem(returnOrder, empty);

            if (empty || returnOrder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReturnOrderCard(returnOrder, getIndex() + 1).getRoot());
            }
        }
    }

}
