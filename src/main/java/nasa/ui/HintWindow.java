package nasa.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * UI component for quote panel.
 */
public class HintWindow extends UiPart<Region> {

    private static final String FXML = "HintWindow.fxml";

    private Popup popup;
    @FXML
    private Label label;

    /**
     * Create a quote message.
     */
    public HintWindow() {
        super(FXML);
        label.setText("");
        label.setWrapText(true);
    }

    /**
     * Creates a Popup to store quote messages in a label.
     * @return Popup
     */
    public Popup getInput(String input) {
        popup = new Popup();
        popup.setAutoFix(true);
        label.setText(input);
        label.setWrapText(true);
        popup.getContent().add(label);
        return popup;
    }

    /**
     * Show quote message.
     * @param stage Stage
     */
    public void show(Stage stage) {
        popup.setWidth(stage.getWidth());
        label.setMaxWidth(stage.getWidth());
        popup.show(stage);
        if (!stage.isFullScreen()) {
            popup.show(stage, stage.getX() + 10, stage.getHeight() - 80 - popup.getHeight());
        } else {
            popup.show(stage, stage.getX() + 10, stage.getHeight() - 130 - popup.getHeight());
        }
    }

    public void hide() {
        popup.hide();
    }

    public boolean isShowing() {
        return popup != null && popup.isShowing();
    }
}
