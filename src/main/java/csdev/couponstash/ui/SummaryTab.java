package csdev.couponstash.ui;

import csdev.couponstash.logic.Logic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


/**
 * Savings summary of CouponStash.
 */
public class SummaryTab extends UiPart<Region> {
    private static final String FXML = "SummaryTab.fxml";

    // Independent Ui parts residing in this Ui container
    private Logic logic;

    @FXML
    private Label label;

    public SummaryTab(Logic logic) {
        super(FXML);
        this.logic = logic;
        label.setText("You saved a total of ");
    }
}
