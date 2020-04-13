//@@author Eclmist

package tatracker.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import tatracker.commons.core.LogsCenter;
import tatracker.model.session.SessionType;
import tatracker.model.statistic.Statistic;

/**
 * Controller for a statistic page
 */
public class StatisticWindow extends UiPart<Stage> {

    public static final int NUM_STUDENTS_TO_DISPLAY = 5;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "StatisticWindow.fxml";

    @FXML
    private PieChart hoursBreakdownPieChart;

    @FXML
    private BarChart<String, Integer> studentRatingBarChart;

    // Labels
    @FXML
    private Label moduleCodeLabel;
    @FXML
    private Label numHoursTutorialLabel;
    @FXML
    private Label numHoursConsultationLabel;
    @FXML
    private Label numHoursLabLabel;
    @FXML
    private Label numHoursGradingLabel;
    @FXML
    private Label numHoursPreparationLabel;
    @FXML
    private Label numHoursOtherLabel;
    @FXML
    private Label numHoursTotalLabel;

    @FXML
    private Label studentName1Label;
    @FXML
    private Label studentName2Label;
    @FXML
    private Label studentName3Label;
    @FXML
    private Label studentName4Label;
    @FXML
    private Label studentName5Label;
    @FXML
    private Label rating1Label;
    @FXML
    private Label rating2Label;
    @FXML
    private Label rating3Label;
    @FXML
    private Label rating4Label;
    @FXML
    private Label rating5Label;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public StatisticWindow(Stage root, Statistic stats) {
        super(FXML, root);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < SessionType.NUM_SESSION_TYPES; ++i) {
            if (stats.numHoursPerCategory[i] > 0) {
                pieChartData.add(
                        new PieChart.Data(SessionType.getSessionTypeById(i).toString(), stats.numHoursPerCategory[i]));
            }
        }
        hoursBreakdownPieChart.setData(pieChartData);

        BarChart.Series<String, Integer> dataSeries1 = new BarChart.Series<>();
        for (int i = 0; i < 5; ++i) {
            dataSeries1.getData().add(new BarChart.Data<>(Integer.toString(i), stats.studentRatingBinValues[i]));
        }
        studentRatingBarChart.getData().add(dataSeries1);

        // Set labels
        moduleCodeLabel.setText(stats.targetModuleCode);
        numHoursTutorialLabel.setText(stats.numHoursPerCategory[0] + " Hours");
        numHoursLabLabel.setText(stats.numHoursPerCategory[1] + " Hours");
        numHoursConsultationLabel.setText(stats.numHoursPerCategory[2] + " Hours");
        numHoursGradingLabel.setText(stats.numHoursPerCategory[3] + " Hours");
        numHoursPreparationLabel.setText(stats.numHoursPerCategory[4] + " Hours");
        numHoursOtherLabel.setText(stats.numHoursPerCategory[5] + " Hours");
        numHoursTotalLabel.setText(stats.getTotalHours() + " (S$" + stats.getTotalEarnings() + ")");

        studentName1Label.setText(stats.worstStudents[0].getFullName());
        rating1Label.setText(Integer.toString(stats.worstStudents[0].getRating()));
        studentName2Label.setText(stats.worstStudents[1].getFullName());
        rating2Label.setText(Integer.toString(stats.worstStudents[1].getRating()));
        studentName3Label.setText(stats.worstStudents[2].getFullName());
        rating3Label.setText(Integer.toString(stats.worstStudents[2].getRating()));
        studentName4Label.setText(stats.worstStudents[3].getFullName());
        rating4Label.setText(Integer.toString(stats.worstStudents[3].getRating()));
        studentName5Label.setText(stats.worstStudents[4].getFullName());
        rating5Label.setText(Integer.toString(stats.worstStudents[4].getRating()));

        //@@author fatin99

        root.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    logger.info("click on escape");
                    root.close();
                }
            }
        });
    }

    //@@author Eclmist

    /**
     * Creates a new HelpWindow.
     */
    public StatisticWindow(Statistic stats) {
        this(new Stage(), stats);
    }

    /**
     * Shows the statistic window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
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
