package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.profile.Profile;

//@@author jadetayy
/**
 * An UI component that displays information of a {@code Profile}.
 */
public class ProfileCard extends UiPart<Region> {

    private static final String FXML = "ProfileListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Profile profile;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label course;
    @FXML
    private Label curYear;
    @FXML
    private Label curSem;
    @FXML
    private Label focusArea;

    public ProfileCard(Profile profile) {
        super(FXML);
        this.profile = profile;
        name.setText(profile.getName().fullName.toUpperCase());
        course.setText("Course: " + profile.getCourseName().toString());
        curYear.setText("Current Year: " + profile.getCurrentYear());
        curSem.setText("Current Semester: " + profile.getCurrentSemester());
        if (profile.getFocusArea() != null) {
            focusArea.setText("Focus Area: " + profile.getFocusAreaString());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCard)) {
            return false;
        }

        // state check
        ProfileCard card = (ProfileCard) other;
        return id.getText().equals(card.id.getText())
                && profile.equals(card.profile);
    }
}
