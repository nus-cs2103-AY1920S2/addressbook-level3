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
            + "1) [prefix/ATTRIBUTE] means an optional attribute for this command,\n"
            + "   while prefix/ATTRIBUTE means a compulsory attribute.\n"
            + "2) TYPE can only be food(f) OR sports(s).\n"
            + "3) DATE follows the format of YYYY-MM-DD.\n"
            + "4) TIME follows the format of YYYY-MM-DD-24:60.\n"
            + "5) INDEX is one-based.\n"
            + "\n"
            + "FitHelper currently supports the following command:\n"
            + "add x/TYPE n/NAME t/TIME l/LOCATION c/CALORIE\n"
            + "  - Add a food/sports entry.\n"
            + "list [d/DATE]\n"
            + "  - List food and sports entries (of a given day).\n"
            + "reminder\n"
            + "  - Show entries in the reminder list only.\n"
            + "edit x/TYPE [d/DATE] i/INDEX s/Done OR Undone\n"
            + "  - Mark an entry as done/not done.\n"
            + "edit x/TYPE i/INDEX [n/NAME] [t/DATE-24:60] [l/LOCATION] [c/CALORIE] [r/REMARK]\n"
            + "  - Edit the information of an entry.\n"
            + "delete x/type [d/DATE] i/INDEX\n"
            + "  - Delete an entry.\n"
            + "home (i.e. dashboard) /today/calendar/profile/weight/diary/help\n"
            + "  - Go to the specified page.\n"
            + "find [x/TYPE] k/KEYWORDS\n"
            + "  - Show all (food/sports) entries (whose description contains any of the keywords).\n"
            + "addDiary d/DATE dc/CONTENT\n"
            + "  - Add a diary for a specified date.\n"
            + "appendDiary d/DATE dc/APPENDED_CONTENT\n"
            + "  - Append new content to the diary of a specified date.\n"
            + "editDiary d/DATE dc/NEW_CONTENT\n"
            + "  - Rewrite the diary of a specified date.\n"
            + "findDiary [d/DATE] [k/KEYWORDS]\n"
            + "  - List all diaries, diary of a given date or those containing the keywords.\n"
            + "deleteDiary d/DATE\n"
            + "  - Delete diary of the specified date.\n"
            + "clearDiary\n"
            + "  - Delete all diaries.\n"
            + "clear\n"
            + "  - Clear all entries and diaries.\n"
            + "undo\n"
            + "  - Revokes the last undoable command.\n"
            + "redo\n"
            + "  - Re-execute the last undoable command that has been undone.\n"
            //+ "table x/TYPE DATE\n"
            //+ "  - show calorie table of a specified date.\n"
            + "calendar m/ls [d/DATE]\n"
            + "  - Calendar list mode, showing entries in the current month,\n"
            + "    or the month of the date specified by the user.\n"
            + "calendar m/tb [d/DATE]\n"
            + "  - Calendar timetable mode, showing entries in the current week,\n"
            + "    or the week of the date specified by the user.\n"
            + "calendar sh/DATE\n"
            + "  - Display all entries of the specified date in a pop up window.\n"
            + "check x/TYPE k/KEYWORDS\n"
            + "  - Search the pre-set database for calorie intake/consumption of common food/sports.\n"
            + "update -f attr/ATTRIBUTE v/VALUE\n"
            + "  - Edit the information of the user profile.\n"
            + "    [ATTRIBUTE can only be name/gender/age/address/height/weight/targetweight]\n"
            + "addWeight v/WEIGHT_VALUE [d/DATE]\n"
            + "  - Add a new weight record into the weight records database.\n"
            + "editWeight [d/DATE] v/NEW_WEIGHT_VALUE\n"
            + "  - Edit an existing weight record in the database.\n"
            + "deleteWeight [d/DATE]\n"
            + "  - Delete an existing weight record in the database.\n"
            + "clearWeight\n"
            + "  - Clear all weight records.\n"
            + "quit/exit/bye\n"
            + "  - Exit the application.\n";
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
