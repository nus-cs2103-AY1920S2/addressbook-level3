package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;

/**
 * An UI component that displays administrative information of a {@code Student}.
 */
public class StudentCardAdmin extends UiPart<Region> {

    private static final String FXML = "StudentListCardAdmin.fxml";
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
    private Label temperature;
    @FXML
    private Label attendance;


    public StudentCardAdmin(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        temperature.setText(student.getTemperature().value.equals("Insert temperature here!")
                ? "Temperature:   " + student.getTemperature().value
                : "Temperature:   " + student.getTemperature().value + " \u2103");
        attendance.setText("Attendance:   " + student.getAttendance().value);
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
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCardAdmin card = (StudentCardAdmin) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
