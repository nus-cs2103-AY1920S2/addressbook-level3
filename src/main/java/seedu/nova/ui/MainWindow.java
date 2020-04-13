package seedu.nova.ui;

import static seedu.nova.commons.core.HelpMessages.HELP_ADDRESS_BOOK;
import static seedu.nova.commons.core.HelpMessages.HELP_HOME;
import static seedu.nova.commons.core.HelpMessages.HELP_PROGRESS_TRACKER;
import static seedu.nova.commons.core.HelpMessages.HELP_SCHEDULE;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.nova.commons.core.GuiSettings;
import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.Logic;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.logic.parser.ModeEnum;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.Mode;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpBox helpBox;

    //private HelpWindow helpWindow;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private VBox resultDisplayPlaceholder;

    @FXML
    private VBox helpHolder;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.helpBox = new HelpBox();

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        // Autoscroll to bottom
        resultDisplayPlaceholder.heightProperty().addListener((
                observable, oldValue, newValue) -> scrollPane.setVvalue(scrollPane.getVmax()));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        helpHolder.getChildren().add(helpBox.getRoot());

        helpBox.setHelp(HELP_HOME);

        //add schedule for the day in homepage
        try {
            //get localdate of today
            String today = LocalDate.now().toString();

            //set mode to schedule first
            Mode mode = logic.getMode();
            logic.setMode(mode, ModeEnum.SCHEDULE);

            executeCommand("view t\\" + today);

            //set mode back to home
            logic.setMode(mode, ModeEnum.HOME);
        } catch (CommandException | ParseException e) {
            //do noting
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }


    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            displayToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isChangeMode()) {
                Mode mode = logic.getMode();
                ModeEnum modeEnum = logic.getModeEnum(mode);

                switch (modeEnum) {
                case HOME:
                    helpBox.setHelp(HELP_HOME);
                    break;
                case ADDRESSBOOK:
                    helpBox.setHelp(HELP_ADDRESS_BOOK);
                    break;
                case SCHEDULE:
                    helpBox.setHelp(HELP_SCHEDULE);
                    break;
                case PROGRESSTRACKER:
                    helpBox.setHelp(HELP_PROGRESS_TRACKER);
                    break;
                default:
                    logger.info("Invalid mode: " + logic.getModeName(modeEnum));
                }

            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            displayToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Create a box in the scrollpane to display the result
     * @param s the result string
     */
    private void displayToUser(String s) {
        ResultDisplay r = new ResultDisplay(scrollPane.widthProperty());
        r.setFeedbackToUser(s);
        resultDisplayPlaceholder.getChildren().add(r.getRoot());
    }
}
