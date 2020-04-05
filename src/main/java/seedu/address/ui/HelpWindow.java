package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE =
            "This is the comprehensive list of Commands and and help on how to use them. Enjoy! :)" + "\n" + "\n"
            + "\u2022 Adding Student: add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]"
            + "nok/NAME-RELATIONSHIP-PHONE-NUMBER " + "temp/XX.X " + "att/PRESENCE" + "\n" + "\n"
            + "Example. add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend "
            + "t/colleague nok/jon-father-87654321 temp/36.2 att/Present" + "\n" + "\n"
            + "\u2022 Clear : clear" + "\n" + "\n"
            + "\u2022 Delete Student: delete INDEX" + "\n" + "\n"
            + "Example: delete 3" + "\n" + "\n"
            + "\u2022 Edit Student: edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]"
            + "[temp/TEMPERATURE] [att/ATTENDANCE]" + "\n" + "\n"
            + "Example: edit 2 n/James Lee e/jameslee@example.com" + "\n" + "\n"
            + "\u2022 Listing out all the students : list" + "\n" + "\n"
            + "\u2022 Finding keywords: find KEYWORD(S)" + "\n" + "\n"
            + "Example: find James Jake" + "\n" + "\n"
            + "Academics\n"
            + "\u2022 Display assessments: academics" + "\n"
            + "\u2022 Filter assessment type: academics TYPE" + "\n"
            + "Example. academics homework" + "\n"
            + "\u2022 Display academic statistics: academics statistics" + "\n"
            + "\u2022 Add new assessment: academics add desc/DESCRIPTION type/TYPE date/DATE" + "\n"
            + "\u2022 Delete assessment: academics delete INDEX" + "\n"
            + "\u2022 Submit students' work: academics submit ASSESSMENT_INDEX [stu/STUDENT_NAME]" + "\n"
            + "\u2022 Mark students' work: academics mark ASSESSMENT_INDEX [stu/STUDENT_NAME-SCORE]" + "\n" + "\n"
            + "\u2022 Help Display : help" + "\n" + "\n"
            + "\u2022 Display Personal Schedule : schedule" + "\n" + "\n"
            + "\u2022 Adding events to your Personal Schedule:"
            + " schedule eventName/[EVENTNAME] startDateTime/[STARTDATETIME] "
            + "endDateTime/[ENDDATETIME] recur/[DAILY/WEEKLY/NONE] color/[0-23]" + "\n" + "\n"
            + "Example: schedule eventName/Consultation startDateTime/2020-03-30T08:00"
            + " endDateTime/2020-03-30T10:00 recur/none color/3" + "\n" + "\n"
            + "\u2022 Display detailed student list : detailed" + "\n" + "\n"
            + "\u2022 Displays the administrative content of the student list: admin" + "\n" + "\n"
            + "\u2022 Display default student list : default" + "\n" + "\n"
            + "\u2022 Adding Notes: notesa n/NAME c/CONTENT"
            + "\u2022 Delete Notes: notesd INDEX"
            + "\u2022 Filter Notes: notesf KEYWORD(S)"
            + "\u2022 Export Notes: notese";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";


    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
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

}
