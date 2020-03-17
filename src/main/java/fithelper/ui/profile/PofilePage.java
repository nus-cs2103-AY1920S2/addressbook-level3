package fithelper.ui.profile;

import fithelper.commons.core.LogsCenter;
import fithelper.ui.UiPart;

//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

/**
 * Controller for a user profile page.
 * The profile page contains all user basic information.
 */
public class PofilePage extends UiPart<AnchorPane> {
    private static final String FXML = "ProfileWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(PofilePage.class);


    /**
     * Creates a new ProfileWindow displaying user basic data.
     */
    public PofilePage() {
        super(FXML);
        logger.info("Initializing Profile Page");
    }

}
