package seedu.address.ui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
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
import seedu.address.commons.util.ListUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 *
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private OrderListPanel orderListPanel;
    private ReturnOrderListPanel returnOrderListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ClearWindow clearWindow;

    private List<Order> orderList;
    private List<Order> deliveredList;

    private List<ReturnOrder> returnList;
    private List<ReturnOrder> returnedList;

    @FXML
    private Tab listTab;

    @FXML
    private Tab showTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem exitAppItem;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane orderListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane returnOrderListPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Label dateToday;

    @FXML
    private Label doneOrders;

    @FXML
    private Label returnedOrders;

    @FXML
    private Label totalCash;

    @FXML
    private PieChart deliveryPieChart;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        clearWindow = new ClearWindow(logic);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(exitAppItem, KeyCombination.valueOf("F1"));
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F2"));
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
     * A listener that listens on whether the Show Tab is clicked
     *
     */
    @FXML
    void showStatistics(Event ev) {
        if (showTab.isSelected()) {
            tabPane.getSelectionModel().select(showTab);
            //Do stuff here
        }
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
        orderListPanelPlaceholder.getChildren().add(orderListPanel.getRoot());

        returnOrderListPanel = new ReturnOrderListPanel(logic.getFilteredReturnOrderList());
        returnOrderListPanelPlaceholder.getChildren().add(returnOrderListPanel.getRoot());

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
     * Opens the clear warning window or focuses on it if it's already opened.
     */
    @FXML
    public void handleClearWarning(String warningMessage) {
        clearWindow.setWarningMessage(warningMessage);

        if (!clearWindow.isShowing()) {
            clearWindow.show();
        } else {
            clearWindow.focus();
        }
    }

    /**
     * Calculate the total earnings of the Courier.
     * The method will use a DecimalFormatter instance to convert a double
     * into a String with two decimal places and put the value into the Label.
     *
     */
    public void calcEarnings() {
        DecimalFormat df2 = new DecimalFormat("$0.00");
        double sum = 0;

        for (Order order: deliveredList) {
            sum += order.getCash().getCashValue();
        }

        String earnings = (sum == 0 ? "$0" : df2.format(sum));
        totalCash.setText(earnings);
    }

    /**
     * Populate the PieChart with the data filtered by the
     * ListUtil class
     *
     */
    public void populatePieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Orders completed: " + deliveredList.size() , deliveredList.size()),
                        new PieChart.Data("Orders not completed: "
                                + (orderList.size() - deliveredList.size()), orderList.size() - deliveredList.size()),
                        new PieChart.Data("Orders returned: " + returnedList.size(), returnedList.size()),
                        new PieChart.Data("Orders not returned: "
                                + (returnList.size() - returnedList.size()), returnList.size() - returnedList.size()));

        deliveryPieChart.setTitle("All Orders");
        deliveryPieChart.setData(pieChartData);
    }

    /**
     * With the LogicManager given, use it to get the lists of delivery orders.
     * The list will be used to display the statistics of the delivery orders.
     *
     */
    public void getDeliveryData() {
        orderList = (new FilteredList<>(logic.getOrderBook().getOrderList()))
                .stream()
                .filter(ListUtil::filterListByDates)
                .collect(Collectors.toList());

        deliveredList = orderList
                .stream()
                .filter(Order::isDelivered)
                .collect(Collectors.toList());
    }

    /**
     * Putting the data values for delivery orders into the Labels.
     *
     */
    public void populateDeliveryStats() {
        doneOrders.setText(String.valueOf(deliveredList.size()));
    }

    /**
     * With the LogicManager given, use it to get the lists of return orders.
     * The list will be used to display the statistics of the return orders.
     *
     */
    public void getReturnData() {
        returnList = (new FilteredList<>(logic.getReturnOrderBook().getReturnOrderList()))
                .stream()
                .filter(ListUtil::filterListByDates)
                .collect(Collectors.toList());

        returnedList = returnList
                .stream()
                .filter(ReturnOrder::isDelivered)
                .collect(Collectors.toList());
    }

    /**
     * Putting the data values for return orders into the Labels.
     *
     */
    public void populateReturnStats() {
        returnedOrders.setText(String.valueOf(returnedList.size()));
    }

    /**
     * Opens the show window or focus on it if it the window is already opened.
     */
    @FXML
    public void handleShowCommand() {
        // Initialize the constraints
        // for the statistics
        ListUtil.init();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate dateFrom = ListUtil.getStartDate();
        LocalDate dateTo = ListUtil.getEndDate();

        // Go to the Show Tab
        tabPane.getSelectionModel().select(showTab);

        if (ListUtil.isAll()) {
            dateToday.setText((ListUtil.getAllDates()));
        } else if (dateFrom.compareTo(dateTo) == 0) {
            dateToday.setText(dateFrom.format(dateFormatter));
        } else {
            dateToday.setText(dateFrom.format(dateFormatter) + " to " + dateTo.format(dateFormatter));
        }

        // Delivery orders
        getDeliveryData();
        populateDeliveryStats();

        // Return Orders
        getReturnData();
        populateReturnStats();

        // Calculate Earnings
        calcEarnings();

        // Add data to PieChart
        populatePieChart();
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
        clearWindow.hide();
        primaryStage.hide();
    }

    public OrderListPanel getOrderListPanel() {
        return orderListPanel;
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

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else {
                // Go to the list tab
                tabPane.getSelectionModel().select(listTab);

                if (commandResult.isClearList()) {
                    handleClearWarning(commandResult.getFeedbackToUser());
                    clearWindow.setComponent(resultDisplay);
                }

                // Show Command
                if (commandResult.isDisplayEarnings()) {
                    handleShowCommand();
                }
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
