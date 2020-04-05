package seedu.address.ui;

import java.io.File;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;

/**
 * An UI component that displays detailed information of a {@code Student}.
 */
public class StudentCardDetailed extends UiPart<Region> {

    private static final String FXML = "StudentListCardDetailed.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private Circle circle;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label temperature;
    @FXML
    private Label attendance;
    @FXML
    private Label nokName;
    @FXML
    private Label nokRelationship;
    @FXML
    private Label nokContact;


    /**
     * Constructor to create the student card (detailed) controller.
     * Important to note the format of the image, [namelowercasenospace].png
     * eg. Name is Simon Lam, image name is simonlam.png. 1
     * 1. All lower case
     * 2. No whitespaces
     */
    public StudentCardDetailed(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText("Mobile:  " + student.getPhone().value);
        address.setText("Address: " + student.getAddress().value);
        email.setText("Email:   " + student.getEmail().value);
        temperature.setText(student.getTemperature().value.equals("Insert temperature here!")
                ? "Temperature:   " + student.getTemperature().value
                : "Temperature:   " + student.getTemperature().value + " \u2103");
        attendance.setText("Attendance:   " + student.getAttendance().value);
        nokName.setText("NOK Name:  " + student.getNok().getNameOfNok());
        nokRelationship.setText("NOK Relationship:  " + student.getNok().getRelationshipOfNok());
        nokContact.setText("NOK Contact:  " + student.getNok().getContactOfNok());
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        updateImage();
    }

    /**
     * Updates profile image of student
     */
    public void updateImage() {
        try {
            String path = "images/" + student.getName().toString().toLowerCase().replaceAll("\\s+", "") + ".png";
            File file = new File(path);
            if (!file.exists()) {
                Image defaultImage = new Image("images/default_person.png");
                circle.setStroke(Color.ROSYBROWN);
                circle.setFill(new ImagePattern(defaultImage));
                circle.setEffect(new DropShadow(+10d, 0d, +2d, Color.ROSYBROWN));
            } else {
                Image newImage = new Image(file.toURI().toString());
                circle.setStroke(Color.ROSYBROWN);
                circle.setFill(new ImagePattern(newImage));
                circle.setEffect(new DropShadow(+10d, 0d, +2d, Color.ROSYBROWN));
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCardDetailed)) {
            return false;
        }

        // state check
        StudentCardDetailed card = (StudentCardDetailed) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
