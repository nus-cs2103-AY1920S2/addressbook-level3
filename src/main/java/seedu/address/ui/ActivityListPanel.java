package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.EventDescriptor;

/**
 * Panel showing the list of activities,
 * and their frequencies in descending order.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ActivityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private TableView<EventDescriptor> frequencyTable;

    @FXML
    private TableColumn<EventDescriptor, String> activityColumn;

    @FXML
    private TableColumn<EventDescriptor, String> frequencyColumn;

    public ActivityListPanel(ObservableList<EventDescriptor> frequencyList) {
        super(FXML);
        activityColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty());
        frequencyTable.setItems(frequencyList);
    }
}
