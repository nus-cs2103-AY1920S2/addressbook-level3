package seedu.address.ui.academics;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.academics.Assessment;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays minimal information of a {@code Assessment}.
 */
public class AssessmentCardHomework extends UiPart<Region> {

    private static final String FXML = "AssessmentCardHomework.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Assessment assessment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label date;
    @FXML
    private Label submissionTracker;

    /**
     * Constructor to create the assessment card controller.
     */
    public AssessmentCardHomework(Assessment assessment, int displayedIndex) {
        super(FXML);
        this.assessment = assessment;
        id.setText(displayedIndex + ". ");
        description.setText(assessment.getDescription());
        tags.getChildren().add(new Label("Homework"));
        if (assessment.getSubmissionTracker().size() == assessment.noOfSubmittedStudents()) {
            tags.getChildren().add(new Label("All Submitted"));
        }
        if (assessment.getSubmissionTracker().size() == assessment.noOfMarkedSubmissions()) {
            tags.getChildren().add(new Label("Completed Marking"));
        }
        date.setText("Deadline: "
                + assessment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        submissionTracker.setText("Submissions: " + assessment.noOfSubmittedStudents()
                + " / " + assessment.getSubmissionTracker().size());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssessmentCardHomework)) {
            return false;
        }

        // state check
        AssessmentCardHomework card = (AssessmentCardHomework) other;
        return id.getText().equals(card.id.getText())
                && assessment.equals(card.assessment);
    }
}
