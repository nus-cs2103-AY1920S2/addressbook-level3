package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.day.Day;

/**
 * Panel containing the list of days.
 */
public class ScheduleVisualPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleVisualPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleVisualPanel.class);

    @javafx.fxml.FXML
    private ListView<Day> scheduleListView;

    @FXML
    private Label title;

    public ScheduleVisualPanel(ObservableList<Day> scheduleVisual) {
        super(FXML);
        title.setText("Schedule for the next " + scheduleVisual.size() + " days:");
        scheduleListView.setItems(scheduleVisual);
        scheduleListView.setCellFactory(listView -> new ScheduleVisualPanel.ScheduleViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ScheduleViewCell extends ListCell<Day> {
        @Override
        protected void updateItem(Day day, boolean empty) {
            super.updateItem(day, empty);

            if (empty || day == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleDayCard(day, getIndex()).getRoot());
            }
        }
    }
}
