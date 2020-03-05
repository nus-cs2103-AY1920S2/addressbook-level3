package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of persons.
 */
public class MonthlyDataPanel extends UiPart<Region> {
    private static final String FXML = "MonthlyDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MonthlyDataPanel.class);

    @FXML
    private Label monthlyLabel;

    public MonthlyDataPanel() {
        super(FXML);
    }

}

