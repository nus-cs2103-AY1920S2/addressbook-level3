package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a graph page
 */
public class GraphWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(GraphWindow.class);
    private static final String FXML = "GraphWindow.fxml";

    @FXML
    private LineChart<String, Number> exerciseGraph;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public GraphWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new GraphWindow.
     */
    public GraphWindow() {
        this(new Stage());
        // defining the axes
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");

        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("My portfolio");
        // populating the series with data
        series.getData().add(new XYChart.Data<String, Number>("1", 23));
        series.getData().add(new XYChart.Data<String, Number>("2", 14));
        series.getData().add(new XYChart.Data<String, Number>("3", 15));
        series.getData().add(new XYChart.Data<String, Number>("4", 24));
        series.getData().add(new XYChart.Data<String, Number>("5", 34));
        series.getData().add(new XYChart.Data<String, Number>("6", 36));
        series.getData().add(new XYChart.Data<String, Number>("7", 22));
        series.getData().add(new XYChart.Data<String, Number>("8", 45));
        series.getData().add(new XYChart.Data<String, Number>("9", 43));
        series.getData().add(new XYChart.Data<String, Number>("10", 17));
        series.getData().add(new XYChart.Data<String, Number>("11", 29));
        series.getData().add(new XYChart.Data<String, Number>("12", 25));

        exerciseGraph.getData().addAll(series);
    }

    /**
     * Shows the help window.
     * 
     * @throws IllegalStateException
     *                               <ul>
     *                               <li>if this method is called on a thread other
     *                               than the JavaFX Application Thread.</li>
     *                               <li>if this method is called during animation
     *                               or layout processing.</li>
     *                               <li>if this method is called on the primary
     *                               stage.</li>
     *                               <li>if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing graph.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the graph window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the grap window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the graph window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
