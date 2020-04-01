package nasa.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.module.Module;

/**
 * Panel containing the list of modules.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);

    @FXML
    public PieChart pieChart;
    @FXML
    public BarChart<String, Integer> barChart;
    @FXML
    public CategoryAxis xAxis;
    @FXML
    public NumberAxis yAxis;

    public StatisticsPanel(ObservableList<Module> moduleList) {
        super(FXML);
        //Pie chart
        List<PieChart.Data> pieData = new ArrayList<>();
        for (Module module : moduleList) {
            pieData.add(new PieChart.Data(module.getModuleCode().toString(),
                    module.getActivities().getActivityList().size()));
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(pieData);
        pieChart.setData(chartData);
        pieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });

        //Bar chart

        XYChart.Series<String, Integer> barData= new XYChart.Series();
        for (Module module : moduleList) {
            barData.getData().add(new XYChart.Data(module.getModuleCode().toString(),
                    module.getActivities().getActivityList().size()));
        }
        barChart.setData(FXCollections.observableArrayList(barData));

    }
}
