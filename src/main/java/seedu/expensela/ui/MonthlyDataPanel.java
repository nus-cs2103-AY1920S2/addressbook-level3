package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.expensela.commons.core.LogsCenter;

/**
 * Panel containing the list of persons.
 */
public class MonthlyDataPanel extends UiPart<Region> {
    private static final String FXML = "MonthlyDataPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MonthlyDataPanel.class);

    @FXML
    private Label expenditureLabel;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label incomeLabel;

    @FXML
    private Label balanceLabel;

    public MonthlyDataPanel() {
        super(FXML);
    }

}

