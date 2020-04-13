package seedu.address.ui.student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialName;
import seedu.address.model.mod.ModCode;
import seedu.address.model.student.Student;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on StudentTAble level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label matricNumber;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane modTutorials;


    public StudentCard(Student student, int displayedIndex, ObservableList<Tutorial> tutorialList) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        matricNumber.setText(student.getMatricNumber().value);
        email.setText(student.getEmail().value);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Map<ModCode, List<TutorialName>> modTutorialMap = new HashMap<>();
        for (int i = 0; i < tutorialList.size(); i++) {
            int checker = 0;
            ArrayList<Student> enrolledStudents = tutorialList.get(i).getEnrolledStudents();
            for (int j = 0; j < enrolledStudents.size(); j++) {
                if (student.hasSameMatricNum(enrolledStudents.get(j))
                        || (student.getName().equals(enrolledStudents.get(j).getName())
                        && student.getEmail().equals(enrolledStudents.get(j).getEmail()))) {
                    checker = 1;
                }
            }
            if (checker != 0) {
                modTutorialMap.computeIfAbsent(tutorialList.get(i).getModCode(),
                    t -> new ArrayList<>()).add(tutorialList.get(i).getTutorialName());
            }
        }

        modTutorialMap.entrySet().stream()
                      .forEach(modCodeTutorialNameEntry -> modTutorials.getChildren()
                              .add(new Label(modCodeTutorialNameEntry.getKey().toString() + ": "
                                      + modCodeTutorialNameEntry.getValue().stream()
                                        .map(t -> t.getTutorialName())
                                        .collect(Collectors.joining(", ")))));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
