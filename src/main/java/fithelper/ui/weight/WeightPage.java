package fithelper.ui.weight;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.weight.Weight;
import fithelper.ui.UiPart;

//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for a user weight page.
 * The weight page contains all user weight and bmi information.
 */
public class WeightPage extends UiPart<AnchorPane> {
    private static final String FXML = "WeightPage.fxml";
    private final Logger logger = LogsCenter.getLogger(WeightPage.class);


    /**
     * Creates a new Weight Window displaying user weight-related data.
     */
    public WeightPage(ReadOnlyUserProfile profile, ObservableList<Weight> weights) {
        super(FXML);
        logger.info("Initializing Weight Page");
    }

}
