package seedu.address.ui.uiStudent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.Comparator;
import java.util.Set;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentDetailedCard extends UiPart<Region> {

    private static final String FXML = "StudentListDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     * level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label studentID;
    @FXML
    private Label assignedCourses;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView studentImage;

    private CommandBox commandBox;
    Image studentGuy = new Image(getClass().getResourceAsStream("/view/ourImages/student.png"));
    Image studentGirl = new Image(getClass().getResourceAsStream("/view/ourImages/studentGirl.png"));

    public StudentDetailedCard(Student student, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.student = student;
        this.commandBox = commandBox;
        id.setText(displayedIndex + ". ");
        studentID.setText(student.getId().value);
        name.setText(student.getName().fullName);

        Set<ID> courseIDS = student.getAssignedCoursesID();
        String courseStrings = "None";
        if (courseIDS.size() > 0) {
            courseStrings = courseIDS.toString();
        }
        assignedCourses.setText(courseStrings);

        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        String gender = student.getGender().value;

        if (gender.equals("m")) {
            studentImage.setImage(studentGuy);
        } else if (gender.equals("f")) {
            studentImage.setImage(studentGirl);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentDetailedCard)) {
            return false;
        }

        // state check
        StudentDetailedCard card = (StudentDetailedCard) other;
        return studentID.getText().equals(card.studentID.getText())
                && student.equals(card.student);
    }

    @FXML
    private void selectStudent() {
        String selectedStudentID = studentID.getText();
        String commandText = "select sid/" + selectedStudentID;
        commandBox.runCommand(commandText, "STUDENT");
    }
}
