package fithelper.ui.calendar;

import java.time.LocalDate;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.calculator.CalorieCalculatorByDateRange;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Display two calendars.
 */
public class ListPanel extends UiPart<AnchorPane> {
    private static final String FXML = "ListPanel.fxml";
    private ObservableList<Entry> foodList;
    private ObservableList<Entry> sportList;
    private DaysCard daysPage;
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private CalorieCalculatorByDateRange stats;


    @FXML
    private AnchorPane monthViewPlaceholder;

    @FXML
    private AnchorPane daysPagePlaceholder;

    @FXML
    private StackPane dailyStatusListPlaceholder;

    /**
     * Creates a calendar page displaying two components from {@code }.
     */
    public ListPanel(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        super(FXML);
        this.foodList = foodList;
        this.sportList = sportList;
        logger.info("Initializing Calendar Page");
        daysPage = new DaysCard(foodList, sportList, LocalDate.now());
        set(LocalDate.now());
    }

    // set date reference based on parameter date
    public void set(LocalDate date) {
        getGenerator(date);
        MonthView monthView = new MonthView(date, stats, foodList, sportList);
        monthViewPlaceholder.getChildren().clear();
        monthViewPlaceholder.getChildren().add(monthView.getView());
        daysPagePlaceholder.getChildren().clear();
        daysPage = new DaysCard(foodList, sportList, date);
        daysPagePlaceholder.getChildren().add(daysPage.getRoot());
        dailyStatusListPlaceholder.getChildren().clear();
        DailyStatusList dailyStatusList = new DailyStatusList(foodList, sportList, date);
        dailyStatusListPlaceholder.getChildren().add(dailyStatusList.getRoot());
    }

    public void getGenerator(LocalDate givenDate) {
        LocalDate start = givenDate.withDayOfMonth(1);
        LocalDate end = givenDate.withDayOfMonth(givenDate.lengthOfMonth());
        stats = new CalorieCalculatorByDateRange(foodList, sportList, start, end);
    }
}
