package seedu.address.ui;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseDate;
import seedu.address.model.exercise.ExerciseReps;

/**
 * Controller for a graph page
 */
public class GraphWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(GraphWindow.class);
    private static final String FXML = "GraphWindow.fxml";

    private boolean showReps;
    private boolean showSets;

    @FXML
    private LineChart<Number, Number> exerciseGraph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private StringConverter<Number> converter = new NumberAxis.DefaultFormatter(xAxis) {
        @Override
        public String toString(Number object) {
            return LocalDate.ofEpochDay(object.longValue()).toString();
        }

        @Override
        public Number fromString(String string) {
            return null;
        }
    };

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
    public GraphWindow(List<Exercise> graphList, boolean showReps, boolean showWeight) {
        this(new Stage());
        // defining the axes
        xAxis.setTickLabelFormatter(converter);
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("Exercise Graph");
        // populating the series with data

        for (Exercise e : graphList) {
            ExerciseReps reps = e.getExerciseReps();
            Number plotReps = reps.convertToInt();
            ExerciseDate date = e.getExerciseDate();
            Number plotDate = date.forPlot();
            series.getData().add(new XYChart.Data<Number, Number>(plotDate, plotReps));
        }

        exerciseGraph.getData().add(series);
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
