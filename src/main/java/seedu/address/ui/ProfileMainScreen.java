package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.StudentProfile.Profile;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ProfileMainScreen extends UiPart<Region> {

    private static final String FXML = "ProfileMainScreen.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    @FXML
    private StackPane profileMainScreenPanel;
    @FXML
    private Label name;
    @FXML
    private Label major;
    @FXML
    private Label currentCap;
    @FXML
    private Label targetCap;


    public ProfileMainScreen(Profile student) {
        super(FXML);

        name.setText(student.getName());
        major.setText(student.getMajor());
        currentCap.setText(student.getCap());
        targetCap.setText(student.getTargetCap());


    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileMainScreen)) {
            return false;
        }
        return false;
    }
}
