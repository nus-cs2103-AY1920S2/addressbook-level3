package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
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
 * Controller for a statistics window.
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
     * Sets up the logic to manage products.
     * @param logic
     */
    private void setUpLogic(Logic logic) {
        this.logic = logic;
        statisticsListPanel = new StatisticsListPanel(logic);
        statisticsListPanelPlaceholder.getChildren().add(statisticsListPanel.getRoot());
    }


    /**
     * Shows the statistics window.
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
        setUpLogic(logic);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the statistics window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistics window.
     */
    public void focus() {
        setUpLogic(logic);
        getRoot().requestFocus();
    }
}
