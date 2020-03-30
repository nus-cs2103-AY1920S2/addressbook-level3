package seedu.zerotoone.ui.views.workout;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
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
    // @FXML
    // private Label numReps;
    // @FXML
    // private Label weight;

    public WorkoutExerciseCard(int exerciseId, String exerciseName) {
        super(FXML);
        this.exerciseId.setText(String.format("Exercise %d. ", exerciseId + 1));
        this.exerciseName.setText(exerciseName);
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
