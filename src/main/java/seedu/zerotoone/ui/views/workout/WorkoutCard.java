package seedu.zerotoone.ui.views.workout;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Workout}.
 */
public class WorkoutCard extends UiPart<Region> {
    private static final String FXML = "workout/WorkoutCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label workoutId;
    @FXML
    private Label workoutName;
    @FXML
    private VBox workoutExercises;

    public WorkoutCard(Workout workout, int displayedIndex) {
        super(FXML);
        workoutId.setText(String.format("%d. ", displayedIndex));
        workoutName.setText(workout.getWorkoutName().fullName);

        List<Exercise> workoutExercisesList = workout.getWorkoutExercises();
        for (int i = 0; i < workoutExercisesList.size(); i++) {
            Exercise workoutExercise = workoutExercisesList.get(i);
            WorkoutExerciseCard workoutExerciseCard =
                    new WorkoutExerciseCard(i, workoutExercise.getExerciseName().fullName);
            this.workoutExercises.getChildren().add(workoutExerciseCard.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkoutCard)) {
            return false;
        }

        // state check
        WorkoutCard card = (WorkoutCard) other;
        return workoutId.getText().equals(card.workoutId.getText())
                && workoutName.getText().equals(card.workoutName.getText());
    }
}
