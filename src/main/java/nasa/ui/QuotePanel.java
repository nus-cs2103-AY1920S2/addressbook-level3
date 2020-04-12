package nasa.ui;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * UI component for quote panel.
 */
public class QuotePanel extends UiPart<Region> {

    private static final String FXML = "QuotePanel.fxml";
    private int time = 5000;

    Popup popup;

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
    public void show(Stage stage, double width) {
        if (popup.isShowing()) {
            popup.setOnCloseRequest(x ->
                    new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play());
        } else {

            stage.widthProperty().addListener((observable, oldValue, newValue) -> {
                popup.setX(stage.getX() + width - label.getPrefWidth()/2);
            });

            popup.show(stage);
            new Timeline(new KeyFrame(Duration.millis(time), runtime -> popup.hide())).play();
        }
    }
}
