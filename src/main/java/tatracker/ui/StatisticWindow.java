package tatracker.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import tatracker.commons.core.LogsCenter;

/**
 * Controller for a statistic page
 */
public class StatisticWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s2-cs2103t-w17-4.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "This is a test statistic";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "StatisticWindow.fxml";

    @FXML
    private PieChart hoursBreakdownPieChart;

    @FXML
    private BarChart studentRatingBarChart;

//
//    @FXML
//    private Label statisticMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatisticWindow(Stage root) {
        super(FXML, root);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Tutorial", 10),
                        new PieChart.Data("Lab", 10),
                        new PieChart.Data("Consultation", 10),
                        new PieChart.Data("Grading", 10),
                        new PieChart.Data("Preparation", 10),
                        new PieChart.Data("Other", 10));

        hoursBreakdownPieChart.setData(pieChartData);

        BarChart.Series dataSeries1 = new BarChart.Series();
        dataSeries1.getData().add(new BarChart.Data("1", 2));
        dataSeries1.getData().add(new BarChart.Data("2"  , 3));
        dataSeries1.getData().add(new BarChart.Data("3"  , 8));
        dataSeries1.getData().add(new BarChart.Data("4"  , 5));
        dataSeries1.getData().add(new BarChart.Data("5"  , 2));
        studentRatingBarChart.getData().add(dataSeries1);
    }

    /**
     * Creates a new HelpWindow.
     */
    public StatisticWindow() {
        this(new Stage());
    }

    /**
     * Shows the statistic window.
     *
     * @throws IllegalStateException <ul>
     *                                   <li>
     *                                       if this method is called on a thread other than the JavaFX Application Thread.
     *                                   </li>
     *                                   <li>
     *                                       if this method is called during animation or layout processing.
     *                                   </li>
     *                                   <li>
     *                                       if this method is called on the primary stage.
     *                                   </li>
     *                                   <li>
     *                                       if {@code dialogStage} is already showing.
     *                                   </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing statistic page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the statistic window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistic window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistic window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
