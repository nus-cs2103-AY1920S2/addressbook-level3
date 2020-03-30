package seedu.zerotoone.ui.views.schedule;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.ui.util.UiPart;

/**
 */
public class ScheduledWorkoutListPanel extends UiPart<Region> {
    private static final String FXML = "schedule/ScheduledWorkoutListPanel.fxml";

    @FXML
    private ListView<ScheduledWorkout> scheduledWorkoutListView;

    public ScheduledWorkoutListPanel(ObservableList<ScheduledWorkout> scheduledWorkouts) {
        super(FXML);
        scheduledWorkoutListView.setItems(scheduledWorkouts);
        scheduledWorkoutListView.setCellFactory(listView -> new ScheduledWorkoutListViewCell());
    }

    /**
     */
    class ScheduledWorkoutListViewCell extends ListCell<ScheduledWorkout> {
        @Override
        protected void updateItem(ScheduledWorkout scheduledWorkout, boolean empty) {
            super.updateItem(scheduledWorkout, empty);

            if (empty || scheduledWorkout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduledWorkoutCard(scheduledWorkout, getIndex() + 1).getRoot());
            }
        }
    }

}
