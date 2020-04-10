package seedu.address.ui;

import javafx.scene.layout.Region;

//@@author jadetayy
/**
 * Panel containing the home page (when there is no active module being displayed).
 */
public class WelcomeView extends UiPart<Region> {
    private static final String FXML = "WelcomeViewPanel.fxml";

    public WelcomeView() {
        super(FXML);
    }
}
