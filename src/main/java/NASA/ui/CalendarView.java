package nasa.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarView.fxml";
    private int currentYear;
    private int currentMonth;

    @FXML
    private Label monthAndYear;

    @FXML
    private GridPane calendarGrid;

    public CalendarView() {
        super(FXML);

        // initialize the calendar grid and its contents
        LocalDateTime currentDateTime = LocalDateTime.now();
        currentYear = currentDateTime.getYear();
        currentMonth = currentDateTime.getMonthValue();

        // update the Label and the grids
        monthAndYear.setText(String.format("%s %s", currentMonth, currentYear));

        // update calendar
        initializeWholeCalendar();
    }

    private void initializeCalendarHeader() {
        for (int i = 1; i <= 7; i++) {
            VBox dayHeader = new VBox();
            GridPane.setVgrow(dayHeader, Priority.NEVER);
            Label day = new Label();
            day.setText(DayOfWeek.of(i).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            dayHeader.getChildren().add(day);
            calendarGrid.add(dayHeader, i - 1, 0);
        }
    }

    private void initializeDateGrids() {
        LocalDateTime monthDetails = LocalDateTime.of(currentYear, currentMonth, 1, 0, 0);
        int firstDayOfMonth = monthDetails.getDayOfWeek().getValue(); // get when the first date lies on which day
        int totalDaysInMonth = monthDetails.getMonth().maxLength(); // get total days in current month

        // need to adjust totalDaysInMonth for Feb (leap year)
        LocalDate date = LocalDate.of(currentYear, currentMonth, 1);
        if (!date.isLeapYear() && date.getMonth() == Month.FEBRUARY) {
            totalDaysInMonth--; // need to minus 1 as not leap year
        }

        int nullDays = firstDayOfMonth - 1;
        System.out.println(nullDays);
        int currentDate = 1;

        // populate the first row of the calendar grid
        for (int i = 0; i < 7; i++) {
            VBox dateContent = new VBox();
            GridPane.setVgrow(dateContent, Priority.ALWAYS);

            if (i < nullDays) {
                // not in current month, set to black color
                dateContent.setStyle("-fx-background-color:#000000");
            } else {
                // set to purple color plus add date number
                dateContent.setStyle("-fx-background-color:#5dbcd2");
                Label dateLabel = new Label();
                dateLabel.setText(Integer.toString(currentDate));
                dateLabel.setStyle("-fx-text-fill:#FFFFFF");
                dateContent.getChildren().add(dateLabel);
                currentDate++;
            }
            calendarGrid.add(dateContent, i, 1);
        }

        // populate the rest of the grids as per normal
        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                VBox dateContent = new VBox();
                GridPane.setVgrow(dateContent, Priority.ALWAYS);

                // check if current grid is still within the month
                if (currentDate <= totalDaysInMonth) {
                    dateContent.setStyle("-fx-background-color:#5dbcd2");
                    Label dateLabel = new Label();
                    dateLabel.setText(Integer.toString(currentDate));
                    dateLabel.setStyle("-fx-text-fill:#FFFFFF");
                    dateContent.getChildren().add(dateLabel);
                    currentDate++;
                } else {
                    // create a black pane
                    dateContent.setStyle("-fx-background-color:#000000");
                }
                calendarGrid.add(dateContent, j, i);
            }
        }
    }

    private void initializeWholeCalendar() {
        initializeCalendarHeader();
        initializeDateGrids();
    }
}
