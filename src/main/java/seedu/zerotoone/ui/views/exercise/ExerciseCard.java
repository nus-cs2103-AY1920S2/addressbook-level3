package seedu.zerotoone.ui.views.exercise;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class ExerciseCard extends UiPart<Region> {
    private static final String FXML = "exercise/ExerciseCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExerciseList level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label exerciseId;
    @FXML
    private Label exerciseName;
    @FXML
    private VBox exerciseSets;

    public ExerciseCard(Exercise exercise, int displayedIndex) {
        super(FXML);
        exerciseId.setText(String.format("%d. ", displayedIndex));
        exerciseName.setText(exercise.getExerciseName().fullName);

        List<ExerciseSet> exerciseSetsList = exercise.getExerciseSets();
        for (int i = 0; i < exerciseSetsList.size(); i++) {
            ExerciseSet exerciseSet = exerciseSetsList.get(i);
            ExerciseSetCard exerciseSetCard =
                    new ExerciseSetCard(i, exerciseSet.getNumReps().value, exerciseSet.getWeight().value);
            this.exerciseSets.getChildren().add(exerciseSetCard.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseCard)) {
            return false;
        }

        // state check
        ExerciseCard card = (ExerciseCard) other;
        return exerciseId.getText().equals(card.exerciseId.getText())
                && exerciseName.getText().equals(card.exerciseName.getText());
    }
}
