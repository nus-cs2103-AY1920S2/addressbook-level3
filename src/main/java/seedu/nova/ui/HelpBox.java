package seedu.nova.ui;

import static java.util.Objects.requireNonNull;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * Class for help ui component
 */
public class HelpBox extends UiPart<Region> {
    private static final String FXML = "HelpDisplay.fxml";

    @javafx.fxml.FXML
    private TextArea helpDisplay;

    public HelpBox() {
        super(FXML);
    }

    public void setHelp(String help) {
        requireNonNull(help);
        helpDisplay.setText(help);
    }

}
