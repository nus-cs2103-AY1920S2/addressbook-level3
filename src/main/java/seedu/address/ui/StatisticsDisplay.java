package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.dayData.CustomQueue;

import static seedu.address.model.dayData.CustomQueue.CONSTANT_SIZE;

/** An UI component that displays the Pomodoro {@code Pomodoro}. */
public class StatisticsDisplay extends UiPart<Region> {

    private static final String FXML = "StatisticsDisplay.fxml";
    //private static final String DEFAULT_BAR_CHART_TITLE = "Start POMMING to track your data!";
    private static final Path DEFAULT_PROGRESS_BAR_DAILY__PLACEHOLDER =
            Paths.get("images", "statistics", "progressBarDaily0%.png");
    private static final String DEFAULT_PROGRESS_DAILY = "0 mins / 100 mins (DEFAULT)";
    private static final String DEFAULT_MEDALS = "Placeholder";
    private static final String X_AXIS = "Day";

    //public String barChartTitleText; // mutable
    public Path progressBarDailyFilepath;
    public String progressDailyText;
    public String medalsText;

    @FXML private VBox statisticsPane;
    //@FXML private Label barChartTitle;
    @FXML private BarChart<String, Integer> barChartPomDurationData;
    @FXML private BarChart<String, Integer> barChartTasksDoneData;

    @FXML private Label progressDaily;
    @FXML private ImageView progressBarDaily;
    @FXML private Label medals;

    public StatisticsDisplay() {
        super(FXML);
        //this.barChartTitleText = DEFAULT_BAR_CHART_TITLE;
        this.progressBarDailyFilepath = DEFAULT_PROGRESS_BAR_DAILY__PLACEHOLDER;
        this.progressDailyText = DEFAULT_PROGRESS_DAILY;
        this.medalsText = DEFAULT_MEDALS;

        progressDaily.setText(progressDailyText);
        Image progressBarDailyImage = new Image(String.valueOf(progressBarDailyFilepath));
        progressBarDaily.setImage(progressBarDailyImage);
        medals.setText(medalsText);

        //barChartTitle.setText(barChartTitleText);

        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setLabel(X_AXIS);
        NumberAxis yAxis1 = new NumberAxis();

        CategoryAxis xAxis2 = new CategoryAxis();
        xAxis2.setLabel(X_AXIS);
        NumberAxis yAxis2 = new NumberAxis();
    }

    public void updateGraphs(CustomQueue customQueue) {
        XYChart.Series<String, Integer> dataSeriesPomDurationData = new XYChart.Series<>();
        XYChart.Series<String, Integer> dataSeriesTasksDoneData = new XYChart.Series<>();
        //dataSeries.setName("You");

        for (int i = CONSTANT_SIZE - 1; i >= 0; i--) {
            String dateString = customQueue.get(i).getDate().toPrint();

            int pomDurationDataInt = customQueue.get(i).getPomDurationData().value;
            dataSeriesPomDurationData.getData().add(new XYChart.Data<>(dateString, pomDurationDataInt));

            int tasksDoneDataInt = customQueue.get(i).getTasksDoneData().value;
            dataSeriesTasksDoneData.getData().add(new XYChart.Data<>(dateString, tasksDoneDataInt));
        }

        barChartPomDurationData.getData().clear();
        barChartTasksDoneData.getData().clear();

        barChartPomDurationData.getData().add(dataSeriesPomDurationData);
        barChartTasksDoneData.getData().add(dataSeriesTasksDoneData);

    }
    /*
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatisticsDisplay)) {
            return false;
        }

        // state check
        StatisticsDisplay card = (StatisticsDisplay) other;
        return barChartTitle.getText().equals(card.barChartTitle.getText());
    } */
}
