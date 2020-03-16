package fithelper.ui;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<AnchorPane> {
    public static final String LIST_OF_VALID_COMMANDS = "The valid commands are as follows:\n"
            + "addX n/NAME t/DATETIME l/LOCATION c/CALORIE es/False r/REMARK: add an entry\n"
            + "...\n";
    public static final String LOOK_FOR_URL = "For detailed information regarding the usage of commands, "
            + "please access the User Guide via the following url:\n";
    public static final String USERGUIDE_URL =
            "https://github.com/AY1920S2-CS2103-T09-4/main/blob/master/docs/UserGuide.adoc";
    public static final String HELP_MESSAGE = LIST_OF_VALID_COMMANDS + LOOK_FOR_URL + USERGUIDE_URL;
    private static final String FXML = "HelpWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    
    @FXML
    private AnchorPane helpPage;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        super(FXML);
        logger.info("Initializing Help Page");
        helpMessage.setText(HELP_MESSAGE);
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
