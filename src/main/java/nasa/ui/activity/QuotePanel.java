package nasa.ui.activity;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import nasa.ui.UiPart;

/**
 * UI component for quote panel.
 */
public class QuotePanel extends UiPart<Region> {

    private static final String FXML = "QuotePanel.fxml";
    private int time = 5000;

    @FXML
    private Label label;

    /**
     * Create a quote message.
     * @param input String
     */
    public QuotePanel(String input) {
        super(FXML);
        label.setText(input);
        label.setMaxWidth(500);
        label.setWrapText(true);
    }

    /**
     * Creates a Popup to store quote messages in a label.
     * @return Popup
     */
    private Popup createNotification() {
        final Popup popUp = new Popup();
        popUp.setAutoFix(true);
        popUp.getContent().add(label);
        return popUp;
    }

    /**
     * Show quote message.
     * @param stage Stage
     */
    public void show(Stage stage) {
        final Popup popup = createNotification();
        popup.setOnShown(execute -> popup.centerOnScreen());
        popup.show(stage);
        new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play();
    }
}
