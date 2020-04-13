package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Status;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AssignmentCard extends UiPart<Region> {

    private static final String FXML = "AssignmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Assignment assignment;

    @FXML
    private HBox assignmentCardPane;
    @FXML
    private Label deadline;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private Label title;
    @FXML
    private Label workload;

    public AssignmentCard(Assignment assignment, int displayedIndex) {
        super(FXML);
        this.assignment = assignment;
        id.setText(displayedIndex + ". ");
        deadline.setText(assignment.getDeadline().toString());
        title.setText(assignment.getTitle().title);
        title.setWrapText(true);
        status.setText(" " + assignment.getStatus().status + " ");

        status.setStyle("-fx-text-fill: #000");
        if (assignment.getStatus().status.equals(Status.ASSIGNMENT_OUTSTANDING)) {
            status.setBackground(new Background(new BackgroundFill(Color.rgb(255, 87, 51),
                CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            status.setBackground(new Background(new BackgroundFill(Color.rgb(218, 247, 166),
                CornerRadii.EMPTY, Insets.EMPTY)));
        }

        workload.setText("Estimated workload: " + assignment.getWorkload().estHours + " hours");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        AssignmentCard card = (AssignmentCard) other;
        return id.getText().equals(card.id.getText())
                && assignment.equals(card.assignment);
    }

}
