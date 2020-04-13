package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller of a plot page.
 */
public class PlotWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(PlotWindow.class);
    private static final String FXML = "PlotWindow.fxml";

    @FXML
    private BarChart barChart;

    @FXML
    private Label title;

    /**
     * Creates a new PlotWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public PlotWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new PlotWindow.
     */
    public PlotWindow() {
        this(new Stage());
    }

    /**
     * Shows the plot window.
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
    public void show(XYChart.Series dataSeries, String titleText) {
        logger.fine("Showing plot window.");
        barChart.getData().setAll(dataSeries);
        title.setText(titleText);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the plot window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the plot window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
