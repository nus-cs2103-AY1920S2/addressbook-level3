package seedu.expensela.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.logic.Logic;
import seedu.expensela.logic.commands.CommandResult;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.logic.parser.exceptions.ParseException;

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
    private FilterPanel filterPanel;
    private MonthlyDataPanel monthlyDataPanel;
    private TransactionListPanel transactionListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ChartAnalyticsPanel chartAnalyticsPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane monthlyDataPlaceholder;

    @FXML
    private StackPane transactionListAndChartAnalyticsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane filterPanelPlaceholder;

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
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        monthlyDataPanel = new MonthlyDataPanel(logic.getMonthlyData(), logic.getTotalBalance());
        chartAnalyticsPanel = new ChartAnalyticsPanel(logic.getFilteredTransactionList(), logic.getIsFilterMonth());
        transactionListAndChartAnalyticsPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());
        monthlyDataPlaceholder.getChildren().add(monthlyDataPanel.getRoot());

        filterPanel = new FilterPanel(logic.getFilter()); // instantiate filterPanel
        filterPanelPlaceholder.getChildren().add(filterPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getExpenseLaFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic);
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

    public TransactionListPanel getTransactionListPanel() {
        return transactionListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.expensela.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            monthlyDataPlaceholder.getChildren().clear();
            monthlyDataPanel = new MonthlyDataPanel(logic.getMonthlyData(), logic.getTotalBalance());
            monthlyDataPlaceholder.getChildren().add(monthlyDataPanel.getRoot());

            transactionListAndChartAnalyticsPanelPlaceholder.getChildren().clear();
            if (logic.getToggleView().getIsViewList()) {
                transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
                transactionListAndChartAnalyticsPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());
            } else {
                chartAnalyticsPanel = new ChartAnalyticsPanel(logic.getFilteredTransactionList(),
                        logic.getIsFilterMonth());
                transactionListAndChartAnalyticsPanelPlaceholder.getChildren().add(chartAnalyticsPanel.getRoot());
            }

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // updating filterPanel
            filterPanelPlaceholder.getChildren().clear();
            filterPanel = new FilterPanel(logic.getFilter());
            filterPanelPlaceholder.getChildren().add(filterPanel.getRoot());

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
}
