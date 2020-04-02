package csdev.couponstash.ui;

import java.util.logging.Logger;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.logic.Logic;
import csdev.couponstash.logic.commands.CommandResult;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import javafx.fxml.FXML;
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

    // Independent Ui parts residing in this Ui container
    private CalendarResultDisplayPane calendarResultPane;
    private TabsPanel tabPanel;


    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane calendarPanePlaceholder;

    @FXML
    private StackPane calendarResultPlaceholder;

    @FXML
    private StackPane tabPanePlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        tabPanel = new TabsPanel(logic);
        tabPanePlaceholder.getChildren().add(tabPanel.getRoot());
        tabPanel.fillInnerParts();

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        calendarResultPane = new CalendarResultDisplayPane(logic);
        calendarResultPlaceholder.getChildren().add(calendarResultPane.getRoot());
        calendarResultPane.fillInnerParts();
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
     *
     * @see Logic#execute(String, CsTab)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {

        try {
            CsTab currentSelectedTab = tabPanel.selectedTab();
            CommandResult commandResult = logic.execute(commandText, currentSelectedTab);

            if (!currentSelectedTab.equals(CsTab.COUPONS)) {
                tabPanel.selectTab(CsTab.COUPONS);
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());
            calendarResultPane.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            calendarResultPane.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
