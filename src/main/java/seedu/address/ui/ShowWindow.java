package seedu.address.ui;

import java.text.DecimalFormat;

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
import seedu.address.logic.Logic;
import seedu.address.model.order.Order;




/**
 * The Show Window. Displays the statistics regarding the orders that
 * are being delivered and the earnings made from these orders.
 *
 * The ShowWindow.fxml design was adopted from the link below :
 * https://www.youtube.com/watch?v=UDi051XyQ-U&t=1228s
 */
public class ShowWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ClearWindow.class);
    private static final String FXML = "ShowWindow.fxml";

    private FilteredList<Order> fullList;
    private List<Order> tempOrderList;

    @FXML
    private PieChart piechart;

    @FXML
    private Label doneorders;

    @FXML
    private Label totalorders;

    @FXML
    private Label totalcash;

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
        init(logic);
    }

    /**
     * The method is responsible for adding the data values
     * to the PieChart.
     */
    public void addPieChartValues() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Orders completed", tempOrderList.size()),
                        new PieChart.Data("Orders not completed", fullList.size() - tempOrderList.size()));

        piechart.setTitle("Orders");
        piechart.setData(pieChartData);

        totalcash.setText(calcEarnings());

        doneorders.setText(String.valueOf(tempOrderList.size()));

        totalorders.setText(String.valueOf(fullList.size()));
    }

    /**
     * Calculate the total earnings of the Courier.
     * Earnings from delivering orders.
     *
     * @return String The earnings as a String type.
     */
    public String calcEarnings() {
        DecimalFormat df2 = new DecimalFormat("$0.00");
        double sum = 0;
        for (Order order: tempOrderList) {
            sum += order.getCash().getCashValue();
        }

        return df2.format(sum);
    }

    /**
     * Initialize the private variable fields of
     * ShowWindow class and call the addPieChartValues method.
     *
     * @param logic pass LogicManager as a parameter.
     */
    public void init(Logic logic) {
        fullList = new FilteredList<>(logic.getFilteredOrderList());
        tempOrderList = fullList
                .stream()
                .filter(Order::isDelivered)
                .collect(Collectors.toList());

        addPieChartValues();
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
