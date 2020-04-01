package nasa.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
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
    private Label statistics;
    @FXML
    public PieChart pieChart;

    public StatisticsPanel(ObservableList<Module> moduleList) {
        super(FXML);
        List<PieChart.Data> data = new ArrayList<>();
        for (Module module : moduleList) {
            data.add(new PieChart.Data(module.getModuleCode().toString(),
                    module.getActivities().getActivityList().size()));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data);
        pieChart.setData(pieChartData);
        pieChart.setTitle("PLEASE");
    }
}
