package csdev.couponstash.ui;

import java.util.logging.Logger;

import csdev.couponstash.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class RemindWindow extends UiPart<Stage> {

    public static final String REMIND_MESSAGE = "Halt! " + "Remember to use these coupon(s) before it expires!"
            + "\n ";

    private static final Logger logger = LogsCenter.getLogger(RemindWindow.class);
    private static final String FXML = "RemindWindow.fxml";

    @FXML
    private Button okButton;

    @FXML
    private Label remindMessage;

    /**
     * Creates a new RemindWindow.
     *
     * @param root Stage to use as the root of the RemindWindow.
     */
    public RemindWindow(Stage root) {
        super(FXML, root);
        remindMessage.setText(REMIND_MESSAGE);
    }

    /**
     * Creates a new RemindWindow.
     */
    public RemindWindow() {
        this(new Stage());
    }

    /**
     * This method is to display a default remind window pop when there is
     * a matching coupon's remind date with today's date.
     * @param message that need to be printed
     */
    public static void displayRemind(String message) {

        logger.fine("Showing remind page about the application.");
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("You are reminded!");
        window.setMinWidth(500);

        Label label = new Label();
        label.setText(REMIND_MESSAGE + message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
