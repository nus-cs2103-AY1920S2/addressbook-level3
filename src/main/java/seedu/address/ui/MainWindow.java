package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.ui.calendarui.CalendarView;
import seedu.address.ui.facilitatorui.FacilitatorListPanel;
import seedu.address.ui.facilitatorui.FacilitatorPanel;
import seedu.address.ui.lessonui.LessonPanel;
import seedu.address.ui.moduleui.ModuleDetailsPanel;
import seedu.address.ui.moduleui.ModuleListPanel;
import seedu.address.ui.taskui.TaskListPanel;
import seedu.address.ui.taskui.TaskPanel;

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
    private ModuleListPanel moduleListPanel;
    private FacilitatorListPanel facilitatorListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ModuleDetailsPanel moduleDetailsPanel;
    private LessonPanel lessonPanel;
    private TaskPanel taskPanel;
    private FacilitatorPanel facilitatorPanel;
    private CalendarView calendarView;
    private TaskListPanel taskListPanel;
    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane moduleListPanelPlaceholder;

    @FXML
    private StackPane facilitatorListPanelPlaceholder;

    @FXML
    private StackPane calendarViewPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab module;

    @FXML
    private Tab facilitator;

    @FXML
    private Tab calendar;

    @FXML
    private Tab task;

    @FXML
    private StackPane moduleDetailsPlaceholder;

    @FXML
    private StackPane lessonPanelPlaceholder;

    @FXML
    private StackPane taskPanelPlaceholder;

    @FXML
    private StackPane facilitatorPanelPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        // Set the minimum width that user can make it smaller to
        primaryStage.setMinWidth(1132);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
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
        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

        facilitatorListPanel = new FacilitatorListPanel(logic.getFilteredFacilitatorList());
        facilitatorListPanelPlaceholder.getChildren().add(facilitatorListPanel.getRoot());

        calendarView = new CalendarView(logic.getCalendar(), logic.getFilteredTaskList(), logic.getLessons());
        calendarViewPlaceholder.getChildren().add(calendarView.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getModManagerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        moduleDetailsPanel = new ModuleDetailsPanel();
        moduleDetailsPlaceholder.getChildren().add(moduleDetailsPanel.getRoot());
        moduleDetailsPlaceholder.setStyle("-fx-background-insets: 3px, 0px;  -fx-background-radius: 15px; "
                + "-fx-border-color: transparent;");

        lessonPanel = new LessonPanel();
        lessonPanelPlaceholder.getChildren().add(lessonPanel.getRoot());
        lessonPanelPlaceholder.setStyle("-fx-border-color: transparent; -fx-effect: null;");

        taskPanel = new TaskPanel(logic.getTaskListForModule());
        taskPanelPlaceholder.getChildren().add(taskPanel.getRoot());
        taskPanelPlaceholder.setStyle("-fx-border-color: transparent; -fx-effect: null;");

        facilitatorPanel = new FacilitatorPanel(logic.getFacilitatorListForModule());
        facilitatorPanelPlaceholder.getChildren().add(facilitatorPanel.getRoot());
        facilitatorPanelPlaceholder.setStyle("-fx-border-color: transparent; -fx-effect: null;");

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        commandBox = new CommandBox(this::executeCommand);
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
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Switches to module view
     */
    public void handleSwitchToModule() {
        mainTabPane.getSelectionModel().select(module);
        moduleDetailsPanel.changeDisplayModule(logic.getModule());
    }

    /**
     * Switches to facilitator view
     */
    public void handleSwitchToFacilitator() {
        mainTabPane.getSelectionModel().select(facilitator);
    }

    /**
     * Switches to calendar view
     */
    public void handleSwitchToCalendar() {
        mainTabPane.getSelectionModel().select(calendar);
    }

    /**
     * Switches to task view
     */
    public void handleSwitchToTask() {
        mainTabPane.getSelectionModel().select(task);
    }

    /**
     * Changes the calendar view to the specified week.
     *
     * @param week The week to be viewed
     */
    public void viewCalendar(Calendar week) {
        calendarView = new CalendarView(week, logic.getFilteredTaskList(), logic.getLessons());
        calendarViewPlaceholder.getChildren().clear();
        calendarViewPlaceholder.getChildren().add(calendarView.getRoot());
    }

    /**
     * Changes the module view to the specified module.
     * @param module The module to be viewed.
     */
    public void refreshModuleTab(Optional<Module> module) {
        if (module.isEmpty()) {
            lessonPanel = new LessonPanel();
            lessonPanelPlaceholder.getChildren().clear();
            lessonPanelPlaceholder.getChildren().add(lessonPanel.getRoot());
            return;
        }
        ModuleCode moduleCode = module.get().getModuleCode();
        lessonPanel = new LessonPanel(logic.getLessonListForModule(moduleCode));
        lessonPanelPlaceholder.getChildren().clear();
        lessonPanelPlaceholder.getChildren().add(lessonPanel.getRoot());
    }

    public ModuleListPanel getModuleListPanel() {
        return moduleListPanel;
    }

    public FacilitatorListPanel getFacilitatorListPanel() {
        return facilitatorListPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            viewCalendar(logic.getCalendar());
            switch (commandResult.getType()) {
            case CLEAR:
            case MODULE:
            case LESSON:
                handleSwitchToModule();
                refreshModuleTab(logic.getModule());
                break;
            case TASK:
                handleSwitchToTask();
                break;
            case FACILITATOR:
                handleSwitchToFacilitator();
                break;
            case CALENDAR:
                handleSwitchToCalendar();
                viewCalendar(logic.getCalendar());
                break;
            case HELP:
                handleHelp();
                break;
            case EXIT:
                handleExit();
                break;
            case UNDO:
            case REDO:
                moduleDetailsPanel.changeDisplayModule(logic.getModule());
                refreshModuleTab(logic.getModule());
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
