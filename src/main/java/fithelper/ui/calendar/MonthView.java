package fithelper.ui.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;

import fithelper.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Get the full calendar view.
 */
public class MonthView extends UiPart<AnchorPane> {
    private static final String FXML = "MonthView.fxml";
    private ArrayList<DayNode> allCalendarDays;
    private AnchorPane view;
    private YearMonth currentYearMonth;
    private GridPane calendar;

    @FXML
    private Label monthYearTitle;

    /**
     * Create a calendar view
     */
    public MonthView(LocalDateTime dateToSet) {
        super(FXML);
        currentYearMonth = YearMonth.from(dateToSet);
        // Create the calendar grid pane
        calendar = new GridPane();
        view = new AnchorPane();
        calendar.setPrefSize(60, 40);
        calendar.setGridLinesVisible(false);
        allCalendarDays = new ArrayList<>(42);
        Label monthYearTitle = new Label();
        monthYearTitle.setText(currentYearMonth.getMonth().toString() + " "
                + String.valueOf(currentYearMonth.getYear()));

        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("S"), new Text("M"), new Text("T"),
            new Text("W"), new Text("T"), new Text("F"), new Text("S")};

        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                DayNode ap = new DayNode();
                ap.setPrefSize(20, 20);
                ap.setStyle("-fx-border-color:white;");
                calendar.add(ap, j, i);
                dayNames[j].setFill(Color.DARKORANGE);
                if (i == 0) {
                    ap.setTopAnchor(dayNames[j], 8.0);
                    ap.setLeftAnchor(dayNames[j], 8.0);
                    ap.getChildren().add(dayNames[j]);
                } else {
                    allCalendarDays.add(ap);
                }
            }
            // Populate calendar with the appropriate day numbers
            populateCalendar(currentYearMonth);
            // Create the calendar view
            AnchorPane.setTopAnchor(monthYearTitle, 0.0);
            AnchorPane.setLeftAnchor(monthYearTitle, 10.0);
            AnchorPane.setTopAnchor(calendar, 20.0);
            monthYearTitle.setTextFill(Color.web("#ff7f50"));
            view = new AnchorPane(monthYearTitle, calendar);
        }
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
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
            txt.setFill(Color.DARKORANGE);
            ap.setDate(calendarDate);
            AnchorPane.setTopAnchor(txt, 8.0);
            AnchorPane.setLeftAnchor(txt, 8.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
    }

    public AnchorPane getView() {
        return view;
    }
}
