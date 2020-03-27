package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.statistics.StatisticsListPanel;

/**
 * Controller for a help page
 */
public class StatisticsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(StatisticsWindow.class);
    private static final String FXML = "StatisticsWindow.fxml";
    private StatisticsListPanel statisticsListPanel;
    private Logic logic;

    @FXML
    private StackPane statisticsListPanelPlaceholder;

    /**
     * Creates a new StatisticsWindow.
     *
     * @param root Stage to use as the root of the StatisticsWindow.
     */
    public StatisticsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new StatisticsWindow.
     */
    public StatisticsWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;
        setUpLogic(logic);
    }

    /**
     * Sets up the logic to manage products and transactions.
     * @param logic
     */
    private void setUpLogic(Logic logic) {
        this.logic = logic;
        ObservableList<Product> productList = logic.getFilteredProductList();
        statisticsListPanel = new StatisticsListPanel(sort(productList));
        statisticsListPanelPlaceholder.getChildren().add(statisticsListPanel.getRoot());
    }

    /**
     * Sorts the product list according to product revenue.
     * @param products
     * @return sorted list
     */
    private ObservableList<Product> sort(ObservableList<Product> products) {
        List<Product> modifiableProducts = new ArrayList<Product>(products);
        Collections.sort(modifiableProducts, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return calculateRevenueForProduct(o1) - calculateRevenueForProduct(o2);
            }
        });
        return products;
    }

    /**
     * Calculate revenue for a specific product.
     * @param product
     * @return calculated revenue
     */
    private int calculateRevenueForProduct(Product product) {
        List<Transaction> transactions = logic.getFilteredTransactionList();
        int revenue = 0;

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getProduct().equals(product)) {
                revenue += Integer.parseInt(transaction.getMoney().value);
            }
        }

        return revenue;
    }

    /**
     * Shows the help window.
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
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
