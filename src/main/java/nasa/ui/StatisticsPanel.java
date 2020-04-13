package nasa.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import nasa.commons.core.LogsCenter;
import nasa.model.activity.Deadline;
import nasa.model.module.Module;

/**
 * Panel showing statistics on modules.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);

    @FXML
    private PieChart pieChart;
    @FXML
    private StackedBarChart<String, Integer> stackedBarChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;


    public StatisticsPanel(ObservableList<Module> moduleObservableList) {
        super(FXML);
        pieChart.setMinWidth(500);
        stackedBarChart.setMinWidth(500);
        loadStatistics(moduleObservableList);
        moduleObservableList.addListener(new ListChangeListener<Module>() {
            @Override
            public void onChanged(Change<? extends Module> c) {
                resetStatistics();
                loadStatistics(moduleObservableList);
            }
        });
        updateStatistics(moduleObservableList);
    }


    /**
     * Method to update statistics as activities are edited/removed/added.
     * @param moduleObservableList List of modules
     */
    private void updateStatistics(ObservableList<Module> moduleObservableList) {
        for (Module module : moduleObservableList) {
            ObservableList<Deadline> deadlineObservableList = module.getFilteredDeadlineList();
            deadlineObservableList.addListener(new ListChangeListener<Deadline>() {
                @Override
                public void onChanged(Change<? extends Deadline> c) {
                    resetStatistics();
                    loadStatistics(moduleObservableList);
                }
            });
        }
    }

    /**
     * Set statistics.
     * @param moduleList
     */

    private void loadStatistics(ObservableList<Module> moduleList) {

        List<PieChart.Data> pieData = new ArrayList<>();
        for (Module module : moduleList) {
            pieData.add(new PieChart.Data(module.getModuleCode().toString(),
                    module.getFilteredDeadlineList().size()));
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(pieData);

        pieChart.setData(chartData);
        chartData.forEach(data ->
                data.nameProperty().bind(data.pieValueProperty().getValue() > 1
                        ? Bindings.concat(
                                data.getName(), " - ", data.pieValueProperty().intValue(), " deadlines"
                        )
                        : Bindings.concat(
                                data.getName(), " - ", data.pieValueProperty().intValue(), " deadline"
                        )
                )
        );

        //Bar chart
        XYChart.Series<String, Integer> tasksCompleted = new XYChart.Series();
        for (Module module : moduleList) {
            tasksCompleted.getData().add(new XYChart.Data(module.getModuleCode().toString(),
                    module.getFilteredDeadlineList()
                            .stream()
                            .filter(activity -> activity.isDone())
                            .count()));
        }
        tasksCompleted.setName("Completed");

        XYChart.Series<String, Integer> tasksNotCompleted = new XYChart.Series();
        for (Module module : moduleList) {
            tasksNotCompleted.getData().add(new XYChart.Data(module.getModuleCode().toString(),
                    module.getFilteredDeadlineList()
                            .stream()
                            .filter(activity -> !activity.isDone())
                            .count()));
        }
        tasksNotCompleted.setName("Not completed");
        stackedBarChart.getData().addAll(tasksCompleted, tasksNotCompleted);

    }

    private void resetStatistics() {
        pieChart.getData().clear();
        stackedBarChart.getData().clear();
    }
}
