package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

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

