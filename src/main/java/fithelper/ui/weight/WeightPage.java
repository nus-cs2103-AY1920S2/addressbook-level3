package fithelper.ui.weight;

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
public class WeightPage extends UiPart<AnchorPane> {
    private static final String FXML = "WeightPage.fxml";
    private final Logger logger = LogsCenter.getLogger(WeightPage.class);


    /**
     * Creates a new ProfileWindow displaying user basic data.
     */
    public WeightPage() {
        super(FXML);
        logger.info("Initializing Weight Page");
    }

}
