package seedu.address.ui.uiStaff;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStaff.Staff;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

import java.util.Comparator;
import java.util.Set;

/**
 * An UI component that displays information of a {@code Staff}.
 */
public class StaffDetailedCard extends UiPart<Region> {

    private static final String FXML = "StaffListDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be
     * thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     * level 4</a>
     */

    public final Staff staff;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label level;
    @FXML
    private Label staffID;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label salary;
    @FXML
    private Label assignedCourses;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView staffImage;

    private CommandBox commandBox;
    Image staffGuy = new Image(getClass().getResourceAsStream("/view/ourImages/staff.png"));
    Image staffGirl = new Image(getClass().getResourceAsStream("/view/ourImages/staffGirl.png"));

    public StaffDetailedCard(Staff staff, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.staff = staff;
        this.commandBox = commandBox;
        id.setText(displayedIndex + ". ");
        staffID.setText(staff.getId().value);
        name.setText(staff.getName().fullName);
        phone.setText(staff.getPhone().value);
        address.setText(staff.getAddress().value);
        email.setText(staff.getEmail().value);
        String gender = staff.getGender().value;

        Set<ID> courseIDS = staff.getAssignedCoursesID();
        String coursesStrings = "None";
        if (courseIDS.size() > 0) {
            coursesStrings = courseIDS.toString();
        }
        assignedCourses.setText(coursesStrings);

        salary.setText(staff.getSalary().value);
        staff.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        String staffLevel = staff.getLevel().toString();
        staffLevel = staffLevel.substring(0, 1).toUpperCase() + staffLevel.substring(1).toLowerCase();
        level.setText(staffLevel);

        if (gender.equals("m")) {
            staffImage.setImage(staffGuy);
        } else if (gender.equals("f")) {
            staffImage.setImage(staffGirl);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StaffDetailedCard)) {
            return false;
        }

        // state check
        StaffDetailedCard card = (StaffDetailedCard) other;
        return id.getText().equals(card.id.getText())
                && staff.equals(card.staff);
    }

    @FXML
    private void selectStaff() {
        String selectedStaffID = staffID.getText();
        String commandText = "select tid/" + selectedStaffID;
        commandBox.runCommand(commandText, "STAFF");
    }
}
