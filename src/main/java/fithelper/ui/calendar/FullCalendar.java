package fithelper.ui.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import fithelper.model.entry.Entry;
import fithelper.ui.UiPart;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Get the full calendar view.
 */
public class FullCalendar extends UiPart<AnchorPane> {
    private static final String FXML = "FullCalendar.fxml";
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(42);
    private AnchorPane view;
    private YearMonth currentYearMonth;

    @FXML
    private Label monthYearTitle;

    /**
     * Create a calendar view
     */
    public FullCalendar(ObservableList<Entry> foodList, ObservableList<Entry> sportList) {
        super(FXML);
        currentYearMonth = YearMonth.now();
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(60, 40);
        calendar.setGridLinesVisible(false);
        Label monthYearTitle = new Label();
        monthYearTitle.setText(currentYearMonth.getMonth().toString() + " "
            + String.valueOf(currentYearMonth.getYear()));

        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("S"), new Text("M"), new Text("T"),
            new Text("W"), new Text("T"), new Text("F"), new Text("S")};

        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(20, 20);
                ap.setStyle("-fx-border-color:white;");
                calendar.add(ap, j, i);
                if (i == 0) {
                    ap.setTopAnchor(dayNames[j], 8.0);
                    ap.setLeftAnchor(dayNames[j], 8.0);
                    ap.getChildren().add(dayNames[j]);
                } else {
                    allCalendarDays.add(ap);
                }
            }
        }

        // Populate calendar with the appropriate day numbers
        populateCalendar(YearMonth.now());
        // Create the calendar view
        view = new AnchorPane(monthYearTitle, calendar);
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
