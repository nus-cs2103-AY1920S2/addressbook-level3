package seedu.zerotoone.ui.views.workout;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.ui.util.UiPart;

import seedu.zerotoone.MainApp;
import seedu.zerotoone.commons.core.LogsCenter;
import java.util.logging.Logger;

/**
 * Panel containing the workout page.
 */
public class WorkoutListPanel extends UiPart<Region> {
    private static final String FXML = "workout/WorkoutListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MainApp.class);


    @FXML
    private ListView<Workout> workoutListView;

    public WorkoutListPanel(ObservableList<Workout> workoutList) {
        super(FXML);
        workoutListView.setItems(workoutList);
        workoutListView.setCellFactory(listView -> new WorkoutListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class WorkoutListViewCell extends ListCell<Workout> {
        @Override
        protected void updateItem(Workout workout, boolean empty) {
            super.updateItem(workout, empty);

            if (empty || workout == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkoutCard(workout, getIndex() + 1).getRoot());
            }
        }
    }
}
