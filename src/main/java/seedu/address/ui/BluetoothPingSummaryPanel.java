package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bluetooth.BluetoothPingsSummary;

import java.util.logging.Logger;

public class BluetoothPingSummaryPanel extends UiPart<Region> {
    private static final String FXML = "BluetoothPingPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BluetoothPingSummaryPanel.class);

    @FXML
    private ListView<BluetoothPingsSummary> bluetoothPingsListView;

    public BluetoothPingSummaryPanel(ObservableList<BluetoothPingsSummary> bluetoothPingSummaryList) {
        super(FXML);
        bluetoothPingsListView.setItems(bluetoothPingSummaryList);
        bluetoothPingsListView.setCellFactory(listView -> new BluetoothPingSummaryPanel.BluetoothPingsListViewCell());
    }

    class BluetoothPingsListViewCell extends ListCell<BluetoothPingsSummary> {
        @Override
        protected void updateItem(BluetoothPingsSummary bluetoothPingsSummary, boolean empty) {
            super.updateItem(bluetoothPingsSummary, empty);

            if (empty || bluetoothPingsSummary == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BluetoothPingSummaryCard(bluetoothPingsSummary, getIndex() +1).getRoot());
            }
        }
    }
}
