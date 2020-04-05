package seedu.address.ui.academics;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.academics.Submission;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays minimal information of a {@code Assessment}.
 */
public class SubmissionCard extends UiPart<Region> {

    private static final String FXML = "SubmissionCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Submission submission;

    @FXML
    private HBox submissionPane;
    @FXML
    private Label studentName;
    @FXML
    private Label submitted;
    @FXML
    private Label marked;
    @FXML
    private Label score;

    /**
     * Constructor to create the assessment card controller.
     */
    public SubmissionCard(Submission submission) {
        super(FXML);
        this.submission = submission;
        studentName.setText("   " + submission.getStudentName());
        submitted.setText(submission.hasSubmitted() ? "Submitted" : "Not Submitted");
        marked.setText(submission.isMarked() ? "Marked" : "Not Marked");
        score.setText(submission.getScore() + " / 100");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssessmentCard)) {
            return false;
        }

        // state check
        SubmissionCard card = (SubmissionCard) other;
        return studentName.equals(((SubmissionCard) other).studentName)
                && submission.equals(card.submission);
    }
}
