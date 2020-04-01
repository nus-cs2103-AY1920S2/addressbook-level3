package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.product.Product;

/**
 * Controller for a inventory window.
 */
public class InventoryWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(InventoryWindow.class);
    private static final String FXML = "InventoryWindow.fxml";
    private ObservableList<Product> products;
    private XYChart.Series<Integer, Integer> quantityDataSeries;
    private XYChart.Series<Integer, Integer> salesDataSeries;

    @FXML
    private LineChart<Integer, Integer> quantityLineChart;

    @FXML
    private LineChart<Integer, Integer> salesLineChart;

    @FXML
    private Label title;

    /**
     * Creates a new InventoryWindow.
     *
     * @param root Stage to use as the root of the InventoryWindow.
     */
    public InventoryWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new InventoryWindow.
     */
    public InventoryWindow(Logic logic) {
        this(new Stage());
        products = logic.getInventorySystem().getProductList();
        quantityDataSeries = fetchProductQuantity();
        salesDataSeries = fetchProductSales();
    }

    /**
     * Sorts the product list according to product revenue.
     * @return sorted list
     */
    private XYChart.Series fetchProductQuantity() {
        XYChart.Series dataSeries = new XYChart.Series();
        HashMap<Integer, Integer> allProductQuantities = new HashMap<>();

        products.forEach(p ->
        {
            if (allProductQuantities.containsKey(p.getQuantity().value)) {
                int oldValue = allProductQuantities.get(p.getQuantity().value);
                allProductQuantities.put(p.getQuantity().value, oldValue + 1);
            } else {
                allProductQuantities.put(p.getQuantity().value, 1);
            }
        });

        for (Map.Entry<Integer, Integer> entry: allProductQuantities.entrySet()) {
            XYChart.Data nextData = new XYChart.Data(entry.getKey().intValue(), entry.getValue().intValue());
            dataSeries.getData().add(nextData);
        }

        return dataSeries;
    }

    /**
     * Sorts the product list according to product revenue.
     * @return sorted list
     */
    private XYChart.Series fetchProductSales() {
        XYChart.Series dataSeries = new XYChart.Series();
        HashMap<Integer, Integer> allProductSales = new HashMap<>();

        products.forEach(p ->
        {
            if (allProductSales.containsKey(p.getMoney().value)) {
                int oldValue = allProductSales.get(p.getMoney().value);
                allProductSales.put(p.getMoney().value, oldValue + 1);
            } else {
                allProductSales.put(p.getMoney().value, 1);
            }
        });

        for (Map.Entry<Integer, Integer> entry: allProductSales.entrySet()) {
            XYChart.Data nextData = new XYChart.Data(entry.getKey().intValue(), entry.getValue().intValue());
            dataSeries.getData().add(nextData);
        }

        return dataSeries;
    }

    /**
     * Shows the inventory window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        quantityLineChart.getData().setAll(quantityDataSeries);
        salesLineChart.getData().setAll(salesDataSeries);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the inventory window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the inventory window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the inventory window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

