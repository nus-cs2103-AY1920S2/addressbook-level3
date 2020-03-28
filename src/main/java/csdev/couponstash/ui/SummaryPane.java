package csdev.couponstash.ui;

import csdev.couponstash.logic.Logic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


/**
 * Summary of CouponStash.
 */
public class SummaryPane extends UiPart<Region> {
    private static final String FXML = "SummaryPane.fxml";

    // Independent Ui parts residing in this Ui container
    private Logic logic;

    @FXML
    private Label label;

    public SummaryPane(Logic logic) {
        super(FXML);
        this.logic = logic;
        label.setText("THIS SPACE IS FOR THE SUMMARY!!!");
    }
}
