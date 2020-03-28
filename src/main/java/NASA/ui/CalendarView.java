package nasa.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Lesson;
import nasa.model.module.Module;

public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarView.fxml";
    private int currentYear;
    private int currentMonth;

    @FXML
    private Label monthAndYear;

    @FXML
    private GridPane calendarGrid;

    public CalendarView(ObservableList<Module> moduleObservableList) {
        super(FXML);

        // initialize the calendar grid and its contents
        LocalDateTime currentDateTime = LocalDateTime.now();
        currentYear = currentDateTime.getYear();
        currentMonth = currentDateTime.getMonthValue();

        // update the Label and the grids
        monthAndYear.setText(String.format("%s %s", Month.of(currentMonth), Year.of(currentYear)));

        // update calendar
        initializeWholeCalendar();

        // update contents in calendar
        loadActivities(moduleObservableList);
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
            dateContent.setId(Integer.toString(currentDate));
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
                dateContent.setId(Integer.toString(currentDate));

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

    private void loadActivities(ObservableList<Module> moduleObservableList) {
        HashMap<Integer, ArrayList<Activity>> activityHashMap = new HashMap<>();
        for (Module module : moduleObservableList) {
            ObservableList<Activity> activityObservableList =
                module.getFilteredActivityList();
            for (Activity activity : activityObservableList) {
                if (activity.occurInMonth(currentMonth)) {
                    int activityDate = getMonth(activity);
                    if (activityHashMap.containsKey(activityDate)) {
                        activityHashMap.get(activityDate).add(activity);
                    } else {
                        ArrayList<Activity> activities = new ArrayList<>();
                        activities.add(activity);
                        activityHashMap.put(activityDate, activities);
                    }
                }
            }
        }

        // now we populate those grids that has activities
        for (Integer i : activityHashMap.keySet()) {
            ArrayList<Activity> dateActivities = activityHashMap.get(i);
            int size = dateActivities.size();

            if (size > 0) {
                Label activityLabel = getActivityLabel(dateActivities.get(0));
                Node node = calendarGrid.lookup("#" + Integer.toString(i));
                VBox dateContent = (VBox) node;
                dateContent.getChildren().add(activityLabel);

                // add the others... label
                if (size > 1) {
                    int left = size - 1;
                    Label leftActivities = new Label();
                    leftActivities.setText(String.format("%d other activities", left));
                    leftActivities.setPadding(new Insets(0, 5, 0, 5));
                    dateContent.getChildren().add(leftActivities);
                }
            }
        }
    }

    private Label getActivityLabel(Activity activity) {
        Label activityLabel = new Label();
        activityLabel.setText(activity.toString());
        activityLabel.setPadding(new Insets(0, 5, 0, 5));
        if (activity instanceof Deadline) {
            // color it red
            activityLabel.setStyle("-fx-background-color:red -fx-background-radius: 10, 10, 10, 10");
        } else if (activity instanceof Event) {
            // color it yellow
            activityLabel.setStyle("-fx-background-color:yellow -fx-background-radius: 10, 10, 10, 10");
        } else {
            // color it green
            activityLabel.setStyle("fx-background-color:green -fx-background-radius: 10, 10, 10, 10 ");
        }
        return activityLabel;
    }

    private void initializeWholeCalendar() {
        initializeCalendarHeader();
        initializeDateGrids();
    }

    private int getMonth(Activity activity) {
        if (activity instanceof Deadline) {
            return ((Deadline) activity).getDueDate().getDate().getDayOfMonth();
        } else if (activity instanceof Event) {
            return ((Event) activity).getDateFrom().getDate().getDayOfMonth();
        } else {
            return ((Lesson) activity).getDateFrom().getDate().getDayOfMonth();
        }
    }
}
