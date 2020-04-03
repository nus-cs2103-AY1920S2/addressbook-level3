package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

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
        title.setStyle("-fx-font-family: \"Segoe Pro Display\"; -fx-font-weight: bold;\n" +
                "-fx-font-size: 35px;\n" +
                "-fx-fill: #f78086;");

        Text title2 = new Text("COMMANDS FORMAT: \n" +
                "1. New user profile: \n" +
                "       - new n/NAME c/COURSE cs/CURRENT_SEMESTER [s/SPECIALISATION] \n" +
                "2. Add a current/completed module to MODdy: \n" +
                "       - add m/MODULE y/SEMESTER_INDEX [g/GRADE]\n" +
                "3. Add a task with a deadline to an existing module in MODdy:\n" +
                "       - add m/MODULE t/TASK [d/DEADLINE]\n" +
                "4. Edit your profile:\n" +
                "       - edit [n/NAME] [c/COURSE] [cs/CURRENT_SEMESTER] [s/SPECIALISATION]\n" +
                "5. Edit a module’s details in MODdy:\n" +
                "       - edit m/MODULE [y/SEMESTER_TAKEN] [g/GRADE]\n" +
                "6. Edit a task’s description or deadline:\n" +
                "       - edit m/MODULE t/TASK [nt/NEW_TASK] [d/DEADLINE]\n");
        Text title3 = new Text("PARAMETERS ACCEPTED: ");
        Text newCommands = new Text("1. `new` command \n" +
                "       - n/USER_NAME\n" +
                "       - c/COURSE_NAME\n" +
                "       - cs/CURRENT_SEMESTER\n" +
                "       - [s/SPECIALISATION]\n");

        Text addCommands = new Text("2. `add` command \n" +
                "       - m/MODULE_CODE\n" +
                "       - y/SEMESTER\n" +
                "       - [g/GRADE]\n" +
                "       - [t/TASK]\n" +
                "       - [d/DEADLINE]\n");

        Text editCommands = new Text("3. `edit` command \n" +
                "       - [n/NEW_NAME]\n" +
                "       - [c/NEW_COURSE]\n" +
                "       - [cs/CURRENT_SEMESTER]\n" +
                "       - [s/SPECIALISATION]\n" +
                "       - [d/DEADLINE]\n");

        Text showCommands = new Text("4. `show` command \n" +
                "       - n/USER_NAME\n" +
                "       - y/SEMESTER_INDEX\n" +
                "       - c/COURSE_NAME\n" +
                "       - f/FOCUS_AREA\n" +
                "       - m/MODULE_CODE\n");

        Text deleteCommands = new Text("5. `delete` command \n" +
                "       - n/USER_NAME\n" +
                "       - t/TASK\n" +
                "       - m/MODULE_CODE\n");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(newCommands, addCommands, editCommands, showCommands, deleteCommands);
        helpCommand.getChildren().addAll(title, title2, title3, hbox);
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
