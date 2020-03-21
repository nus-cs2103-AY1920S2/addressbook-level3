package seedu.zerotoone.ui.views.home;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the home page.
 */
public class HomePanel extends UiPart<Region> {
    private static final String FXML = "home/HomePanel.fxml";

    @FXML
    private Text homeView;

    public HomePanel() {
        super(FXML);
    }
}
