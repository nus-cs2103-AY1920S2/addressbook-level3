package team.easytravel.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Panel containing the schedule.
 */
public class NoTripTabPanel extends TabPanel {
    private static final String FXML = "NoTripTabPanel.fxml";

    @FXML
    private AnchorPane panel;

    public NoTripTabPanel() {
        super(FXML);
    }
}
