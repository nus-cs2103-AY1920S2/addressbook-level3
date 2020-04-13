package nasa.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane pane;

    @FXML
    private Label label;

    /**
     * Create a quote panel.
     */
    public QuotePanel() {
        super(FXML);
        popup = new Popup();
        label.setMaxWidth(400);
        label.setWrapText(true);
        pane.prefWidthProperty().bind(label.prefWidthProperty());
        pane.prefHeightProperty().bind(label.prefHeightProperty());
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.getContent().add(pane);
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
            new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play();
        }
        popup.centerOnScreen();

        new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play();
        popup.show(stage);
    }
}
