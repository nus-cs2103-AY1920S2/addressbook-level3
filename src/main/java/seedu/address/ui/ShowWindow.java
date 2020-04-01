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

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ListUtil;
import seedu.address.logic.Logic;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.returnorder.ReturnOrder;

/**
 * The Show Window. Displays the statistics regarding the orders that
 * are being delivered and the earnings made from these orders.
 *
 * The ShowWindow.fxml design was adopted from the link below :
 * https://www.youtube.com/watch?v=UDi051XyQ-U&t=1228s
 */
public class ShowWindow extends UiPart<Stage> {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private static final LocalDate dateNow = LocalDate.now();
    private static final Logger logger = LogsCenter.getLogger(ClearWindow.class);
    private static final String FXML = "ShowWindow.fxml";

    private List<Order> orderList;
    private List<Order> deliveredList;

    private List<ReturnOrder> returnList;
    private List<ReturnOrder> returnedList;

    private Logic logic;

    @FXML
    private PieChart deliveryPieChart;

    @FXML
    private PieChart returnPieChart;

    @FXML
    private Label doneOrders;

    @FXML
    private Label deliveriesToday;

    @FXML
    private Label totalCash;

    @FXML
    private Label dateToday;

    @FXML
    private Label returnedOrders;

    @FXML
    private Label toReturn;

    @FXML
    private Label totalOrders;

    /**
     * Create a new ShowWindow.
     *
     * @param root Stage to use as the root of the ShowWindow.
     */
    public ShowWindow(Stage root) {
        super(FXML, root);
    }

    public ShowWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;
        dateToday.setText(dateNow.format(dateFormatter));
        init();
    }

    /**
     * The method is responsible for populating the PieChart
     * with data values about the delivery orders assigned to the courier.
     *
     */
    public void populateDeliveryPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Orders completed", deliveredList.size()),
                        new PieChart.Data("Orders not completed", orderList.size() - deliveredList.size()));

        deliveryPieChart.setTitle("Delivery Orders");
        deliveryPieChart.setData(pieChartData);
    }

    /**
     * With the LogicManager given, use it to get the lists of delivery orders.
     * The list will be used to display the statistics of the delivery orders.
     *
     */
    public void getDeliveryData() {
        orderList = (new FilteredList<>(logic.getFilteredOrderList()))
                .stream()
                .filter(ListUtil::isToday)
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
        deliveriesToday.setText(String.valueOf(orderList.size()));
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
     * With the LogicManager given, use it to get the lists of return orders.
     * The list will be used to display the statistics of the return orders.
     *
     */
    public void getReturnData() {
        returnList = (new FilteredList<>(logic.getFilteredReturnOrderList()))
                .stream()
                .filter(ListUtil::isToday)
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
        toReturn.setText(String.valueOf(returnList.size()));
    }

    /**
     * Find the total orders to make today for both
     * return orders and delivery orders.
     *
     */
    public void setTotalOrders() {
        totalOrders.setText(String.valueOf(returnList.size() + orderList.size()));
    }

    /**
     * The method is responsible for populating the PieChart
     * with data values about the return orders assigned to the courier.
     *
     */
    public void populateReturnPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Orders completed", returnedList.size()),
                        new PieChart.Data("Orders not completed", returnList.size() - returnedList.size()));

        returnPieChart.setTitle("Return Orders");
        returnPieChart.setData(pieChartData);
    }

    /**
     * Call a series of methods in the init() method that
     * collects all the data from the list of orders and return orders
     * and displays it to the user through the ShowWindow.
     *
     */
    public void init() {
        // Delivery Orders
        getDeliveryData();
        populateDeliveryStats();
        populateDeliveryPieChart();

        // Display Earnings
        calcEarnings();

        // Return Orders
        getReturnData();
        populateReturnStats();
        populateReturnPieChart();

        // Total Orders
        setTotalOrders();
    }
    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Show the Clear Window Stage in the centre of the Screen.
     */
    public void show() {
        logger.fine("Showing the earnings to the user for every orders completed.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Focuses on the show window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
