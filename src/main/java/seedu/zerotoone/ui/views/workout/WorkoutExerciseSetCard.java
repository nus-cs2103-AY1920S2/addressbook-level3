package seedu.zerotoone.ui.views.workout;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays the Set information of an Exercise.
 */
public class WorkoutExerciseSetCard extends UiPart<Region> {
    private static final String FXML = "workout/WorkoutExerciseSetCard.fxml";

    @FXML
    private Label numReps;
    @FXML
    private Label weight;

    public WorkoutExerciseSetCard(NumReps numReps, Weight weight) {
        super(FXML);
        this.numReps.setText(String.format("- %s x ", numReps.value));
        this.weight.setText(String.format("%s kg", weight.value));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkoutExerciseSetCard)) {
            return false;
        }

        // state check
        WorkoutExerciseSetCard card = (WorkoutExerciseSetCard) other;
        return numReps.getText().equals(card.numReps.getText())
                && weight.getText().equals(card.weight.getText());
    }
}
