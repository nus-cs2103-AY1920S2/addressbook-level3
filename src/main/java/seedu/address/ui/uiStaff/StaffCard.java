package seedu.address.ui.uiStaff;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.modelStaff.Staff;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Staff}.
 */
public class StaffCard extends UiPart<Region> {

    private static final String FXML = "StaffListCard.fxml";

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
    private Label staffID;
    @FXML
    private Label level;
    @FXML
    private ImageView staffImage;

    private CommandBox commandBox;
    Image staffGuy = new Image(getClass().getResourceAsStream("/view/ourImages/staff.png"));
    Image staffGirl = new Image(getClass().getResourceAsStream("/view/ourImages/staffGirl.png"));

    public StaffCard(Staff staff, CommandBox commandBox, int displayedIndex) {
        super(FXML);
        this.staff = staff;
        this.commandBox = commandBox;
        id.setText(displayedIndex + ". ");
        staffID.setText(staff.getId().value);
        name.setText(staff.getName().fullName);
        String gender = staff.getGender().value;

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
        if (!(other instanceof StaffCard)) {
            return false;
        }

        // state check
        StaffCard card = (StaffCard) other;
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
