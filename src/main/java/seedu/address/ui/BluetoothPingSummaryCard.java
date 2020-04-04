package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.bluetooth.BluetoothPingsSummary;

public class BluetoothPingSummaryCard extends UiPart<Region> {

    private static final String FXML="BluetoothPingSummaryCard.fxml";

    public final BluetoothPingsSummary bluetoothPings;

    @FXML
    private Label contacts;

    @FXML
    private Label occurrence;

    public BluetoothPingSummaryCard(BluetoothPingsSummary bluetoothPingSummary,int displayedIndex){
        super(FXML);
        this.bluetoothPings = bluetoothPingSummary;
        contacts.setText(bluetoothPingSummary.getUserIDs().toString());
        occurrence.setText(bluetoothPingSummary.getCounts().toString());
    }

    @Override
    public boolean equals(Object other){return true;}
}
