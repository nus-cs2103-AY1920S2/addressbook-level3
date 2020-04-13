//@@author fatin99

package tatracker.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import tatracker.commons.core.GuiSettings;
import tatracker.commons.core.LogsCenter;
import tatracker.logic.Logic;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.commands.statistic.StatisticCommandResult;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.statistic.Statistic;
import tatracker.ui.claimstab.ClaimsListPanel;
import tatracker.ui.claimstab.ModuleListPanelCopy;
import tatracker.ui.sessiontab.SessionListPanel;
import tatracker.ui.studenttab.GroupListPanel;
import tatracker.ui.studenttab.ModuleListPanel;
import tatracker.ui.studenttab.StudentListPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String BORDER_COLOUR = "#917b3e";
    private static final String BORDER_WIDTH = "1";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private CommandBox commandBox;

    // Independent Ui parts residing in this Ui container
    private StudentListPanel studentListPanel;
    private GroupListPanel groupListPanel;
    private ModuleListPanel moduleListPanel;

    private ModuleListPanelCopy moduleListPanelCopy;
    private Focusable currentStudentViewList;

    private SessionListPanel sessionListPanel;
    private ClaimsListPanel claimsListPanel;

    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private StatisticWindow statisticWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab studentListTab;

    @FXML
    private Tab sessionListTab;

    @FXML
    private Tab claimsListTab;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane groupListPanelPlaceholder;

    @FXML
    private StackPane moduleListPanelPlaceholder;

    @FXML
    private StackPane moduleListPanelPlaceholderCopy;

    @FXML
    private StackPane sessionListPanelPlaceholder;

    @FXML
    private StackPane claimsListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        studentListTab.setStyle("-fx-border-color: " + BORDER_COLOUR + "; "
                + "-fx-border-width: " + BORDER_WIDTH + ";");

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        groupListPanel = new GroupListPanel(logic.getFilteredGroupList());
        groupListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

        moduleListPanelCopy = new ModuleListPanelCopy(logic.getFilteredModuleList());
        moduleListPanelPlaceholderCopy.getChildren().add(moduleListPanelCopy.getRoot());

        currentStudentViewList = studentListPanel;

        sessionListPanel = new SessionListPanel(logic.getFilteredSessionList());
        sessionListPanelPlaceholder.getChildren().add(sessionListPanel.getRoot());

        claimsListPanel = new ClaimsListPanel(logic.getFilteredDoneSessionList(), logic.getTaTracker());
        claimsListPanelPlaceholder.getChildren().add(claimsListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTaTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand, resultDisplay);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        getRoot().addEventFilter(KeyEvent.KEY_RELEASED, this::handleFocusOnCommandBox);
        getRoot().addEventFilter(KeyEvent.KEY_RELEASED, this::handleFocusOnView);
        getRoot().addEventFilter(KeyEvent.KEY_RELEASED, this::handleSwitchingStudentViewLists);
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
     * Switched to user specified tab.
     */
    @FXML
    public void handleGoto(Tab tabToSwitchTo) {
        tabPane.getSelectionModel().select(tabToSwitchTo);
        studentListTab.setStyle("-fx-border-color: " + "black" + "; "
                                + "-fx-border-width: " + "0" + ";");
        sessionListTab.setStyle("-fx-border-color: " + "black" + "; "
                                + "-fx-border-width: " + "0" + ";");
        claimsListTab.setStyle("-fx-border-color: " + "black" + "; "
                                + "-fx-border-width: " + "0" + ";");
        tabToSwitchTo.setStyle("-fx-border-color: " + BORDER_COLOUR + "; "
                            + "-fx-border-width: " + BORDER_WIDTH + ";");
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

//@@author Eclmist

    /**
     * Opens the statistic window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStatistic() {
        handleStatistic(null);
    }

    /**
     * Opens the statistic window for the input module.
     * @param moduleCode the module code for which the stats will be for
     */
    public void handleStatistic(String moduleCode) {
        if (statisticWindow != null && statisticWindow.isShowing()) {
            statisticWindow.hide();
        }

        // Create a new statistic window
        statisticWindow = new StatisticWindow(new Statistic(logic.getTaTracker(), moduleCode));
        statisticWindow.show();
        statisticWindow.focus();
    }

    //@@author fatin99

    void show() {
        primaryStage.show();
    }

    private boolean isSelectedTab(Tab tab) {
        return tab.equals(tabPane.getSelectionModel().getSelectedItem());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();

        if (statisticWindow != null) {
            statisticWindow.hide();
        }
    }

    //@@author PotatoCombat

    /**
     * Alternates the focus on the command box.
     */
    private void handleFocusOnCommandBox(KeyEvent event) {
        if (!KeyCode.ESCAPE.equals(event.getCode())) {
            return;
        }
        if (commandBox.isFocused()) {
            commandBoxPlaceholder.requestFocus();
            logger.info("Focus on view");
        } else {
            commandBox.requestFocus();
            logger.info("Focus on text");
        }
    }

    /**
     * Alternates the focus on the current tab view.
     */
    private void handleFocusOnView(KeyEvent event) {
        if (!KeyCode.ESCAPE.equals(event.getCode()) || commandBox.isFocused()) {
            return;
        }
        if (isSelectedTab(studentListTab)) {
            currentStudentViewList.requestFocus();
        } else if (isSelectedTab(sessionListTab)) {
            sessionListPanel.requestFocus();
        } else if (isSelectedTab(claimsListTab)) {
            moduleListPanelCopy.requestFocus();
        } else {
            assert false;
            logger.warning("Tab does not exist");
        }
    }

    /**
     * Alternates the focus on the module, group, and student list in the StudentView.
     */
    private void handleSwitchingStudentViewLists(KeyEvent event) {
        if (!isSelectedTab(studentListTab) || commandBox.isFocused()) {
            return;
        }
        switch (event.getCode()) {

        case LEFT:
            handleLeftKeyReleased();
            break;
        case RIGHT:
            handleRightKeyReleased();
            break;
        default:
            logger.fine("Not switching lists");
            break;
        }
    }

    /**
     * Sets the focus on the list view to the left of the currently active list view.
     * This can only be used in the Student View since it has multiple lists.
     */
    private void handleLeftKeyReleased() {
        if (currentStudentViewList.equals(studentListPanel)) {
            logger.info("LEFT: Showing groups");
            currentStudentViewList = groupListPanel;

        } else if (currentStudentViewList.equals(groupListPanel)) {
            logger.info("LEFT: Showing modules");
            currentStudentViewList = moduleListPanel;

        } else {
            assert currentStudentViewList.equals(moduleListPanel);
            logger.fine("Nothing to the left of module list panel");
        }
        currentStudentViewList.requestFocus();
    }

    /**
     * Sets the focus on the list view to the right of the currently active list view.
     * This can only be used in the Student View since it has multiple lists.
     */
    private void handleRightKeyReleased() {
        if (currentStudentViewList.equals(moduleListPanel)) {
            logger.info("RIGHT: Showing groups");
            currentStudentViewList = groupListPanel;

        } else if (currentStudentViewList.equals(groupListPanel)) {
            logger.info("RIGHT: Showing students");
            currentStudentViewList = studentListPanel;

        } else {
            assert currentStudentViewList.equals(studentListPanel);
            logger.fine("Nothing to the right of student list panel");
        }
        currentStudentViewList.requestFocus();
    }

    //@@author fatin99

    /**
     * Executes the command and returns the result.
     *
     * @see tatracker.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult instanceof StatisticCommandResult) {
                StatisticCommandResult scr = (StatisticCommandResult) commandResult;
                handleStatistic(scr.targetModuleCode);
            }

            switch (commandResult.getNextAction()) {
            case DONE:
                claimsListPanel.updateLabel();
                handleGoto(claimsListTab);
                break;

            case EXIT:
                handleExit();
                break;

            case GOTO_CLAIMS:
            case FILTER_CLAIMS:
                moduleListPanelCopy.updateCells(logic.getFilteredModuleList());
                claimsListPanel.updateLabel();
                handleGoto(claimsListTab);
                break;

            case FILTER_SESSION:
                sessionListPanel.updateLabel(
                        logic.getCurrSessionDateFilter(),
                        logic.getCurrSessionModuleFilter(),
                        logic.getCurrSessionTypeFilter());
                handleGoto(sessionListTab);
                break;

            case FILTER_STUDENT:
                moduleListPanel.updateCells(logic.getFilteredModuleList());
                groupListPanel.updateCells(logic.getFilteredGroupList());
                handleGoto(studentListTab);
                break;

            case GOTO_SESSION:
                handleGoto(sessionListTab);
                break;

            case GOTO_STUDENT:
                moduleListPanel.updateCells(logic.getFilteredModuleList());
                moduleListPanelCopy.updateCells(logic.getFilteredModuleList());
                groupListPanel.updateCells(logic.getFilteredGroupList());
                handleGoto(studentListTab);
                break;

            case HELP:
                handleHelp();
                break;

            case LIST:
                claimsListPanel.updateLabel();
                moduleListPanelCopy.updateCells(logic.getFilteredModuleList());
                sessionListPanel.updateLabel(
                        logic.getCurrSessionDateFilter(),
                        logic.getCurrSessionModuleFilter(),
                        logic.getCurrSessionTypeFilter());
                break;

            default:
                break;
            }
            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
