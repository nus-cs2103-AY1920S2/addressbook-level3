package seedu.zerotoone.ui.views.log;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.zerotoone.commons.util.DateUtil;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code CompletedWorkout}.
 */
public class CompletedWorkoutCard extends UiPart<Region> {
    private static final String FXML = "log/CompletedWorkoutCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExerciseList level 4</a>
     */

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label workoutId;
    @FXML
    private Label workoutName;
    @FXML
    private ListView<CompletedExercise> exerciseListView;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;

    public CompletedWorkoutCard(CompletedWorkout completedWorkout, int displayedIndex) {
        super(FXML);
        workoutId.setText(String.format("WORKOUT %d", displayedIndex));
        workoutName.setText("Workout Name: '" + completedWorkout.getWorkoutName().fullName + "'");
        startTime.setText(DateUtil.getPrettyDateTimeString(completedWorkout.getStartTime()));
        endTime.setText(DateUtil.getPrettyDateTimeString(completedWorkout.getEndTime()));


        List<CompletedExercise> exerciseList = completedWorkout.getExercises();

        exerciseListView.setItems(FXCollections.observableArrayList(exerciseList));
        exerciseListView.setCellFactory(listView -> new CompletedExerciseViewCell());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompletedWorkoutCard)) {
            return false;
        }

        // state check
        CompletedWorkoutCard card = (CompletedWorkoutCard) other;
        return workoutId.getText().equals(card.workoutName.getText())
            && workoutName.getText().equals(card.workoutName.getText());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code SessionCard}.
     */
    class CompletedExerciseViewCell extends ListCell<CompletedExercise> {
        @Override
        protected void updateItem(CompletedExercise completedExercise, boolean empty) {
            super.updateItem(completedExercise, empty);

            if (empty || completedExercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CompletedExerciseCard(completedExercise, getIndex() + 1).getRoot());
            }
        }
    }
}
