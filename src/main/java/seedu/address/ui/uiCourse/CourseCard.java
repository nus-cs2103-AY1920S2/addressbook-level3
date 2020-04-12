package seedu.address.ui.uiCourse;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelCourse.Course;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Assignment}.
 */
public class CourseCard extends UiPart<Region> {

    private static final String FXML = "CourseListCard.fxml";

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

    private CommandBox commandBox;

    public CourseCard(Course course, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.course = course;
        this.commandBox = commandBox;
        name.setText(course.getName().fullName);
        id.setText(displayedIndex + ". ");
        courseID.setText(course.getId().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CourseCard)) {
            return false;
        }

        // state check
        CourseCard card = (CourseCard) other;
        return courseID.getText().equals(card.courseID.getText())
                && course.equals(card.course);
    }

    @FXML
    private void selectCourse() {
        String selectedCourseID = courseID.getText();
        String commandText = "select cid/" + selectedCourseID;
        commandBox.runCommand(commandText, "COURSE");
    }
}
