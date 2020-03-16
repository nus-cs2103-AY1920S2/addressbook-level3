package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class TimeWindowPanel extends UiPart<Region> {
    private static final String FXML = "TimeWindowPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TimeWindowPanel.class);

    @javafx.fxml.FXML
    private ListView<BluetoothPings> bluetoothPingsListView;


}
