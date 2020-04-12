package nasa.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        label.setMaxWidth(500);
        label.setWrapText(true);
        popup.getContent().add(label);
        return popup;
    }

    /**
     * Show quote message.
     * @param stage Stage
     */
    public void show(Stage stage) {
        popup.setOnShown(execute -> popup.centerOnScreen());
        label.setMaxWidth(stage.getMaxWidth());
        popup.show(stage, stage.getX() + 10, stage.getY() + stage.getHeight() - 180);
    }

    public void hide() {
        popup.hide();
    }
}
