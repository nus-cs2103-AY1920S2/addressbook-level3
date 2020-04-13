package seedu.recipe.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.GoalCount;

/**
 * Panel containing the list of recipes.
 */
public class CookedListPanel extends UiPart<Region> {
    private static final String FXML = "CookedListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CookedListPanel.class);
    private final String styleHeader = "-fx-font-family: \"Segoe UI\";\n"
            + "-fx-text-fill: #FFFFFF;\n"
            + "-fx-font-weight: bold;\n"
            + "-fx-font-size: 20pt;";
    @FXML
    private ListView<Record> cookedListView;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label cooked;

    public CookedListPanel(ObservableList<Record> recordList, ObservableList<GoalCount> goalCountList) {
        super(FXML);
        cooked.setStyle(styleHeader);
        cookedListView.setItems(recordList);
        cookedListView.setCellFactory(listView -> new RecordListViewCell());
        setChart(goalCountList);
    }

    public void setChart(ObservableList<GoalCount> goalCountList) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (GoalCount goalCount : goalCountList) {
            String goalName = goalCount.getGoal().goalName;
            int count = goalCount.getCount();
            pieChartData.add(new PieChart.Data(goalName, count));
        }

        pieChart.setTitle("My Healthy Plate");
        pieChart.setData(pieChartData);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Record} using a {@code RecordCard}.
     */
    class RecordListViewCell extends ListCell<Record> {
        @Override
        protected void updateItem(Record record, boolean empty) {
            super.updateItem(record, empty);
            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CookedCard(record, getIndex() + 1).getRoot());
            }
        }
    }

}
