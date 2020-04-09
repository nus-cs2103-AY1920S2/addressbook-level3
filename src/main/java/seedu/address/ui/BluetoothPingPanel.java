package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bluetooth.BluetoothPings;

public class BluetoothPingPanel extends UiPart<Region> {
    private static final String FXML = "BluetoothPingPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BluetoothPingPanel.class);

    @FXML
    private ListView<BluetoothPings> bluetoothPingsListView;

    public BluetoothPingPanel(ObservableList<BluetoothPings> bluetoothPingsList) {
        super(FXML);
        bluetoothPingsListView.setItems(bluetoothPingsList);
        bluetoothPingsListView.setCellFactory(listView -> new BluetoothPingsListViewCell());
    }

    class BluetoothPingsListViewCell extends ListCell<BluetoothPings> {
        @Override
        protected void updateItem(BluetoothPings bluetoothPings, boolean empty) {
            super.updateItem(bluetoothPings, empty);

            if (empty || bluetoothPings == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BluetoothPingCard(bluetoothPings, getIndex() +1).getRoot());
            }
        }
    }
}
