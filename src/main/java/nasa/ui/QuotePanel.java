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
public class QuotePanel extends UiPart<Region> {

    private static final String FXML = "QuotePanel.fxml";
    private int time = 5000;

    private Popup popup;

    @FXML
    private Label label;

    /**
     * Create a quote panel.
     */
    public QuotePanel() {
        super(FXML);
        popup = new Popup();
        label.setPrefWidth(400);
        label.setWrapText(true);
        popup.setAutoFix(true);
        popup.getContent().add(label);
    }

    public void setText(String input) {
        label.setText(input);
    }

    /**
     * Show quote message.
     * @param stage Stage
     */
    public void show(Stage stage) {
        if (popup.isShowing()) {
            popup.setOnCloseRequest(x ->
                    new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play());
        } else {
            popup.centerOnScreen();
            popup.show(stage);
            new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play();
        }
    }
}
