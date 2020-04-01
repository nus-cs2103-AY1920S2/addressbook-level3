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
 */
public class WeightPage extends UiPart<AnchorPane> {
    private static final String FXML = "WeightPage.fxml";
    private final Logger logger = LogsCenter.getLogger(WeightPage.class);
    private final List<Weight> weights;
    private final ReadOnlyUserProfile profile;

    @FXML
    private Label title;

    @FXML
    private LineChart<String, Double> weightLineChart;

    @FXML
    private LineChart<String, Double> bmiLineChart;

    /**
     * Creates a new Weight Window displaying user weight-related data.
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
     */
    private void initializeTitle() {
        double target = profile.getUserProfile().getTargetWeight().value;
        if (weights.size() == 0) {
            title.setText("Keep going! You haven't reached your goal!");
        } else {
            double current = weights.get(weights.size() - 1).getWeightValue().value;
            double gap = current - target;
            if (gap > 0.0001) {
                title.setText("Keep going! You are " + String.format("%.3f", gap) + " away from you target weight!");
            } else {
                title.setText("Congratulations! You have reached your target weight!");
            }
        }
    }

    /**
     * Get first and last date from weights.
     * @param weights a list of weight.
     * @return an array with first element equals to first date and second element equals to last date.
     */
    private String[] getDate(List<Weight> weights) {
        if (weights.size() == 0) {
            return null;
        } else {
            LocalDate first = weights.get(0).getDate().value;
            LocalDate last = weights.get(weights.size() - 1).getDate().value;
            return new String[]{first.toString(), last.toString()};
        }
    }

    /**
     * Get dates from all weights.
     * @param weights a list of weight.
     * @return a list of date.
     */
    private ArrayList<String> getDates(List<Weight> weights) {
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            dates.add(weights.get(i).getDate().value.toString());
        }
        return dates;
    }

    /**
     * Get weight from all weights.
     * @param weights a list of weight.
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
     * Get BMI from all weights.
     * @param weights a list of weight.
     * @return a list of BMI value in double.
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
     */
    public void installToolTipXyChart(ObservableList<XYChart.Data<String, Double>> data) {
        data.stream().forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(d.getXValue() + "\n" + d.getYValue());
            Tooltip.install(d.getNode(), tooltip);
        });
    }

    /**
     * Initialize the weight line chart.
     */
    private void initializeWeightLineChart() {
        String[] date = getDate(weights);
        ArrayList<String> dates = getDates(weights);
        ArrayList<Double> values = getWeights(weights);

        weightLineChart.setAnimated(false);
        weightLineChart.layout();

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        int size = dates.size();
        for (int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), values.get(i)));
        }

        weightLineChart.setLegendVisible(false);
        if (date == null) {
            weightLineChart.setTitle("Weight Line Chart");
        } else {
            weightLineChart.setTitle("Weight Line Chart: [" + date[0] + "~" + date[1] + "]");
        }
        weightLineChart.getData().add(series);

        installToolTipXyChart(series.getData());
    }

    /**
     * Initialize the bmi line chart.
     */
    private void initializeBmiLineChart() {
        String[] date = getDate(weights);
        ArrayList<String> dates = getDates(weights);
        ArrayList<Double> values = getBmis(weights);

        bmiLineChart.setAnimated(false);
        bmiLineChart.layout();

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        int size = dates.size();
        for (int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), values.get(i)));
        }

        bmiLineChart.setLegendVisible(false);
        if (date == null) {
            bmiLineChart.setTitle("BMI Line Chart");
        } else {
            bmiLineChart.setTitle("BMI Line Chart: " + "[" + date[0] + "~" + date[1] + "]");
        }
        bmiLineChart.getData().add(series);

        installToolTipXyChart(series.getData());
    }
}
