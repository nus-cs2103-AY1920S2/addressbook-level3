package seedu.zerotoone.ui.views.log;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Session}.
 */
public class SessionSetCard extends UiPart<Region> {

    private static final String FXML = "log/SessionSetCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on SessionList level 4</a>
     */

    @FXML
    private Label setId;
    @FXML
    private Label numReps;
    @FXML
    private Label weight;
    @FXML
    private Label isFinished;

    public SessionSetCard(int setId, String numReps, String weight, Boolean isFinished) {
        super(FXML);
        this.setId.setText(String.format("Set %d. ", setId + 1));
        this.numReps.setText(numReps);
        this.weight.setText(String.format("%skg", weight));
        this.isFinished.setText(isFinished ? "FINISHED" : "UNFINISHED");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SessionSetCard)) {
            return false;
        }

        // state check
        SessionSetCard card = (SessionSetCard) other;
        return setId.getText().equals(card.setId.getText())
            && numReps.getText().equals(card.numReps.getText())
            && weight.getText().equals(card.weight.getText())
            && isFinished.getText().equals(card.isFinished.getText());
    }
}
