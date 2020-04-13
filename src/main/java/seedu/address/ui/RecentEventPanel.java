package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.RecentEvent;

/**
 * Panel showing the last 5 events with the target person
 * in reverse chronological order of event addition.
 */
public class RecentEventPanel extends UiPart<Region> {
    private static final String FXML = "RecentEventPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecentEventPanel.class);

    @FXML
    private TableView<RecentEvent> eventTable;

    @FXML
    private TableColumn<RecentEvent, String> placeColumn;

    @FXML
    private TableColumn<RecentEvent, String> activityColumn;

    @FXML
    private TableColumn<RecentEvent, String> timeColumn;

    @FXML
    private TableColumn<RecentEvent, String> eventId;

    public RecentEventPanel(ObservableList<RecentEvent> list) {
        super(FXML);
        eventId.setCellValueFactory(cellData-> cellData.getValue().eventIdProperty());
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        activityColumn.setCellValueFactory(cellData -> cellData.getValue().activityProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        eventTable.setItems(list);
    }
}
