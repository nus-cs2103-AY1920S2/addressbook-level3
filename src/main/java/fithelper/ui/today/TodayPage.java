package fithelper.ui.today;

import java.util.ArrayList;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.calculator.CalorieCalculator;
import fithelper.model.calculator.FeedbackGenerator;
import fithelper.model.calculator.PerformanceCalculator;
import fithelper.model.entry.Entry;
import fithelper.ui.FoodCard;
import fithelper.ui.SportCard;
import fithelper.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
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

    private int sportDone;

    private double percentDone;

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

    @FXML
    private PieChart foodCaloriePieChart;

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

        initializeFoodCaloriePieChart(foodList);

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
     * Initializes the food calorie pie chart.
     * @param foodList an observable list of sports entries
     */
    private void initializeFoodCaloriePieChart(ObservableList<Entry> foodList) {
        ArrayList<PieChart.Data> pieChartDataList = getPieChartDataArrayList(foodList);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(pieChartDataList);

        foodCaloriePieChart.setData(pieChartData);
        setFoodCaloriePieChartProperty();
    }

    /**
     * Generates an arrayList of PieChart data from the foodList.
     * @param foodList an observable list of sports entries
     */
    private ArrayList<PieChart.Data> getPieChartDataArrayList(ObservableList<Entry> foodList) {
        ArrayList<String> names = getFoodNameList(foodList);
        ArrayList<Integer> calories = getFoodCalorieList(foodList);
        ArrayList<PieChart.Data> pieChartDataList = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            //String foodName = names.get(i);
            int foodCalorie = calories.get(i);
            pieChartDataList.add(new PieChart.Data(String.valueOf(i + 1), foodCalorie));
        }

        return pieChartDataList;
    }

    /**
     * Generates the list of done food names;
     * @param foodList
     * @return
     */
    private ArrayList<String> getFoodNameList(ObservableList<Entry> foodList) {
        ArrayList<String> names = new ArrayList<>();
        for (Entry entry: foodList) {
            names.add(entry.getName().toString());
        }

        return names;
    }

    /**
     * Generates the list of done food calories;
     * @param foodList
     * @return
     */
    private ArrayList<Integer> getFoodCalorieList(ObservableList<Entry> foodList) {
        ArrayList<Integer> calories = new ArrayList<>();
        for (Entry entry: foodList) {
            calories.add((int) entry.getCalorie().value);
        }
        return calories;
    }

    /**
     * Sets the property of food calorie pie chart.
     */
    private void setFoodCaloriePieChartProperty() {
        foodCaloriePieChart.setClockwise(true);
        foodCaloriePieChart.setLabelLineLength(20);
        foodCaloriePieChart.setLabelsVisible(true);
        foodCaloriePieChart.setLegendVisible(false);
        foodCaloriePieChart.setStartAngle(180);
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
        updateFoodCaloriePieChart(foodList);
        updateFeedback();
    }

    /**
     * Updates the sport entry list statistics.
     * @param sportList an observable list of sport entries
     */
    private void updateSportStatistics(ObservableList<Entry> sportList) {
        updateSportCounter(sportList);
        updateSportCalorie(sportList);
        updatePerformance(sportList);
        updateFeedback();
    }

    /**
     * Updates the food entry list statistics.
     * @param foodList an observable list of food entries
     */
    private void updateFoodCounter(ObservableList<Entry> foodList) {
        PerformanceCalculator foodPerformanceCalculator = new PerformanceCalculator(foodList);

        int foodUndone = foodPerformanceCalculator.getUndoneCounter();
        int foodDone = foodPerformanceCalculator.getDoneCounter();

        undoneFoodCounter.setText(foodUndone + " undone");
        doneFoodCounter.setText(foodDone + " completed");
    }

    /**
     * Updates the food entry list statistics.
     * @param sportList an observable list of food entries
     */
    private void updateSportCounter(ObservableList<Entry> sportList) {
        PerformanceCalculator sportPerformanceCalculator = new PerformanceCalculator(sportList);

        int sportUndone = sportPerformanceCalculator.getUndoneCounter();
        this.sportDone = sportPerformanceCalculator.getDoneCounter();

        undoneSportCounter.setText(sportUndone + " sport plans undone");
        doneSportCounter.setText(sportDone + " sport plans completed");
    }


    /**
     * Updates the food entry list statistics.
     * @param foodList an observable list of food entries
     */
    private void updateFoodCalorie(ObservableList<Entry> foodList) {
        CalorieCalculator foodCalorieCalculator = new CalorieCalculator(foodList);
        foodCalorie = foodCalorieCalculator.getFoodCalorie();
        logger.info("foodCalorie: " + foodCalorie);
        calorieGain.setText("food adds in: " + foodCalorie + " kcal");
        difCalorie = foodCalorie - sportCalorie;
        totalCalorie.setText("total calorie: " + difCalorie + " kcal");
    }

    /**
     * Updates the foodCaloriePieChart.
     * @param foodList an observable list of food entries
     */
    private void updateFoodCaloriePieChart(ObservableList<Entry> foodList) {
        initializeFoodCaloriePieChart(foodList);
    }

    /**
     * Updates the food entry list statistics.
     * @param sportList an observable list of food entries
     */
    private void updateSportCalorie(ObservableList<Entry> sportList) {
        CalorieCalculator sportCalorieCalculator = new CalorieCalculator(sportList);
        sportCalorie = sportCalorieCalculator.getSportsCalorie();
        calorieConsume.setText("sports burn: " + sportCalorie + " kcal");
        difCalorie = foodCalorie - sportCalorie;
        totalCalorie.setText("totdal calorie: " + difCalorie + " kcal");
    }

    /**
     * Updates the performance calculation of today's food and sport list
     * @param sportList an observable list of sport entries
     */
    private void updatePerformance(ObservableList<Entry> sportList) {
        percentDone = this.sportDone * 100.0 / sportList.size();
        performance.setText("Sports Task Completion: " + (int) this.percentDone + "%");
        logger.info("task modification: " + this.percentDone);
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

    /**
     * Generates and updates the feedback box based on the task completion data.
     */
    private void updateFeedback() {
        FeedbackGenerator feedbackGenerator = new FeedbackGenerator(this.percentDone, this.difCalorie);
        logger.info("generate feedback: " + percentDone);
        String todayFeedback = feedbackGenerator.generateFeedback();
        feedback.setText(todayFeedback);
    }

}

