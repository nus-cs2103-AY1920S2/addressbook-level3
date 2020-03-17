package fithelper.ui.profile;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.ui.UiPart;

//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for a user profile page.
 * The profile page contains all user basic information.
 */
public class ProfilePage extends UiPart<AnchorPane> {
    private static final String FXML = "ProfilePage.fxml";
    private final Logger logger = LogsCenter.getLogger(ProfilePage.class);


    /**
     * Creates a new ProfileWindow displaying user basic data.
     */
    public ProfilePage() {
        super(FXML);
        logger.info("Initializing Profile Page");
    }

}
