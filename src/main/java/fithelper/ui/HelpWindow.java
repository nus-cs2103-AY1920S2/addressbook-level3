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
    public static final String LIST_OF_VALID_COMMANDS = "General rules:\n"
            + "1) TYPE can only be food(f) OR sports(s).\n"
            + "2) [prefix/ATTRIBUTE] means an optional attribute for this command,\n"
            + "   while prefix/ATTRIBUTE means a compulsory attribute.\n\n"
            + "Common commands, their format and functions are as follows:\n"
            + "add x/TYPE n/NAME t/YYYY-MM-DD-24:60 l/LOCATION c/CALORIE\n"
            + "  - add a food/sports entry.\n"
            + "list [x/TYPE] [d/YYYY-MM-DD]\n"
            + "  - list (food/sports) entries (of a given day).\n"
            //+ "reminder (x/TYPE) (d/YYYY-MM-DD)\n"
            //+ "  - list all entries (of food/sport) not marked as done (in a given day)\n"
            + "edit x/TYPE [d/YYYY-MM-DD] i/INDEX s/Done OR Undone\n"
            + "  - Mark an entry as done/not done.\n"
            + "edit x/TYPE i/INDEX [n/NAME] [t/YYYY-MM-DD-24:60] [l/LOCATION] [c/CALORIE] [r/REMARK]\n"
            + "  - edit the information of an entry.\n"
            + "delete x/type [d/YYYY-MM-DD] i/INDEX\n"
            + "  - delete an entry.\n"
            + "home (i.e. dashboard) /today/calendar/profile/weight/diary/help\n"
            + "  - go to the specified page.\n"
            + "find [x/TYPE] k/KEYWORDS\n"
            + "  - show all (food/sports) entries (whose description contains any of the keywords).\n"
            + "addDiary d/YYYY-MM-DD dc/CONTENT\n"
            + "  - add a diary for a specified date.\n"
            + "appendDiary d/YYYY-MM-DD dc/APPENDED_CONTENT\n"
            + "  - appends new content to the diary of a specified date.\n"
            + "editDiary d/YYYY-MM-DD dc/NEW_CONTENT\n"
            + "  - rewrite the diary of a specified date.\n"
            //+ "table x/TYPE YYYY-MM-DD\n"
            //+ "  - show calorie table of a specified date.\n"
            + "findDiary [d/YYYY-MM-DD] [k/KEYWORDS]\n"
            + "  - list all diaries, diary of a given date or those containing the keywords.\n"
            + "calendar [d/YYYY-MM-DD] [m/ls OR tb] [sh/YYYY-MM-DD]\n"
            + "  - show calendar of a specified time period, mode can be switched to list or timetable,\n"
            + "    or shows entries of a particular date.\n"
            + "check x/TYPE k/KEYWORDS\n"
            + "  - search the pre-set database for calorie intake/consumption of common food/sports.\n"
            + "update -f attr/ATTRIBUTE v/VALUE\n"
            + "  - edit the information of the user profile.\n"
            + "    [ATTRIBUTE can only be name/gender/age/address/height/weight/targetweight]\n"
            + "quit/exit/bye\n"
            + "  - exit the application.\n";
    public static final String LOOK_FOR_URL = "For detailed information regarding the usage of commands, "
            + "please refer to the user guide via\n";
    public static final String USERGUIDE_URL =
            "https://github.com/AY1920S2-CS2103-T09-4/main/blob/master/docs/UserGuide.adoc\n";
    public static final String URL_COPED = "(We have already copied the url into your clipboard,"
            + " press ctrl + v in your browser's url bar to visit the guide)";
    public static final String HELP_MESSAGE = LIST_OF_VALID_COMMANDS;
    public static final String SHOW_URL = LOOK_FOR_URL + USERGUIDE_URL + URL_COPED;
    private static final String FXML = "HelpWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    @FXML
    private AnchorPane helpPage;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label showUrl;

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        super(FXML);
        logger.info("Initializing Help Page");
        helpMessage.setText(HELP_MESSAGE);
        showUrl.setText(SHOW_URL);
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    public void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
