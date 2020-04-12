package seedu.address.ui.uiCourse;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.Comparator;
import java.util.Set;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class CourseDetailedCard extends UiPart<Region> {

    private static final String FXML = "CourseListDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     * level 4</a>
     */

    public final Course course;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label courseID;
    @FXML
    private Label assignedStaff;
    @FXML
    private Label assignedStudents;
    @FXML
    private Label assignedAssignments;
    @FXML
    private Label amount;
    @FXML
    private FlowPane tags;

    private CommandBox commandBox;

    public CourseDetailedCard(Course course, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.course = course;
        this.commandBox = commandBox;
        name.setText(course.getName().fullName);
        id.setText(displayedIndex + ". ");
        courseID.setText(course.getId().value);
        amount.setText(course.getAmount().value);

        Set<ID> assignmentIDS = course.getAssignedAssignmentsID();
        String assignmentsStrings = "None";
        if (assignmentIDS.size() > 0) {
            assignmentsStrings = assignmentIDS.toString();
        }
        assignedAssignments.setText(assignmentsStrings);

        Set<ID> studentIDS = course.getAssignedStudentsID();
        String studentStrings = "None";
        if (studentIDS.size() > 0) {
            studentStrings = studentIDS.toString();
        }
        assignedStudents.setText(studentStrings);

        ID assignedStaffID = course.getAssignedStaffID();
        if (assignedStaffID == null || assignedStaffID.toString().equals("")) {
            assignedStaff.setText("None");
        } else {
            assignedStaff.setText(assignedStaffID.toString());
        }

        course.getTags().stream()
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
        if (!(other instanceof CourseDetailedCard)) {
            return false;
        }

        // state check
        CourseDetailedCard card = (CourseDetailedCard) other;
        return courseID.getText().equals(card.courseID.getText())
                && amount.getText().equals(card.amount.getText())
                && course.equals(card.course);
    }

    @FXML
    private void selectCourse() {
        String selectedCourseID = courseID.getText();
        String commandText = "select cid/" + selectedCourseID;
        commandBox.runCommand(commandText, "COURSE");
    }
}
