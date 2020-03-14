package fithelper.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.Logic;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.ui.calendar.CalendarPage;
import fithelper.ui.calendar.FullCalendar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static final String FXML = "MainWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Stage primaryStage;

    private Logic logic;

    private List<String> inputHistory = new ArrayList<>();
    //private int historyIndex = inputHistory.size();

    // Independent Ui parts residing in this Ui container
    private FoodListPanel foodListPanel;
    private SportsListPanel sportsListPanel;

    private TodayPage todayPage;
    /*private ReportPage reportPage;
    private ProfilePage profilePage;*/
    private CalendarPage calendarPage;
    private FullCalendar fullCalendar;
    private HelpWindow helpWindow;

    @FXML
    private StackPane foodListPanelPlaceholder;

    @FXML
    private StackPane sportsListPanelPlaceholder;

    //Main page
    @FXML
    private Label currentPage;
    @FXML
    private AnchorPane pagePane;
    @FXML
    private Label result;
    @FXML
    private TextField userInput;

    //Sidebar
    @FXML
    private Button todayButton;
    @FXML
    private Button calendarButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button helpButton;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        logger.info("Initializing MainWindow");
        this.primaryStage = primaryStage;
        this.logic = logic;
        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        todayPage = new TodayPage(logic.getFilteredFoodEntryList(), logic.getFilteredSportsEntryList());
        setAllPageAnchor(todayPage.getRoot());
        calendarPage = new CalendarPage(logic.getFilteredFoodEntryList(), logic.getFilteredSportsEntryList());
        setAllPageAnchor(calendarPage.getRoot());
        fullCalendar = new FullCalendar(logic.getFilteredFoodEntryList(), logic.getFilteredSportsEntryList());
        //setAllPageAnchor(fullCalendar.getRoot());

        logger.fine("All pages filled in MainWindow");
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Handles the user inputs.
     */
    @FXML
    public void handleUserInput() {
        String input = userInput.getText();

        inputHistory.add(input);
        //historyIndex = inputHistory.size();

        try {
            CommandResult commandResult = logic.execute(input);
            showPage(commandResult);

            showResultMessage(commandResult.getFeedbackToUser());
        } catch (CommandException | ParseException | IllegalArgumentException e) {
            showResultMessage(e.getMessage());
        }
        userInput.clear();
    }

    /**
     * Shows the page of FitHelper according to the command keyword.
     * @param commandResult the result ofe executing a command
     */
    private void showPage(CommandResult commandResult) {
        CommandResult.DisplayedPage toDisplay = commandResult.getDisplayedPage();
        switch (toDisplay) {
        case TODAY:
            showTodayPage();
            break;
        case CALENDAR:
            showCalendarPage();
            break;
        default:
            break;
        }
    }

    @FXML
    public void handleShowTodayPage() {
        showTodayPage();
    }

    @FXML
    public void handleShowCalendarPage() {
        showCalendarPage();
    }

    private void showTodayPage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(todayPage.getRoot());
        currentPage.setText("Today");
    }

    /**
     * Shows the calendar page.
     */
    private void showCalendarPage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(calendarPage.getRoot());
        currentPage.setText("Calendar");
    }

    private void showResultMessage(String message) {
        result.setText(message);
    }

    private void setAllPageAnchor(AnchorPane... pages) {
        for (AnchorPane page : pages) {
            AnchorPane.setLeftAnchor(page, 0.0);
            AnchorPane.setRightAnchor(page, 0.0);
            AnchorPane.setTopAnchor(page, 0.0);
            AnchorPane.setBottomAnchor(page, 4.0);
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    public FoodListPanel getFoodListPanel() {
        return foodListPanel;
    }

    public SportsListPanel getSportsListPanel() {
        return sportsListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see fithelper.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            throw e;
        }
    }
}
