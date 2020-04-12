package fithelper.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.commons.util.EntriesUtil;
import fithelper.logic.Logic;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.Entry;
import fithelper.model.today.Today;
import fithelper.ui.calendar.CalendarPanel;
import fithelper.ui.calendar.DayCardWithStage;
import fithelper.ui.calendar.ListPanel;
import fithelper.ui.diary.DiaryPage;
import fithelper.ui.home.DashBoard;
import fithelper.ui.profile.ProfilePage;
import fithelper.ui.today.TodayPage;
import fithelper.ui.weight.WeightPage;

import javafx.collections.ObservableList;
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

    private DashBoard dashBoard;
    private TodayPage todayPage;
    private DiaryPage diaryPage;
    private ProfilePage profilePage;
    private WeightPage weightPage;
    private CalendarPanel calendarPanel;
    private HelpWindow helpWindow;
    private ListPanel listPanel;

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
    private Button dashBoardButton;
    @FXML
    private Button todayButton;
    @FXML
    private Button calendarButton;
    @FXML
    private Button diaryButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button weightButton;
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
        dashBoard = new DashBoard(logic.getFilteredFoodEntryList(), logic.getFilteredSportsEntryList(),
                logic.getFilteredReminderEntryList());
        setAllPageAnchor(dashBoard.getRoot());
        todayPage = new TodayPage(logic.getFilteredTodayFoodEntryList(),
                logic.getFilteredTodaySportsEntryList());
        logger.info(new Today().getTodayDateStr());
        setAllPageAnchor(todayPage.getRoot());
        diaryPage = new DiaryPage(logic.getFilteredDiaryList());
        setAllPageAnchor(diaryPage.getRoot());
        calendarPanel = new CalendarPanel(logic.getFilteredFoodEntryList(),
            logic.getFilteredSportsEntryList(), logic.getVEvents());
        listPanel = new ListPanel(logic.getFilteredFoodEntryList(), logic.getFilteredSportsEntryList());
        setAllPageAnchor(listPanel.getRoot());
        setAllPageAnchor(calendarPanel.getRoot());
        helpWindow = new HelpWindow();
        setAllPageAnchor(helpWindow.getRoot());
        profilePage = new ProfilePage(logic.getUserProfile());
        setAllPageAnchor(profilePage.getRoot());
        weightPage = new WeightPage(logic.getUserProfile(), logic.getFilteredWeightList());
        setAllPageAnchor(weightPage.getRoot());
        logger.fine("All pages filled in MainWindow");
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Handles the user inputs.
     */
    @FXML
    public void handleUserInput() throws IllegalValueException {
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
     * @param commandResult the result of executing a command
     */
    private void showPage(CommandResult commandResult) {
        CommandResult.DisplayedPage toDisplay = commandResult.getDisplayedPage();
        switch (toDisplay) {
        case HOME:
            showDashBoard();
            break;
        case TODAY:
            updateTodayPage();
            showTodayPage();
            break;
        case DIARY:
            showDiaryPage();
            break;
        case CALENDAR:
            showCalendarPanel();
            break;
        case HELP:
            showHelpPageAndCopyUrl();
            break;
        case PROFILE:
            updateProfilePage();
            showProfilePage();
            break;
        case WEIGHT:
            updateProfilePage();
            updateWeightPage();
            showWeightPage();
            break;
        default:
            break;
        }
    }

    @FXML
    public void handleShowHelpPage() {
        showHelpPageAndCopyUrl();
    }

    @FXML
    public void handleShowDashBoard() {
        showDashBoard();
    }

    @FXML
    public void handleShowTodayPage() {
        showTodayPage();
    }

    @FXML
    public void handleShowDiaryPage() {
        showDiaryPage();
    }

    @FXML
    public void handleShowCalendarPage() {
        showCalendarPanel();
    }

    @FXML
    public void handleShowProfilePage() {
        showProfilePage();
    }

    @FXML
    public void handleShowWeightPage() {
        showWeightPage();
    }

    private void showDashBoard() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(dashBoard.getRoot());
        currentPage.setText("Dash Board");
    }

    private void showTodayPage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(todayPage.getRoot());
        currentPage.setText("Today");
    }

    /**
     * Update content of today page with latest values.
     */
    private void updateTodayPage() {
        todayPage = new TodayPage(logic.getFilteredTodayFoodEntryList(),
                logic.getFilteredTodaySportsEntryList());
    }

    private void showDiaryPage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(diaryPage.getRoot());
        currentPage.setText("My Diary");
    }

    /**
     * Goes to the Help Page and copys the url of user guide to the clipboard.
     */
    private void showHelpPageAndCopyUrl() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(helpWindow.getRoot());
        currentPage.setText("Help");
        helpWindow.copyUrl();
    }

    /**
     * Shows the calendar page.
     */
    private void showCalendarPanel() {
        pagePane.getChildren().clear();
        if (logic.getCalendarShow() != null) {
            logger.info("Showing day page about this date.");
            ObservableList<Entry> entries = EntriesUtil.getEntries(logic.getFilteredFoodEntryList(),
                    logic.getFilteredSportsEntryList(), logic.getCalendarShow());
            DayCardWithStage temp = new DayCardWithStage(entries, logic.getCalendarShow());
            temp.getRoot().show();
            temp.getRoot().centerOnScreen();
            logic.setCalendarShow();
        }
        if ("tb".equals(logic.getCalendarMode())) {
            calendarPanel.updateScheduler();
            calendarPanel.set(logic.getCalendarDate());
            pagePane.getChildren().add(calendarPanel.getRoot());
        } else {
            listPanel.set(logic.getCalendarDate());
            pagePane.getChildren().add(listPanel.getRoot());
        }
        currentPage.setText("Calendar");
    }

    /**
     * Update content of profile page with latest values.
     */
    private void updateProfilePage() {
        profilePage = new ProfilePage(logic.getUserProfile());
    }

    /**
     * Shows the user profile page.
     */
    private void showProfilePage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(profilePage.getRoot());
        currentPage.setText("Profile");
    }

    /**
     * Update content of weight page with latest values.
     */
    private void updateWeightPage() {
        weightPage = new WeightPage(logic.getUserProfile(), logic.getFilteredWeightList());
    }

    /**
     * Shows the user weight records.
     */
    private void showWeightPage() {
        pagePane.getChildren().clear();
        pagePane.getChildren().add(weightPage.getRoot());
        currentPage.setText("Weight Records");
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
    public CommandResult executeCommand(String commandText) throws CommandException, IllegalValueException {
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
