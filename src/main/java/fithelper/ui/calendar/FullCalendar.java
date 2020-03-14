package fithelper.ui.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Get the full calendar view.
 */
public class FullCalendar extends UiPart<AnchorPane> {
    private static final String FXML = "FullCalendar.fxml";
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private AnchorPane view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    /**
     * Create a calendar view
     */
    public FullCalendar(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        super(FXML);
        currentYearMonth = YearMonth.now();
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(60, 40);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(20, 20);
                calendar.add(ap, j, i);
                if (i != 0) {
                    allCalendarDays.add(ap);
                }
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("S"), new Text("M"), new Text("T"),
            new Text("W"), new Text("T"), new Text("F"), new Text("S")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(60);
        Integer col = 0;
        for (Text txt: dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(20, 20);
            ap.setBottomAnchor(txt, 0.5);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        HBox titleBar = new HBox(calendarTitle);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(YearMonth.now());
        // Create the calendar view
        view = new AnchorPane(dayLabels, calendar);
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
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 8.0);
            ap.setLeftAnchor(txt, 8.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    public AnchorPane getView() {
        return view;
    }
}
