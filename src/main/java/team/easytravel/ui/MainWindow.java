package team.easytravel.ui;

import static team.easytravel.ui.SideTabsBar.STYLE_BUTTON_SELECTED;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team.easytravel.commons.core.GuiSettings;
import team.easytravel.commons.core.LogsCenter;
import team.easytravel.logic.Logic;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.ui.accommodationtab.AccommodationBookingTabPanel;
import team.easytravel.ui.activitiestab.ActivityTabPanel;
import team.easytravel.ui.expensestab.FixedExpenseTabPanel;
import team.easytravel.ui.help.HelpTabPanel;
import team.easytravel.ui.packinglisttab.ListPresetWindow;
import team.easytravel.ui.packinglisttab.PackingListTabPanel;
import team.easytravel.ui.scheduletab.ScheduleTabPanel;
import team.easytravel.ui.transportationtab.TransportBookingTabPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private ResultDisplay resultDisplay;
    private StatusWindow statusWindow;
    private ListPresetWindow listPresetWindow;
    private SideTabsBar sideTabsBar;

    // Individual list panels
    private TabPanel scheduleTabPanel;
    private TabPanel activityTabPanel;
    private TabPanel accommodationBookingTabPanel;
    private TabPanel transportBookingTabPanel;
    private TabPanel packingListTabPanel;
    private TabPanel fixedExpenseTabPanel;
    private TabPanel helpTabPanel;

    @FXML
    private StackPane sideTabsBarPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane tabPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane dashboardPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        statusWindow = new StatusWindow();
        listPresetWindow = new ListPresetWindow();
        sideTabsBar = new SideTabsBar(tabName -> clearFunction -> button -> switchTab(tabName, clearFunction, button));
        helpTabPanel = new HelpTabPanel(logic.getHelpQuestions());
        // Set up the list panels
        if (logic.hasTrip()) {
            handleSetTrip();
        } else {
            handleDeleteTrip();
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        // Independent Ui parts residing in this Ui container
        sideTabsBarPlaceholder.getChildren().add(sideTabsBar.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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

    /**
     * Sets up the respective tabs when trip is set.
     */
    public void handleSetTrip() {
        scheduleTabPanel = new ScheduleTabPanel(logic.getScheduleList());
        activityTabPanel = new ActivityTabPanel(logic.getFilteredActivityList());
        accommodationBookingTabPanel = new AccommodationBookingTabPanel(logic.getFilteredAccommodationBookingList());
        transportBookingTabPanel = new TransportBookingTabPanel(logic.getFilteredTransportBookingList());
        packingListTabPanel = new PackingListTabPanel(logic.getFilteredPackingList());
        fixedExpenseTabPanel = new FixedExpenseTabPanel(logic.getFilteredFixedExpenseList(),
                logic.getGuiSettings().getWindowHeight(), logic.getGuiSettings().getWindowWidth());

        sideTabsBar.handleSwitchToScheduleTab();
        handleUpdateTrip(true);
    }

    /**
     * Tears down the respective tabs when trip is deleted.
     */
    public void handleDeleteTrip() {
        scheduleTabPanel = new NoTripTabPanel();
        activityTabPanel = new NoTripTabPanel();
        accommodationBookingTabPanel = new NoTripTabPanel();
        transportBookingTabPanel = new NoTripTabPanel();
        packingListTabPanel = new NoTripTabPanel();
        fixedExpenseTabPanel = new NoTripTabPanel();
        sideTabsBar.handleSwitchToScheduleTab();
        handleUpdateTrip(false);

    }

    /**
     * Handles the UI when trip is updated.
     */
    public void handleUpdateTrip(boolean hasTrip) {
        dashboardPlaceholder.getChildren().clear();
        if (hasTrip) {
            dashboardPlaceholder.getChildren().add(new DashboardPanel(logic.getTrip()).getRoot());
        } else {
            dashboardPlaceholder.getChildren().add(new DashboardPanel().getRoot());
        }
    }

    /**
     * Handle list preset.
     */
    public void handleListPreset() {
        if (!listPresetWindow.isShowing()) {
            listPresetWindow.show();
        } else {
            listPresetWindow.focus();
        }
    }

    /**
     * Opens the checkstatus window or focuses on it if it's already opened.
     */
    public void handleShowStatus(List<String> status) {
        if (statusWindow.isShowing()) {
            statusWindow.close();
        }
        statusWindow.show(status);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        if (statusWindow.isShowing()) {
            statusWindow.close();
        }

        if (listPresetWindow.isShowing()) {
            listPresetWindow.close();
        }

        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Switches tab to the specified tab name.
     */
    private void switchTab(String tabName, Runnable clearFormat, Button buttonToFormat) {
        clearFormat.run();
        buttonToFormat.getStyleClass().add(STYLE_BUTTON_SELECTED);
        tabPanelPlaceholder.getChildren().clear();
        switch (tabName) {
        case ScheduleTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(scheduleTabPanel.getRoot());
            break;
        case ActivityTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(activityTabPanel.getRoot());
            break;
        case AccommodationBookingTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(accommodationBookingTabPanel.getRoot());
            break;
        case TransportBookingTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(transportBookingTabPanel.getRoot());
            break;
        case PackingListTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(packingListTabPanel.getRoot());
            break;
        case FixedExpenseTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(fixedExpenseTabPanel.getRoot());
            break;

        case HelpTabPanel.TAB_NAME:
            tabPanelPlaceholder.getChildren().add(helpTabPanel.getRoot());
            break;

        default:
            throw new AssertionError("No such tab name: " + tabName);
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                sideTabsBar.handleSwitchToHelpTab();
            }

            if (commandResult.isCheckStatus()) {
                handleShowStatus(commandResult.getStatusFeedback());
            }

            if (commandResult.isListPreset()) {
                handleListPreset();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isSetTrip()) {
                handleSetTrip();
            }

            if (commandResult.isDeleteTrip()) {
                handleDeleteTrip();
            }

            if (commandResult.isEditTrip()) {
                handleUpdateTrip(true);
            }

            if (commandResult.isActivity()) {
                sideTabsBar.handleSwitchToActivitiesTab();
            }

            if (commandResult.isTransportation()) {
                sideTabsBar.handleSwitchToTransportationTab();
            }

            if (commandResult.isAccommodation()) {
                sideTabsBar.handleSwitchToAccommodationTab();
            }

            if (commandResult.isPackingList()) {
                sideTabsBar.handleSwitchToPackingListTab();
            }

            if (commandResult.isFixedExpense()) {
                sideTabsBar.handleSwitchToFixedExpensesTab();
            }

            if (commandResult.isSchedule()) {
                sideTabsBar.handleSwitchToScheduleTab();
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
