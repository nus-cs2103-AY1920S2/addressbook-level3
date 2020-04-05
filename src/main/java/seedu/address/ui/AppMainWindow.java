package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.AppLogic;
import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class AppMainWindow extends UiPart<Stage> {

    private static final String FXML = "AppMainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private AppLogic logic;
    private BluetoothPingPanel bluetoothPingPanel;
    private BluetoothPingSummaryPanel bluetoothPingSummaryPanel;
    private PersonSummaryPanel personSummaryPanel;
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane bluetoothPingPanelPlaceholder;

    private void renderToPanel() {
        this.bluetoothPingPanelPlaceholder.getChildren().clear();

    }

    public AppMainWindow(Stage primaryStage, AppLogic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void renderToDisplay(AppMessage commandResult) {
        if (commandResult.getRenderFlag()) {
            this.bluetoothPingPanelPlaceholder.getChildren().clear();
            if (commandResult.getIdentifier().equals("BluetoothPings")){
                this.bluetoothPingPanel = new BluetoothPingPanel(commandResult.getDisplayAsObservable());
                this.bluetoothPingPanelPlaceholder.getChildren().add(this.bluetoothPingPanel.getRoot());
            }
            else if (commandResult.getIdentifier().equals("BluetoothPingsSummary")) {
                this.bluetoothPingSummaryPanel = new BluetoothPingSummaryPanel(commandResult.getDisplayAsObservable());
                this.bluetoothPingPanelPlaceholder.getChildren().add(this.bluetoothPingSummaryPanel.getRoot());
            }
            else if (commandResult.getIdentifier().equals("UserSummary")) {
                this.personSummaryPanel = new PersonSummaryPanel(commandResult.getDisplayAsObservable());
                this.bluetoothPingPanelPlaceholder.getChildren().add(this.personSummaryPanel.getRoot());
            }
        }
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(this.resultDisplay.getRoot());

        AppCommandBox commandBox = new AppCommandBox(this::executeCommand);
        this.commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private AppMessage executeCommand(String commandText) throws ParseException {
        try {
            AppMessage commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            this.renderToDisplay(commandResult);

            if (commandResult.isExit()) {
                handleExit();
            }
            return commandResult;
        } catch (ParseException e) {
            logger.warning("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
