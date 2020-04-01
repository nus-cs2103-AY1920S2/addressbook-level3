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

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Lesson;
import nasa.model.module.Module;

/**
 * UI component to represent the calendar view.
 */
public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarView.fxml";
    private int currentYear;
    private int currentMonth;

    @FXML
    private Label monthAndYear;

    @FXML
    private GridPane calendarGrid;

    /**
     * Constructor for the controller.
     * @param moduleObservableList modules listed
     */
    public CalendarView(ObservableList<Module> moduleObservableList) {
        super(FXML);

        // initialize the calendar grid and its contents
        LocalDateTime currentDateTime = LocalDateTime.now();
        currentYear = currentDateTime.getYear();
        currentMonth = currentDateTime.getMonthValue();

        // update the Label and the grids
        monthAndYear.setText(String.format("%s %s", Month.of(currentMonth), Year.of(currentYear)));
        monthAndYear.setTextFill(Color.WHITE);

        // update calendar
        initializeWholeCalendar();

        // update contents in calendar
        loadActivities(moduleObservableList);

        // allow updating of calendar
        // TODO: whenever a module is added, do this also
        moduleObservableList.addListener(new ListChangeListener<Module>() {
            @Override
            public void onChanged(Change<? extends Module> c) {
                resetCalendar();
                loadActivities(moduleObservableList);
                updateCalendar(moduleObservableList);
            }
        });
        updateCalendar(moduleObservableList);
    }

    /**
     * Method to update the calendar as activities are editied/removed/added.
     * @param moduleObservableList List of modules
     */
    private void updateCalendar(ObservableList<Module> moduleObservableList) {
        for (Module module : moduleObservableList) {
            ObservableList<Activity> activityObservableList = module.getFilteredActivityList();
            activityObservableList.addListener(new ListChangeListener<Activity>() {
                @Override
                public void onChanged(Change<? extends Activity> c) {
                    resetCalendar();
                    loadActivities(moduleObservableList);
                }
            });
        }
    }

    /**
     * Initialize the header for the calendar.
     */
    private void initializeCalendarHeader() {
        for (int i = 1; i <= 7; i++) {
            VBox dayHeader = new VBox();
            GridPane.setVgrow(dayHeader, Priority.NEVER);
            Label day = new Label();
            day.setText(DayOfWeek.of(i).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            day.setTextFill(Color.WHITE);
            dayHeader.getChildren().add(day);
            dayHeader.getStyleClass().add("calendarGrid");
            calendarGrid.add(dayHeader, i - 1, 0);
        }
    }

    /**
     * Initialize the date grids for the calendar.
     */
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
        int currentDate = 1;

        // populate the first row of the calendar grid
        for (int i = 0; i < 7; i++) {
            VBox dateContent = new VBox();
            GridPane.setVgrow(dateContent, Priority.ALWAYS);
            dateContent.setId(Integer.toString(currentDate));
            if (i < nullDays) {
                // not in current month, set to black color
                dateContent.setStyle("-fx-background-color:black");
            } else {
                // set to purple color plus add date number
                dateContent.setStyle("-fx-background-color:#361350");
                Label dateLabel = new Label();
                dateLabel.setText(Integer.toString(currentDate));
                dateLabel.setStyle("-fx-text-fill:#FFFFFF");
                dateLabel.setPadding(new Insets(5));
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
                    dateContent.setStyle("-fx-background-color:#361350");
                    Label dateLabel = new Label();
                    dateLabel.setText(Integer.toString(currentDate));
                    dateLabel.setStyle("-fx-text-fill:#FFFFFF");
                    dateLabel.setPadding(new Insets(5));
                    dateContent.getChildren().add(dateLabel);
                    currentDate++;
                } else {
                    // create a black pane
                    dateContent.setStyle("-fx-background-color:black");
                }
                calendarGrid.add(dateContent, j, i);
            }
        }
    }

    /**
     * Load all activities into the calendar.
     * @param moduleObservableList modules in the application
     */
    public void loadActivities(ObservableList<Module> moduleObservableList) {
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
                Node node = calendarGrid.lookup("#" + Integer.toString(i));
                VBox dateContent = (VBox) node;
                int counter = 1;
                for (Activity activity : dateActivities) {
                    if (counter <= 3) {
                        Label activityLabel = getActivityLabel(activity);
                        dateContent.getChildren().add(activityLabel);
                        counter++;
                    } else {
                        break;
                    }
                }
                // add the others... label
                if (size > 3) {
                    int left = size - 3;
                    Label leftActivities = new Label();
                    leftActivities.setText(String.format("%d other activities", left));
                    leftActivities.setPadding(new Insets(0, 5, 0, 5));
                    leftActivities.setStyle("-fx-background-color:#f4c2c2; -fx-background-radius: 5 5 5 5");
                    leftActivities.setMaxWidth(Double.MAX_VALUE);
                    dateContent.getChildren().add(leftActivities);
                }
            }
        }
    }

    /**
     * Get the label representing the activity.
     * @param activity activity to be listed in calendar
     * @return
     */
    private Label getActivityLabel(Activity activity) {
        Label activityLabel = new Label();
        activityLabel.setText(activity.toString());
        activityLabel.setPadding(new Insets(0, 5, 0, 5));
        if (activity instanceof Deadline) {
            // color it red
            activityLabel.setStyle("-fx-background-color:#AFEEEE; -fx-background-radius: 5 5 5 5");
        } else if (activity instanceof Event) {
            // color it yellow
            activityLabel.setStyle("-fx-background-color:yellow; -fx-background-radius: 5 5 5 5");
        } else {
            // color it green
            activityLabel.setStyle("-fx-background-color:green; -fx-background-radius: 5 5 5 5");
        }
        activityLabel.setTextFill(Color.BLACK);
        return activityLabel;
    }

    /**
     * Initialize the whole calendar.
     */
    private void initializeWholeCalendar() {
        initializeCalendarHeader();
        initializeDateGrids();
    }

    /**
     * Get the start months of the activities.
     * @param activity activity to be listed on calendar
     * @return
     */
    private int getMonth(Activity activity) {
        if (activity instanceof Deadline) {
            return ((Deadline) activity).getDueDate().getDate().getDayOfMonth();
        } else if (activity instanceof Event) {
            return ((Event) activity).getDateFrom().getDate().getDayOfMonth();
        } else {
            return ((Lesson) activity).getDateFrom().getDate().getDayOfMonth();
        }
    }

    /**
     * Method to reset the whole calendar (remove all activities).
     */
    private void resetCalendar() {
        ObservableList<Node> calendarCells = calendarGrid.getChildren();
        for (Node cell : calendarCells) {
            if (GridPane.getRowIndex(cell) != null
                && GridPane.getRowIndex(cell) != 0) {
                ObservableList<Node> nodes = ((VBox) cell).getChildren();
                if (nodes.size() > 1) {
                    nodes.remove(1, nodes.size());
                }
            }
        }
    }
}
