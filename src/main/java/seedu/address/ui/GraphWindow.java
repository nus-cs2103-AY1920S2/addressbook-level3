package seedu.address.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseDate;
import seedu.address.model.exercise.ExerciseReps;
import seedu.address.model.exercise.ExerciseWeight;

/**
 * Controller for a graph page
 */
public class GraphWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(GraphWindow.class);
    private static final String FXML = "GraphWindow.fxml";

    private final List<Exercise> graphList;
    private final boolean isShowReps;
    private final boolean isShowWeight;
    public XYChart.Series<Number, Number> series;

    @FXML
    private LineChart<Number, Number> exerciseGraph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    /**
     * Creates a new GraphWindow.
     */
    public GraphWindow(List<Exercise> graphList, boolean isShowReps, boolean isShowWeight) {
        super(FXML, new Stage());
        this.graphList = graphList;
        this.isShowReps = isShowReps;
        this.isShowWeight = isShowWeight;

        if (isShowReps) {
            fillRepsSeries();
            yAxis.setLabel("Reps");
        } else {
            fillWeightSeries();
            yAxis.setLabel("Weight");
        }

        series.setName("Exercise Graph");
        
        formatDateLabels();
        exerciseGraph.getData().add(series);
    }

    private void fillRepsSeries() {
        series = new XYChart.Series<Number, Number>();

        for (Exercise exercise : graphList) {
            Number plotDate = getDateInNumberFormat(exercise);
            Number plotReps = getRepsInNumberFormat(exercise);
            series.getData().add(new XYChart.Data<Number, Number>(plotDate, plotReps));
        }
    }

    private void fillWeightSeries() {
        series = new XYChart.Series<Number, Number>();

        for (Exercise exercise : graphList) {
            Number plotDate = getDateInNumberFormat(exercise);
            Number plotWeight = getWeightInNumberFormat(exercise);
            series.getData().add(new XYChart.Data<Number, Number>(plotDate, plotWeight));
        }
    }

    private void formatDateLabels() {
        StringConverter<Number> converter = new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(Number object) {
                return LocalDate.ofEpochDay(object.longValue()).toString();
            }
            @Override
            public Number fromString(String string) {
                return null;
            }
        };

        xAxis.setTickLabelFormatter(converter);
    }

    /**
     * Shows the graph window.
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
     * Hides the graph window.
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

    private static Number getRepsInNumberFormat(Exercise exercise) {
        ExerciseReps reps = exercise.getExerciseReps();
        Number plotReps = reps.convertToInt();
        return plotReps;
    }

    private static Number getWeightInNumberFormat(Exercise exercise) {
        ExerciseWeight weight = exercise.getExerciseWeight();
        Number plotWeight = weight.convertToInt();
        return plotWeight;
    }

    private static Number getDateInNumberFormat(Exercise exercise) {
        ExerciseDate date = exercise.getExerciseDate();
        Number plotDate = date.forPlot();
        return plotDate;
    }
}
