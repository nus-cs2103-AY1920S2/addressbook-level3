package team.easytravel.ui.scheduletab;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.ui.TabPanel;

/**
 * Panel containing the schedule.
 */
public class ScheduleTabPanel extends TabPanel {
    public static final String TAB_NAME = "Schedule Tab";

    private static final String FXML = "scheduletab/ScheduleTabPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleTabPanel.class);

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox scheduleView;

    public ScheduleTabPanel(List<ObservableList<DayScheduleEntry>> schedule) {
        super(FXML);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        schedule.forEach(daySchedule -> {
            VBox vBox = new VBox();
            vBox.setMinWidth(250);
            vBox.setPadding(new Insets(5, 10, 5, 0));
            ListView<DayScheduleEntry> listView = new ListView<>();
            listView.setItems(daySchedule);
            listView.setCellFactory(l -> new DayScheduleListViewCell());
            vBox.getChildren().add(listView);
            scheduleView.getChildren().add(vBox);
        });

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DayScheduleEntry} using a
     * {@code ScheduleEntryCard}.
     */
    class DayScheduleListViewCell extends ListCell<DayScheduleEntry> {
        @Override
        protected void updateItem(DayScheduleEntry scheduleEntry, boolean empty) {
            super.updateItem(scheduleEntry, empty);
            if (empty || scheduleEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleEntryCard(scheduleEntry, getIndex() + 1).getRoot());
            }
        }
    }
}
