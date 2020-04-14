package fithelper.ui.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import fithelper.model.calculator.CalorieCalculatorByDateRange;
import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Get the full calendar view.
 */
public class MonthView extends UiPart<AnchorPane> {
    private static final String FXML = "MonthView.fxml";
    private ArrayList<DayNode> allCalendarDays;
    private AnchorPane view;
    private ObservableList<Entry> foodList;
    private ObservableList<Entry> sportsList;
    private YearMonth currentYearMonth;
    private CalorieCalculatorByDateRange stats;

    @FXML
    private Label monthYearTitle;

    /**
     * Create a calendar view
     */
    public MonthView(LocalDate dateToSet, CalorieCalculatorByDateRange stats,
                     ObservableList<Entry> foodList, ObservableList<Entry> sportsList) {
        super(FXML);
        currentYearMonth = YearMonth.from(dateToSet);
        this.stats = stats;
        this.foodList = foodList;
        this.sportsList = sportsList;
        this.monthYearTitle = monthYearTitle;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        view = new AnchorPane();
        calendar.setPrefSize(60, 40);
        calendar.setGridLinesVisible(false);
        allCalendarDays = new ArrayList<>(42);
        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("S"), new Text("M"), new Text("T"),
            new Text("W"), new Text("T"), new Text("F"), new Text("S")};

        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                DayNode ap = new DayNode(foodList, sportsList);
                ap.setPrefSize(20, 20);
                ap.setStyle("-fx-border-color:white;");
                calendar.add(ap, j, i);
                dayNames[j].setFill(Color.web("#789cce"));
                if (i == 0) {
                    ap.setTopAnchor(dayNames[j], 8.0);
                    ap.setLeftAnchor(dayNames[j], 8.0);
                    ap.getChildren().add(dayNames[j]);
                } else {
                    allCalendarDays.add(ap);
                }
            }
            populateCalendar(currentYearMonth, stats);
            HBox titleBar = new HBox(monthYearTitle);
            AnchorPane.setTopAnchor(titleBar, 0.0);
            AnchorPane.setLeftAnchor(titleBar, 10.0);
            AnchorPane.setTopAnchor(calendar, 20.0);
            monthYearTitle.setTextFill(Color.web("#789cce"));
            view = new AnchorPane(titleBar, calendar);
        }
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth, CalorieCalculatorByDateRange calorieStats) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (DayNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            if (calorieStats.getDailyCalorie().containsKey(calendarDate)) {
                if (calorieStats.getDailyCalorie().get(calendarDate) > 0) {
                    txt.setFill(Color.RED);
                } else if (calorieStats.getDailyCalorie().get(calendarDate) < 0) {
                    txt.setFill(Color.GREEN);
                } else {
                    txt.setFill(Color.web("#789cce"));
                }
            } else {
                txt.setFill(Color.web("#789cce"));
            }
            ap.setDate(calendarDate);
            AnchorPane.setTopAnchor(txt, 8.0);
            AnchorPane.setLeftAnchor(txt, 8.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        monthYearTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    public AnchorPane getView() {
        return view;
    }
}
