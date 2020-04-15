package seedu.address.ui.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class StatisticsListPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsListPanel.fxml";
    private static List<Transaction> transactions = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(StatisticsListPanel.class);

    @FXML
    private ListView<Product> statisticsListView;

    @FXML
    private LineChart<Integer, Integer> quantityLineChart;

    @FXML
    private LineChart<Integer, Integer> salesLineChart;


    public StatisticsListPanel(Logic logic) {
        super(FXML);
        transactions = logic.getInventorySystem().getTransactionList();
        products = logic.getInventorySystem().getProductList();
        statisticsListView.setItems(getSortedProductList(logic));
        logger.fine("Linked top selling product list to panel.");
        statisticsListView.setCellFactory(listView -> new StatisticsListViewCell());

        quantityLineChart.getData().setAll(fetchProductQuantity());
        salesLineChart.getData().setAll(fetchProductSales());
        logger.fine("Fetched product quantity and sales.");
    }

    /**
     * Sorts the product list according to product revenue.
     * @param logic
     * @return sorted list
     */
    private ObservableList<Product> getSortedProductList(Logic logic) {
        List<Product> modifiableProducts = new ArrayList<>(logic.getInventorySystem().getProductList());

        Collections.sort(modifiableProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                int o1Sales = o1.getProfit(transactions);
                int o2Sales = o2.getProfit(transactions);
                return o2Sales - o1Sales;
            }
        });

        return FXCollections.observableArrayList(modifiableProducts);
    }

    /**
     * Sorts the product list according to product revenue.
     * @return sorted list
     */
    private XYChart.Series fetchProductQuantity() {
        XYChart.Series dataSeries = new XYChart.Series();
        HashMap<Integer, Integer> allProductQuantities = new HashMap<>();

        products.forEach(p -> {
            if (allProductQuantities.containsKey(p.getQuantity().getValue())) {
                int oldValue = allProductQuantities.get(p.getQuantity().getValue());
                allProductQuantities.put(p.getQuantity().getValue(), oldValue + 1);
            } else {
                allProductQuantities.put(p.getQuantity().getValue(), 1);
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

        products.forEach(p -> {
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
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class StatisticsListViewCell extends ListCell<Product> {
        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StatisticsCard(product, getIndex() + 1, transactions).getRoot());
            }
        }
    }

}
