package seedu.zerotoone.ui.views.workout;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Workout}.
 */
public class WorkoutExerciseCard extends UiPart<Region> {

    private static final String FXML = "workout/WorkoutExerciseCard.fxml";

    @FXML
    private Label exerciseId;

    @FXML
    private Label exerciseName;

    @FXML
    private VBox exerciseSets;

    public WorkoutExerciseCard(int exerciseId, Exercise exercise) {
        super(FXML);
        this.exerciseId.setText(String.format("%d. ", exerciseId + 1));
        this.exerciseName.setText(exercise.getExerciseName().fullName);

        List<ExerciseSet> exerciseSets = exercise.getExerciseSets();
        for (ExerciseSet exerciseSet : exerciseSets) {
            WorkoutExerciseSetCard workoutExerciseSetCard =
                    new WorkoutExerciseSetCard(exerciseSet.getNumReps(), exerciseSet.getWeight());
            this.exerciseSets.getChildren().add(workoutExerciseSetCard.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkoutExerciseCard)) {
            return false;
        }

        // state check
        WorkoutExerciseCard card = (WorkoutExerciseCard) other;
        return exerciseId.getText().equals(card.exerciseId.getText())
                && exerciseName.getText().equals(card.exerciseName.getText());
    }
}
