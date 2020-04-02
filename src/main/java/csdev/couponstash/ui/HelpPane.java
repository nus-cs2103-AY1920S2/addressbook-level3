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

    private static final String COMMAND_SUMMARY = ""
            + "Add: add n/NAME e/EXPIRY_DATE s/SAVINGS [u/USAGE] [l/USAGE_LIMIT] [p/PROMO_CODE] "
            + "[sd/START_DATE] [r/REMIND_DATE] [c/CONDITION] [s/SAVINGS]… [t/TAG]…\n"
            + "e.g. <code>add</code> n/The Deck Chicken Rice s/20% sd/2-3-2020 e/30-8-2020 t/friend t/value\n";

    // Independent Ui parts residing in this Ui container
    private Logic logic;

    @FXML
    private Label label;

    public HelpPane(Logic logic) {
        super(FXML);
        this.logic = logic;
        label.setText(COMMAND_SUMMARY);
    }
}
