package fithelper.ui.profile;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.profile.Profile;
import fithelper.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for a user profile page.
 * The profile page contains all user basic information.
 */
public class ProfilePage extends UiPart<AnchorPane> {
    private static final String FXML = "ProfilePage.fxml";
    private final Logger logger = LogsCenter.getLogger(ProfilePage.class);

    @FXML
    private Label name;

    @FXML
    private Label gender;

    @FXML
    private Label age;

    @FXML
    private Label address;

    @FXML
    private Label height;

    @FXML
    private Label targetweight;

    @FXML
    private Label currentweight;

    @FXML
    private Label currentbmi;

    /**
     * Creates a new ProfileWindow displaying user basic data.
     */
    public ProfilePage(ReadOnlyUserProfile profile) {
        super(FXML);
        logger.info("Initializing Profile Page");

        initializeAttributeValue(profile.getUserProfile());
    }

    /**
     * Initialize the value of each profile attribute.
     *
     * @param profile an profile object containing user basic data.
     */
    private void initializeAttributeValue(Profile profile) {
        name.setText(profile.getName().toString());
        gender.setText(profile.getGender().toString());
        age.setText(profile.getAge().toString());
        address.setText(profile.getAddress().toString());
        height.setText(profile.getHeight().toString() + " cm");
        targetweight.setText(profile.getTargetWeight().toString() + " kg");

        String weightText;
        String bmiText;
        if (profile.getCurrentWeight() == null) {
            weightText = "Not Available Now";
        } else {
            weightText = profile.getCurrentWeight().toString() + " kg";
        }
        currentweight.setText(weightText);
        if (profile.getCurrentBmi() == null) {
            bmiText = "Not Available Now";
        } else {
            bmiText = profile.getCurrentBmi().toString();
        }
        currentbmi.setText(bmiText);
    }

}
