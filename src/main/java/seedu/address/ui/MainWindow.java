package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Product;
import seedu.address.ui.customer.CustomerListPanel;
import seedu.address.ui.product.ProductListPanel;
import seedu.address.ui.statistics.StatisticsListPanel;
import seedu.address.ui.transaction.TransactionListPanel;

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
    private CustomerListPanel personListPanel;
    private ProductListPanel productListPanel;
    private TransactionListPanel transactionListPanel;
    private StatisticsListPanel statisticsListPanel;

    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private NotificationWindow notificationWindow;
    private PlotWindow plotWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane productListPanelPlaceholder;

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane statisticsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
        notificationWindow = new NotificationWindow();
        plotWindow = new PlotWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
        customerListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        productListPanel = new ProductListPanel(logic.getFilteredProductList());
        productListPanelPlaceholder.getChildren().add(productListPanel.getRoot());

        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        statisticsListPanel = new StatisticsListPanel(logic);
        statisticsPanelPlaceholder.getChildren().add(statisticsListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getInventorySystemFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);

        Label commandBoxLabel = new Label("Enter command");
        commandBoxLabel.setTextFill(Color.web("#ffffff"));

        HBox commandHBox = new HBox();
        HBox.setHgrow(commandBoxLabel, Priority.ALWAYS);
        HBox.setHgrow(commandBox.getRoot(), Priority.ALWAYS);
        commandHBox.setAlignment(Pos.BASELINE_CENTER);
        commandHBox.setSpacing(10);
        commandHBox.getChildren().addAll(commandBoxLabel, commandBox.getRoot());

        commandBoxPlaceholder.getChildren().add(commandHBox);
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

    /**
     * Plot the sales of a product.
     */
    @FXML
    public void handlePlot(XYChart.Series dataSeries, String title) {
        if (!plotWindow.isShowing()) {
            plotWindow.show(dataSeries, title);
        } else {
            plotWindow.focus();
        }
    }

    /**
     *
     */
    @FXML
    public void handleNotification(Product editedProduct) {
        if (!notificationWindow.isShowing()) {
            notificationWindow.show(editedProduct.getDescription(), editedProduct.getQuantity());
        } else {
            notificationWindow.focus();
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
        Platform.exit();
        System.exit(0);
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

            statisticsPanelPlaceholder.getChildren().removeAll();
            statisticsPanelPlaceholder.getChildren().add(new StatisticsListPanel(logic).getRoot());

            if (commandResult.isShowNotification()) {
                handleNotification(commandResult.getNotificationData());
            }

            if (commandResult.isShowPlot()) {
                handlePlot(commandResult.getDataSeries(), commandResult.getTitle());
            }

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
