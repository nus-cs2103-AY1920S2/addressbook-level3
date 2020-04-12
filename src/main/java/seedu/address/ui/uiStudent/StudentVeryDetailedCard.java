package seedu.address.ui.uiStudent;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelStudent.Student;
import seedu.address.ui.CommandBox;
import seedu.address.ui.ProgressCard;
import seedu.address.ui.UiPart;

import java.util.Comparator;
import java.util.Set;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentVeryDetailedCard extends UiPart<Region> {

    private static final String FXML = "StudentListVeryDetailedCard.fxml";

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
    private Label noOfDoneProgress;
    @FXML
    private FlowPane tags;
    @FXML
    private ListView<Progress> progressListView;
    @FXML
    private ImageView studentImage;

    private CommandBox commandBox;
    Image studentGuy = new Image(getClass().getResourceAsStream("/view/ourImages/student.png"));
    Image studentGirl = new Image(getClass().getResourceAsStream("/view/ourImages/studentGirl.png"));
    private String selectedCourseID;

    public StudentVeryDetailedCard(Student student, String selectedCourseID, ObservableList<Progress> progressList, int progress, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.student = student;
        this.selectedCourseID = selectedCourseID;
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

        noOfDoneProgress.setText(String.valueOf(progress));

        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        String gender = student.getGender().value;

        if (gender.equals("m")) {
            studentImage.setImage(studentGuy);
        } else if (gender.equals("f")) {
            studentImage.setImage(studentGirl);
        }

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
        if (!(other instanceof StudentVeryDetailedCard)) {
            return false;
        }

        // state check
        StudentVeryDetailedCard card = (StudentVeryDetailedCard) other;
        return studentID.getText().equals(card.studentID.getText())
                && student.equals(card.student);
    }

    @FXML
    private void selectStudent() {
        String selectedStudentID = studentID.getText();
        String commandText = "select cid/" + selectedCourseID + " sid/" + selectedStudentID;
        commandBox.runCommand(commandText, "COURSE");
    }
}
