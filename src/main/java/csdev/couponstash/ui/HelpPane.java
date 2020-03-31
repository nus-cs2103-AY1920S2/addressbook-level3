package csdev.couponstash.ui;

import csdev.couponstash.logic.Logic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


/**
 * Help page for usage of CouponStash.
 */
public class HelpPane extends UiPart<Region> {
    private static final String FXML = "HelpPane.fxml";

    // Independent Ui parts residing in this Ui container
    private Logic logic;

    @FXML
    private Label label;

    public HelpPane(Logic logic) {
        super(FXML);
        this.logic = logic;
        label.setText("THIS SPACE IS FOR HELP!!!");
    }
}
