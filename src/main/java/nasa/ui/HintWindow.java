package nasa.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
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
    private GridPane popUp;
    @FXML
    private Label description;
    @FXML
    private Label parameters;
    @FXML
    private Label example;
    @FXML
    private Label commandMessage;
    @FXML
    private Label parametersMessage;
    @FXML
    private Label exampleMessage;
    @FXML
    private Label copyMessage;
    @FXML
    private Button copyExample;

    public HintWindow() {
        super(FXML);
    }

    /**
     * Updates the input of the hint window
     */
    public void setInput(String input) {
        popup = new Popup();
        popup.setHideOnEscape(true);
        popup.setAutoFix(true);
        commandMessage.setText(input.split("\n")[0].split(" ", 2)[1]);
        parametersMessage.setText(input.split("\n")[1].split(" ", 2)[1]);
        exampleMessage.setText(input.split("\n")[2].split(" ", 2)[1]);
        popup.getContent().add(popUp);
    }

    /**
     * Displays the hint panel to the user.
     * @param stage Stage
     */
    public void show(Stage stage) {
        popUp.setMaxWidth(stage.getWidth() * 3 / 4);
        popup.show(stage);
        popup.setAnchorX(stage.getX());
        popup.setAnchorY(stage.getHeight() - 125 - popup.getHeight());
    }

    public void hide() {
        popup.hide();
    }

    public boolean isShowing() {
        return popup != null && popup.isShowing();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyExample() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent example = new ClipboardContent();
        example.putString(exampleMessage.getText());
        clipboard.setContent(example);
    }
}
