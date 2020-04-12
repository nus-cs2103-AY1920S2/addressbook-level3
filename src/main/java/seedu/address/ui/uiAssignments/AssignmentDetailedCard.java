package seedu.address.ui.uiAssignments;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class AssignmentDetailedCard extends UiPart<Region> {

    private static final String FXML = "AssignmentListDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     * level 4</a>
     */

    public final Assignment assignment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label assignmentID;
    @FXML
    private Label assignedCourseID;
    @FXML
    private Label name;
    @FXML
    private Label assignmentDeadline;
    @FXML
    private FlowPane tags;

    private CommandBox commandBox;

    public AssignmentDetailedCard(Assignment assignment, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.assignment = assignment;
        this.commandBox = commandBox;
        id.setText(displayedIndex + ". ");
        assignmentID.setText("Assignment ID: " + assignment.getId().toString());
        if (assignment.isAssignedToCourse()) {
            assignedCourseID.setText("Assigned To CourseID: " + assignment.getAssignedCourseID().toString());
        } else {
            assignedCourseID.setText("Assigned To CourseID: None");
        }
        name.setText(assignment.getName().fullName);
        assignmentDeadline.setText("Deadline: " + assignment.getDeadline().toString());

        assignment.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignmentDetailedCard)) {
            return false;
        }

        // state check
        AssignmentDetailedCard card = (AssignmentDetailedCard) other;
        return id.getText().equals(card.id.getText())
                && assignment.equals(card.assignment);
    }

}
