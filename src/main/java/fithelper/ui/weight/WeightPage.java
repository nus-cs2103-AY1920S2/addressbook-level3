package fithelper.ui.weight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.weight.Weight;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for a user weight page.
 * The weight page contains all user weight and bmi information.
 * It can be divided into three main parts: notification bar, weight line graph and BMI line graph.
 * Weight page will be updated and shown automatically when user inputs some weight-related command.
 */
public class WeightPage extends UiPart<AnchorPane> {
    private static final String FXML = "WeightPage.fxml";
    private final Logger logger = LogsCenter.getLogger(WeightPage.class);
    private final List<Weight> weights; // a sorted version (by date) of all weight records.
    private final ReadOnlyUserProfile profile;

    @FXML
    private Label title;

    @FXML
    private LineChart<String, Double> weightLineChart;

    @FXML
    private LineChart<String, Double> bmiLineChart;

    /**
     * Creates a new Weight Window displaying user weight-related data.
     * @param profile an unmodifiable version of user profile data.
     * @param weights an unmodifiable version of all user weight records.
     */
    public WeightPage(ReadOnlyUserProfile profile, ObservableList<Weight> weights) {
        super(FXML);
        logger.info("Initializing Weight Page");
        this.weights = weights.sorted((o1, o2) -> {
            if (o1.getDate().value.isAfter(o2.getDate().value)) {
                return 1;
            } else {
                return -1;
            }
        });
        this.profile = profile;

        initializeTitle();
        initializeWeightLineChart();
        initializeBmiLineChart();
    }

    /**
     * Initialize the value of the title line.
     * Three cases are considered: no weight records, not reach target goal weight and reach already.
     */
    private void initializeTitle() {
        double target = profile.getUserProfile().getTargetWeight().value;
        if (weights.size() == 0) {
            title.setText("Keep going! You haven't reached your goal!");
        } else {
            double current = weights.get(weights.size() - 1).getWeightValue().value;
            double gap = current - target;
            if (gap > 0.0001) {
                title.setText("Keep going! You are " + String.format("%.2f", gap) + "kg away from you target weight!");
            } else {
                title.setText("Congratulations! You have reached your target weight!");
            }
        }
    }

    /**
     * Get the first date from weight records.
     * @param weights a list of weight.
     * @return a String representation of the first date.
     */
    private String getFirstDate(List<Weight> weights) {
        if (weights.size() == 0) {
            return null;
        } else {
            LocalDate first = weights.get(0).getDate().value;
            return first.toString();
        }
    }

    /**
     * Get the last date from weight records.
     * @param weights a list of weight.
     * @return a String representation of the last date.
     */
    private String getLastDate(List<Weight> weights) {
        if (weights.size() == 0) {
            return null;
        } else {
            LocalDate last = weights.get(weights.size() - 1).getDate().value;
            return last.toString();
        }
    }

    /**
     * Get all dates from all weight records.
     * The order of the dates is corresponding to each weight records in the list.
     * @param weights a list of weight records.
     * @return a list of dates.
     */
    private ArrayList<String> getDates(List<Weight> weights) {
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            dates.add(weights.get(i).getDate().value.toString());
        }
        return dates;
    }

    /**
     * Get all weight from all weight records.
     * The order of the weight values is corresponding to each weight records in the list.
     * @param weights a list of weight records.
     * @return a list of weight value in double.
     */
    private ArrayList<Double> getWeights(List<Weight> weights) {
        ArrayList<Double> weight = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            weight.add(weights.get(i).getWeightValue().value);
        }
        return weight;
    }

    /**
     * Get all BMI from all weight records.
     * The order of the BMI is corresponding to each weight records in the list.
     * @param weights a list of weight records.
     * @return a list of BMI values in double.
     */
    private ArrayList<Double> getBmis(List<Weight> weights) {
        ArrayList<Double> bmis = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            bmis.add(weights.get(i).getBmi().value);
        }
        return bmis;
    }

    /**
     * Install tooltip for line charts.
     * The text content of the tooltip is the value of x and y in data pairs.
     * @param data an unmodifiable version of x,y data pairs.
     */
    public void installToolTipXyChart(ObservableList<XYChart.Data<String, Double>> data) {
        data.stream().forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(d.getXValue() + "\n" + d.getYValue());
            Tooltip.install(d.getNode(), tooltip);
        });
    }

    /**
     * Set the title of the line chart.
     * The content and format of the title depends on the number and dates of weight records.
     * @param lineChart the corresponding chart.
     * @param defaultTitle the default title of there is no weight records.
     */
    private void setLineChartTitle(LineChart<String, Double> lineChart, String defaultTitle) {
        String firstDate = getFirstDate(weights);
        String lastDate = getLastDate(weights);
        int size = weights.size();

        if (size == 0) {
            lineChart.setTitle(defaultTitle);
        } else if (size == 1) {
            lineChart.setTitle(defaultTitle + ": [" + firstDate + "]");
        } else {
            lineChart.setTitle(defaultTitle + ": [" + firstDate + "~" + lastDate + "]");
        }
    }

    /**
     * Initialize the weight line chart.
     * X-value is all dates in weight records, sorted from oldest to newest.
     * Y-value is the magnitude of all weight values, corresponding to each date.
     * The title of the chart will show the date range if applicable.
     */
    private void initializeWeightLineChart() {
        // Initialize data points value.
        ArrayList<String> dates = getDates(weights);
        ArrayList<Double> values = getWeights(weights);

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        int size = dates.size(); // the number of weight records in total.
        for (int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), values.get(i)));
        }

        weightLineChart.getData().add(series);

        // Initialize chart layout.
        weightLineChart.setAnimated(false);
        weightLineChart.layout();
        weightLineChart.setLegendVisible(false);
        installToolTipXyChart(series.getData());

        setLineChartTitle(weightLineChart, "Weight Trend Line");
    }

    /**
     * Initialize the BMI line chart.
     * X-value is all dates in weight records, sorted from oldest to newest.
     * Y-value is the BMI value corresponding to each date.
     * The title of the chart will show the date range if applicable.
     */
    private void initializeBmiLineChart() {
        // Initialize data points value.
        ArrayList<String> dates = getDates(weights);
        ArrayList<Double> values = getBmis(weights);

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        int size = dates.size();
        for (int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), values.get(i)));
        }

        bmiLineChart.getData().add(series);

        // Initialize chart layout.
        bmiLineChart.setAnimated(false);
        bmiLineChart.layout();
        bmiLineChart.setLegendVisible(false);
        installToolTipXyChart(series.getData());

        setLineChartTitle(bmiLineChart, "BMI Trend Line");
    }
}
