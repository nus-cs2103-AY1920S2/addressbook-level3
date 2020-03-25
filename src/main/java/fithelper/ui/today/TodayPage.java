package fithelper.ui.today;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.calculator.CalorieCalculator;
import fithelper.model.entry.Entry;
import fithelper.ui.FoodCard;
import fithelper.ui.SportCard;
import fithelper.ui.UiPart;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for today page.
 * An order page contains order cards and a statistics bar.
 */
public class TodayPage extends UiPart<AnchorPane> {
    private static final String FXML = "TodayPage.fxml";
    private final Logger logger = LogsCenter.getLogger(TodayPage.class);

    private double foodCalorie;
    private double sportCalorie;
    private double difCalorie;

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

    @FXML
    private Label calorieGain;

    @FXML
    private Label calorieConsume;

    @FXML
    private Label totalCalorie;

    @FXML
    private Label performance;

    @FXML
    private Label feedback;

    /**
     * Creates an order page displaying orders from {@code orderList}.
     */
    public TodayPage(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        super(FXML);

        logger.info("Initializing Today Page");

        foodCalorie = 0.0;
        sportCalorie = 0.0;
        difCalorie = 0.0;

        initializeFoodListView(foodList);
        initializeSportListView(sportList);

        initializeFoodListener(foodList);
        initializeSportListener(sportList);
    }

    /**
     * Initializes the list view.
     * @param foodList an observable list of food entries
     */
    private void initializeFoodListView(ObservableList<Entry> foodList) {
        foodListView.setItems(foodList);
        foodListView.setCellFactory(listView -> new FoodListViewCell());
    }

    /**
     * Initializes the list view.
     * @param sportList an observable list of sports entries
     */
    private void initializeSportListView(ObservableList<Entry> sportList) {
        sportListView.setItems(sportList);
        sportListView.setCellFactory(listView -> new SportListViewCell());
    }

    /**
     * Initializes the listeners.
     * @param foodList an observable list of food entries
     */
    public void initializeFoodListener(ObservableList<Entry> foodList) {
        updateFoodStatistics(foodList);
        foodList.addListener((ListChangeListener<Entry>) change ->
                updateFoodStatistics(foodList)
        );
    }

    /**
     * Initializes the listeners.
     * @param sportList an observable list of sports entries
     */
    public void initializeSportListener(ObservableList<Entry> sportList) {
        updateSportStatistics(sportList);
        sportList.addListener((ListChangeListener<Entry>) change ->
                updateSportStatistics(sportList)
        );
    }

    /**
     * Updates the food entry list statistics.
     * @param foodList an observable list of food entries
     */
    private void updateFoodStatistics(ObservableList<Entry> foodList) {
        updateFoodCounter(foodList);
        updateFoodCalorie(foodList);
    }

    /**
     * Updates the sport entry list statistics.
     * @param sportList an observable list of sport entries
     */
    private void updateSportStatistics(ObservableList<Entry> sportList) {
        updateSportCounter(sportList);
        updateSportCalorie(sportList);
    }

    /**
     * Updates the food entry list statistics.
     * @param foodList an observable list of food entries
     */
    private void updateFoodCounter(ObservableList<Entry> foodList) {
        int undoneCount = 0;
        int doneCount = 0;

        for (Entry entry : foodList) {
            if (entry.getStatus().value.equalsIgnoreCase("Done")) {
                doneCount++;
            } else {
                undoneCount++;
            }
        }

        undoneFoodCounter.setText(undoneCount + " undone");
        doneFoodCounter.setText(doneCount + " completed");
    }

    /**
     * Updates the food entry list statistics.
     * @param sportList an observable list of food entries
     */
    private void updateSportCounter(ObservableList<Entry> sportList) {
        int undoneCount = 0;
        int doneCount = 0;

        for (Entry entry : sportList) {
            if (entry.getStatus().value.equalsIgnoreCase("Undone")) {
                undoneCount++;
            } else {
                doneCount++;
            }
        }

        undoneSportCounter.setText(undoneCount + " sport plans undone");
        doneSportCounter.setText(doneCount + " sport plans completed");
    }


    /**
     * Updates the food entry list statistics.
     * @param foodList an observable list of food entries
     */
    private void updateFoodCalorie(ObservableList<Entry> foodList) {
        CalorieCalculator foodCalorieCalculator = new CalorieCalculator(foodList);
        foodCalorie = foodCalorieCalculator.getFoodCalorie();
        logger.info("foodCalorie: " + foodCalorie);
        calorieGain.setText("today food calorie: " + foodCalorie);
        difCalorie = foodCalorie - sportCalorie;
        totalCalorie.setText("total calorie for today: " + difCalorie);
    }

    /**
     * Updates the food entry list statistics.
     * @param sportList an observable list of food entries
     */
    private void updateSportCalorie(ObservableList<Entry> sportList) {
        CalorieCalculator sportCalorieCalculator = new CalorieCalculator(sportList);
        sportCalorie = sportCalorieCalculator.getSportsCalorie();
        calorieConsume.setText("today sports calorie: " + sportCalorie);
        difCalorie = foodCalorie - sportCalorie;
        totalCalorie.setText("total calorie for today: " + difCalorie);
    }

    /**
     * Constructs foodListView Cell class.
     */
    static class FoodListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);
            updateSelected(false);
            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodCard(entry, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Constructs sportListView Cell class.
     */
    static class SportListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry sport, boolean empty) {
            super.updateItem(sport, empty);
            updateSelected(false);
            if (empty || sport == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SportCard(sport, getIndex() + 1).getRoot());
            }
        }
    }

}

