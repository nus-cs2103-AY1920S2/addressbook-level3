package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.bluetooth.BluetoothPings;

public class BluetoothPingCard extends UiPart<Region> {

    private static final String FXML = "BluetoothPingCard.fxml";

    public final BluetoothPings bluetoothPings;

    @FXML
    private Label contacts;

    @FXML
    private Label timestamp;

    public BluetoothPingCard(BluetoothPings bluetoothPings, int displayedIndex) {
        super(FXML);
        this.bluetoothPings = bluetoothPings;
        contacts.setText(bluetoothPings.getUserIDs().toString());
        timestamp.setText(bluetoothPings.getEpochTs().toString());
    }

    @Override
    public boolean equals(Object other) {return true;}
}
