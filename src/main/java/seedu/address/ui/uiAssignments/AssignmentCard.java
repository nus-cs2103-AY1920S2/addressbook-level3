package seedu.address.ui.uiAssignments;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class AssignmentCard extends UiPart<Region> {

    private static final String FXML = "AssignmentListCard.fxml";

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
    private Label name;


    private CommandBox commandBox;

    public AssignmentCard(Assignment assignment, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.assignment = assignment;
        this.commandBox = commandBox;
        id.setText(displayedIndex + ". ");
        assignmentID.setText(assignment.getId().toString());
        name.setText(assignment.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignmentCard)) {
            return false;
        }

        // state check
        AssignmentCard card = (AssignmentCard) other;
        return id.getText().equals(card.id.getText())
                && assignment.equals(card.assignment);
    }

    @FXML
    private void selectAssignment() {
        String selectedAssignmentID = assignmentID.getText();
        String commandText = "select aid/" + selectedAssignmentID;
        commandBox.runCommand(commandText, "ASSIGNMENT");
    }

}
