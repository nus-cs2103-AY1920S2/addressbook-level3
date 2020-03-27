package seedu.zerotoone.ui.views.log;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the log page.
 */
public class LogListPanel extends UiPart<Region> {
    private static final String FXML = "log/LogListPanel.fxml";

    @FXML
    private Text logView;

    public LogListPanel() {
        super(FXML);
    }
}
