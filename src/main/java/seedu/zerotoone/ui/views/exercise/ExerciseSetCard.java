package seedu.zerotoone.ui.views.exercise;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class ExerciseSetCard extends UiPart<Region> {

    private static final String FXML = "exercise/ExerciseSetCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExerciseList level 4</a>
     */

    @FXML
    private Label setId;
    @FXML
    private Label numReps;
    @FXML
    private Label weight;

    public ExerciseSetCard(int setId, String numReps, String weight) {
        super(FXML);
        this.setId.setText(String.format("Set %d. ", setId + 1));
        this.numReps.setText(numReps);
        this.weight.setText(String.format("%skg", weight));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExerciseSetCard)) {
            return false;
        }

        // state check
        ExerciseSetCard card = (ExerciseSetCard) other;
        return setId.getText().equals(card.setId.getText())
                && numReps.getText().equals(card.numReps.getText())
                && weight.getText().equals(card.weight.getText());
    }
}
