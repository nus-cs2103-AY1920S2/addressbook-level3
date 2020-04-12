package seedu.address.ui.uiCourse;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelProgress.Progress;
import seedu.address.ui.CommandBox;
import seedu.address.ui.ProgressCard;
import seedu.address.ui.UiPart;

import java.util.Comparator;
import java.util.Set;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class CourseVeryDetailedCard extends UiPart<Region> {

    private static final String FXML = "CourseListVeryDetailedCard.fxml";

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
    private Label noOfDoneProgress;
    @FXML
    private FlowPane tags;
    @FXML
    private ListView<Progress> progressListView;

    private CommandBox commandBox;
    private String selectedStudentID;

    public CourseVeryDetailedCard(Course course, String selectedStudentID, ObservableList<Progress> progressList, int progress, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.course = course;
        this.selectedStudentID = selectedStudentID;
        this.commandBox = commandBox;
        name.setText(course.getName().fullName);
        id.setText(displayedIndex + ". ");
        courseID.setText(course.getId().value);
        noOfDoneProgress.setText(String.valueOf(progress));

        Set<ID> assignmentIDS = course.getAssignedAssignmentsID();
        String assignmentsStrings = "None";
        if (assignmentIDS.size() > 0) {
            assignmentsStrings = assignmentIDS.toString();
        }
        assignedAssignments.setText(assignmentsStrings);

        course.getAssignedAssignmentsID();
        amount.setText(course.getAmount().value);

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

        progressListView.setItems(progressList);
        progressListView.setCellFactory(listView -> new ProgressListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Progress} using a {@code
     * ProgressCard}.
     */
    class ProgressListViewCell extends ListCell<Progress> {

        @Override
        protected void updateItem(Progress progress, boolean empty) {
            super.updateItem(progress, empty);

            if (empty || progress == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProgressCard(progress, commandBox, getIndex() + 1).getRoot());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CourseVeryDetailedCard)) {
            return false;
        }

        // state check
        CourseVeryDetailedCard card = (CourseVeryDetailedCard) other;
        return courseID.getText().equals(card.courseID.getText())
                && amount.getText().equals(card.amount.getText())
                && course.equals(card.course);
    }

    @FXML
    private void selectCourse() {
        String selectedCourseID = courseID.getText();
        String commandText = "select sid/" + selectedStudentID + " cid/" + selectedCourseID;
        commandBox.runCommand(commandText, "STUDENT");
    }
}
