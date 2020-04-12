package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

//@@author jadetayy
/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s2-cs2103t-w13-3.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "For more information, refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;
    @FXML
    private VBox helpCommand;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        Text title = new Text("MODdy Commands:");
        title.setStyle("-fx-font-family: \"Segoe Pro Display\"; -fx-font-weight: bold;\n"
                + "-fx-font-size: 35px;\n"
                + "-fx-fill: #f78086;");

        Text title2 = new Text("EXAMPLE OF COMMANDS FORMAT: \n"
                + "1. New user profile: \n"
                + "       - new n/John c/Computer Science y/2.2 [f/Software Engineering] \n\n"
                + "2. Add a current/completed module to MODdy: \n"
                + "       - add m/CS2103T y/2.2 [g/A+]\n\n"
                + "3. Add a task with a deadline to an existing module in MODdy:\n"
                + "       - add m/CS2103T t/Project [d/2020-04-12 23:59]\n\n"
                + "4. Edit your profile:\n"
                + "       - edit [n/Amy] [c/Business Analytics] [y/3.1] [s/Finance Analytics]\n\n"
                + "5. Edit a module’s details in MODdy:\n"
                + "       - edit m/CS2103T [y/3.1] [g/A]\n\n"
                + "6. Edit a task’s description or deadline:\n"
                + "       - edit m/CS2103T t/Project [nt/Quiz] [d/2020-04-20 12:00]\n\n");

        helpCommand.getChildren().addAll(title, title2);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
