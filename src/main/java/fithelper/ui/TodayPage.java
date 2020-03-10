package fithelper.ui;

import fithelper.commons.core.LogsCenter;
import fithelper.model.entry.Entry;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

/**
 * Controller class for order page.
 * An order page contains order cards and a statistics bar.
 */
public class TodayPage extends UiPart<AnchorPane> {
    private static final String FXML = "TodayPage.fxml";
    private final Logger logger = LogsCenter.getLogger(TodayPage.class);

    @FXML
    private ListView<Entry> foodListView;

    @FXML
    private ListView<Entry> sportListView;

    @FXML
    private Label undoneFoodCounter;

    @FXML
    private Label doneFoodCounter;

    @FXML
    private Label undoneSportCounter;

    @FXML
    private Label doneSportCounter;

    /**
     * Creates an order page displaying orders from {@code orderList}.
     */
    public TodayPage(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        super(FXML);

        logger.info("Initializing Order Page");

        initializeListView(foodList, sportList);
    }

    private void initializeListView(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        foodListView.setItems(foodList);
        foodListView.setCellFactory(listView -> new FoodListViewCell());
        sportListView.setItems(sportList);
        sportListView.setCellFactory(listView -> new SportListViewCell());
    }

    private void initializeListener(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        updateFoodStatistics(foodList);
        foodList.addListener((ListChangeListener<Entry>) change ->
                updateFoodStatistics(foodList)
        );
        updateSportStatistics(sportList);
        sportList.addListener((ListChangeListener<Entry>) change ->
                updateSportStatistics(sportList)
        );
    }

    private void updateFoodStatistics(ObservableList<Entry> foodList) {
        int undoneCount = 0;
        int doneCount = 0;

        for (Entry entry : foodList) {
            if (entry.getStatus().value.equalsIgnoreCase("false")) {
                undoneCount++;
            } else {
                doneCount++;
            }
        }

        undoneFoodCounter.setText(undoneCount + " undone");
        doneFoodCounter.setText(doneCount + " completed");
    }

    private void updateSportStatistics(ObservableList<Entry> sportList) {
        int undoneCount = 0;
        int doneCount = 0;

        for (Entry entry : sportList) {
            if (entry.getStatus().value.equalsIgnoreCase("false")) {
                undoneCount++;
            } else {
                doneCount++;
            }
        }

        undoneSportCounter.setText(undoneCount + " sport plans undone");
        doneSportCounter.setText(doneCount + " sport plans completed");
    }

    static class FoodListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);
            updateSelected(false);
            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodCard(entry).getRoot());
            }
        }
    }

    static class SportListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry sport, boolean empty) {
            super.updateItem(sport, empty);
            updateSelected(false);
            if (empty || sport == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SportCard(sport).getRoot());
            }
        }
    }
}

