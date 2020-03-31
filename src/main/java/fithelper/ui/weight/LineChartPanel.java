package fithelper.ui.weight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fithelper.model.weight.Weight;
import fithelper.ui.UiPart;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;

/**
 * An UI for line chart.
 */
public class LineChartPanel extends UiPart<Region> {

    private static final String FXML = "LineChartPanel.fxml";
    private static final String DEFAULT_DATE = "Date";
    private final List<Weight> weights;
    private final String category;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<LocalDate, Double> lineChart;

    public LineChartPanel(ObservableList<Weight> weights, String category) {
        super(FXML);
        this.weights = weights.sorted((o1, o2) -> {
            if (o1.getDate().value.isAfter(o2.getDate().value)) {
                return 1;
            } else {
                return -1;
            }
        });
        this.category = category;
        display();
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
    private ArrayList<LocalDate> getDates(List<Weight> weights){
        ArrayList<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            dates.add(weights.get(i).getDate().value);
        }
        return dates;
    }

    /**
     * Get weight from all weights.
     * @param weights a list of weight.
     * @return a list of weight value in double.
     */
    private ArrayList<Double> getWeights(List<Weight> weights){
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
    private ArrayList<Double> getBmis(List<Weight> weights){
        ArrayList<Double> bmis = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            bmis.add(weights.get(i).getBmi().value);
        }
        return bmis;
    }

    /**
     * Install tooltip for  line chart.
     */
    public static void installToolTipXyChart(ObservableList<XYChart.Data<LocalDate, Double>> data) {
        data.stream().forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(d.getXValue() + "\n" + d.getYValue());
            Tooltip.install(d.getNode(), tooltip);
        });
    }

    /**
     * Set data for line chart to be displayed.
     */
    private void display() {
        String[] date = getDate(weights);
        ArrayList<LocalDate> dates = getDates(weights);
        ArrayList<Double> values;
        if ("Weight".equals(category)) {
            values = getWeights(weights);
        } else {
            values = getBmis(weights);
        }

        lineChart.setAnimated(false);
        lineChart.layout();

        xAxis.setLabel(DEFAULT_DATE);
        yAxis.setLabel(category);

        XYChart.Series<LocalDate, Double> series = new XYChart.Series<>();

        int size = dates.size();
        for (int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(dates.get(i), values.get(i)));
        }

        lineChart.setLegendVisible(false);
        lineChart.setTitle(category + ": " + date[0] + " - " + date[1]);
        lineChart.getData().add(series);

        installToolTipXyChart(series.getData());
    }
}