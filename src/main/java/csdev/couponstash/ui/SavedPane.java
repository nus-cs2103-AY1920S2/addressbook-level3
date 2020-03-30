package csdev.couponstash.ui;

import csdev.couponstash.logic.Logic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


/**
 * Savings summary of CouponStash.
 */
public class SavedPane extends UiPart<Region> {
    private static final String FXML = "SavedPane.fxml";

    // Independent Ui parts residing in this Ui container
    private Logic logic;

    @FXML
    private Label label;

    public SavedPane(Logic logic) {
        super(FXML);
        this.logic = logic;
        label.setText("THIS SPACE IS FOR THE SAVED SUMMARY!!!");
    }
}
