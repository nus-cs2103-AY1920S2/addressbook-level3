package seedu.zerotoone.ui.views.log;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.session.SessionSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Session}.
 */
public class SessionCard extends UiPart<Region> {
    private static final String FXML = "log/SessionCard.fxml";

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
    private Label sessionId;
    @FXML
    private Label exerciseName;
    @FXML
    private VBox sessionSets;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;

    public SessionCard(Session session, int displayedIndex) {
        super(FXML);
        sessionId.setText(String.format("%d. ", displayedIndex));
        exerciseName.setText(session.getExerciseName().fullName);

        List<SessionSet> exerciseSetsList = session.getSets();
        for (int i = 0; i < exerciseSetsList.size(); i++) {
            SessionSet sessionSet = exerciseSetsList.get(i);
            SessionSetCard sessionSetCard =
                new SessionSetCard(i, sessionSet.getNumReps().value, sessionSet.getWeight().value,
                    sessionSet.isFinished());
            this.sessionSets.getChildren().add(sessionSetCard.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.zerotoone.ui.views.log.SessionCard)) {
            return false;
        }

        // state check
        seedu.zerotoone.ui.views.log.SessionCard card = (seedu.zerotoone.ui.views.log.SessionCard) other;
        return sessionId.getText().equals(card.sessionId.getText())
            && exerciseName.getText().equals(card.exerciseName.getText());
    }
}
