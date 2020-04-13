package seedu.planner.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.Logic;
import seedu.planner.logic.commands.CommandResult;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.module.Module;

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
    private ResultDisplay resultDisplay;
    private CalendarBox calendarBox;
    private HelpWindow helpWindow;
    private GradWindow gradWindow;
    private int colorTrack = 1;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem changeColor;

    @FXML
    private StackPane calendarBoxPlaceholder;

    @FXML
    private StackPane gradPlaceholder;

    @FXML
    private Button lanchCalendar;

    @FXML
    private StackPane moduleListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
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
        gradWindow = new GradWindow(logic.getPlanner());
        gradWindow.changeColor(0);
        gradPlaceholder.getChildren().add(gradWindow.getRoot());

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getPlannerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

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

    public ModuleListPanel getModuleListPanel() {
        return moduleListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.planner.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            gradWindow.setGrad(logic.getPlanner());
            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Changes color between light theme and dark theme.
     */
    @FXML
    public void changeColor() {
        if (colorTrack == 1) {
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/view/LightTheme.css")
                    .toExternalForm());
            gradWindow.changeColor(1);
            colorTrack = 0;
        } else {
            primaryStage.getScene().getStylesheets().remove(getClass().getResource("/view/LightTheme.css")
                    .toExternalForm());
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/view/DarkTheme.css")
                    .toExternalForm());
            gradWindow.changeColor(0);
            colorTrack = 1;
        }
    }

    /**
     * Launches the Calendar in a separate window.
     */
    @FXML
    private void launchCalendar() {
        calendarBox = new CalendarBox(logic.getPlanner());
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(calendarBox.getRoot());
        Scene secondScene = new Scene(secondaryLayout, 1360, 400);
        secondScene.getStylesheets().add(getClass().getResource("/view/DarkTheme.css").toExternalForm());
        Stage newWindow = new Stage();
        newWindow.setTitle("Calendar");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    /**
     * Searches Module based on the textbox.
     */
    @FXML
    private void searchMod() {
        String searchCriteria = searchBox.getText();
        ObservableList<Module> tempSearch = logic.getFilteredModuleList();
        if (!searchCriteria.isEmpty()) {
            tempSearch = tempSearch.stream().filter(mod -> mod.getModuleCode().value.contains(searchCriteria)
                    || mod.getModuleTitle().contains(searchCriteria))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        moduleListPanel.setSearch(tempSearch);
    }
}
