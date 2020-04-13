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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Name;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

/**
 * UI component to represent the calendar view.
 */
public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarView.fxml";
    private int currentYear;
    private int currentMonth;
    private ObservableList<Module> moduleObservableList;

    @FXML
    private Label monthAndYear;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Button next;

    @FXML
    private Button prev;

    @FXML
    private HBox calendarDetails;

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
        this.moduleObservableList = moduleObservableList;

        // update the Label
        updateLabel();

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
                updateCalendar(moduleObservableList);
                loadActivities(moduleObservableList);
            }
        });
        updateCalendar(moduleObservableList);
    }

    /**
     * Method to update the calendar as activities are edited/removed/added.
     * @param moduleObservableList List of modules
     */
    private void updateCalendar(ObservableList<Module> moduleObservableList) {
        for (Module module : moduleObservableList) {
            ObservableList<Deadline> deadlineObservableList = module.getFilteredDeadlineList();
            ObservableList<Event> eventObservableList = module.getFilteredEventList();
            deadlineObservableList.addListener(new ListChangeListener<Deadline>() {
                @Override
                public void onChanged(Change<? extends Deadline> c) {
                    resetCalendar();
                    loadActivities(moduleObservableList);
                }
            });
            eventObservableList.addListener(new ListChangeListener<Event>() {
                @Override
                public void onChanged(Change<? extends Event> c) {
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
            dayHeader.setAlignment(Pos.CENTER);
            GridPane.setVgrow(dayHeader, Priority.NEVER);
            Label day = new Label();
            day.setText(DayOfWeek.of(i).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            day.setTextFill(Color.WHITE);
            day.setAlignment(Pos.CENTER);
            dayHeader.getChildren().add(day);
            dayHeader.getStyleClass().add("date-header");
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
            ScrollPane dateContentSp = new ScrollPane();
            VBox dateContent = new VBox();
            dateContentSp.setFitToWidth(true);
            dateContentSp.setFitToHeight(true);
            dateContentSp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            dateContentSp.setContent(dateContent);
            if (i < nullDays) {
                // not in current month, set to white color
                dateContent.getStyleClass().add("date-pane");
            } else {
                // set to purple color plus add date number
                dateContentSp.setId(Integer.toString(currentDate));
                dateContent.setId(Integer.toString(currentDate));
                dateContent.getStyleClass().add("date-pane");
                Label dateLabel = new Label();
                dateLabel.setText(Integer.toString(currentDate));
                dateLabel.setStyle("-fx-text-fill:black");
                dateLabel.setPadding(new Insets(10));
                dateContent.getChildren().add(dateLabel);
                currentDate++;
            }
            calendarGrid.add(dateContentSp, i, 1);
        }

        // populate the rest of the grids as per normal
        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ScrollPane dateContentSp = new ScrollPane();
                VBox dateContent = new VBox();
                dateContentSp.setFitToWidth(true);
                dateContentSp.setFitToHeight(true);
                dateContentSp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                dateContentSp.setContent(dateContent);
                dateContentSp.setId(Integer.toString(currentDate));
                dateContent.setId(Integer.toString(currentDate));

                // check if current grid is still within the month
                if (currentDate <= totalDaysInMonth) {
                    dateContent.getStyleClass().add("date-pane");
                    Label dateLabel = new Label();
                    dateLabel.setText(Integer.toString(currentDate));
                    dateLabel.setStyle("-fx-text-fill: black");
                    dateLabel.setPadding(new Insets(10));
                    dateContent.getChildren().add(dateLabel);
                    currentDate++;
                } else {
                    // create a white pane
                    dateContent.getStyleClass().add("date-pane");
                }
                calendarGrid.add(dateContentSp, j, i);
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
            ObservableList<Deadline> deadlineObservableList =
                module.getFilteredDeadlineList();
            for (Deadline deadline : deadlineObservableList) {
                Deadline deadlineCopy = (Deadline) deadline.deepCopy();
                Name name = new Name(String.format("[%s] %s", module.getModuleCode().toString(),
                    deadline.getName().toString()));
                deadlineCopy.setName(name);
                if (deadline.occurInMonth(currentMonth)) {
                    if (deadline.getDueDate().getDate().getYear() != currentYear) {
                        continue;
                    }
                    int activityDate = getDayOfMonth(deadline);
                    if (activityHashMap.containsKey(activityDate)) {
                        activityHashMap.get(activityDate).add(deadlineCopy);
                    } else {
                        ArrayList<Activity> activities = new ArrayList<>();
                        activities.add(deadlineCopy);
                        activityHashMap.put(activityDate, activities);
                    }
                }
            }
            ObservableList<Event> eventObservableList =
                module.getFilteredEventList();
            addEvents(eventObservableList, activityHashMap, module.getModuleCode());
        }

        // now we populate those grids that has activities
        for (Integer i : activityHashMap.keySet()) {
            ArrayList<Activity> dateActivities = activityHashMap.get(i);
            int size = dateActivities.size();

            if (size > 0) {
                Node node = calendarGrid.lookup("#" + Integer.toString(i));
                ScrollPane dateContentSp = (ScrollPane) node;
                VBox dateContent = (VBox) dateContentSp.getContent();
                for (Activity activity : dateActivities) {
                    Label activityLabel = getActivityLabel(activity);
                    activityLabel.setMaxWidth(Double.MAX_VALUE);
                    dateContent.getChildren().add(activityLabel);
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
        activityLabel.setText(activity.getName().toString());
        activityLabel.setPadding(new Insets(0, 5, 0, 5));
        if (activity instanceof Deadline) {
            // color it red
            activityLabel.setStyle("-fx-background-color: purple; -fx-background-radius: 5"
                    + " 5 5 5");
        } else if (activity instanceof Event) {
            // color it yellow
            activityLabel.setStyle("-fx-background-color: darkblue");
        } else {
            // color it green
            activityLabel.setStyle("-fx-background-color:green");
        }
        activityLabel.setTextFill(Color.BLACK);
        activityLabel.setAlignment(Pos.CENTER);
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
    private int getDayOfMonth(Activity activity) {
        if (activity instanceof Deadline) {
            return ((Deadline) activity).getDueDate().getDate().getDayOfMonth();
        } else {
            return ((Event) activity).getStartDate().getDate().getDayOfMonth();
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
                ObservableList<Node> nodes = ((VBox) ((ScrollPane) cell).getContent()).getChildren();
                if (nodes.size() > 1) {
                    nodes.remove(1, nodes.size());
                }
            }
        }
    }

    /**
     * Updates the calendar to the next month's schedule.
     */
    public void onClickNext() {
        if (currentMonth == 12) {
            // set to first month of next year
            currentMonth = 1;
            currentYear += 1;
        } else {
            currentMonth += 1;
        }

        updateLabel();
        calendarGrid.getChildren().clear();
        initializeWholeCalendar();
        loadActivities(moduleObservableList);
        /*
        initializeDateGrids();
        loadActivities(moduleObservableList);*/
    }

    /**
     * Update the calendar to previous month's schedule.
     */
    public void onClickPrevious() {
        if (currentMonth == 1) {
            // set to last month of last year
            currentMonth = 12;
            currentYear -= 1;
        } else {
            currentMonth -= 1;
        }

        updateLabel();
        calendarGrid.getChildren().clear();
        initializeWholeCalendar();
        loadActivities(moduleObservableList);
    }

    /**
     * Update label based on current months.
     */
    public void updateLabel() {
        monthAndYear.setText(String.format("%s %s", Month.of(currentMonth), Year.of(currentYear)));
        monthAndYear.setTextFill(Color.WHITE);
        monthAndYear.setAlignment(Pos.CENTER);
    }

    /**
     * Adds event to the calendar.
     * @param events list of events to be added
     * @param activityHashMap underlying data structure to show activities on the calendar
     */
    public void addEvents(ObservableList<Event> events, HashMap<Integer, ArrayList<Activity>> activityHashMap,
                          ModuleCode moduleCode) {
        for (Event event : events) {
            int startYear = event.getStartDate().getDate().getYear();
            int endYear = event.getEndDate().getDate().getYear();
            Event eventCopy = (Event) event.deepCopy();
            Name name = new Name(String.format("[%s] %s", moduleCode.toString(),
                event.getName().toString()));
            eventCopy.setName(name);
            if (startYear > currentYear
                || endYear < currentYear) {
                continue; // event not happening this year at all
            } else {
                // now check for month
                int startMonth = event.getStartDate().getDate().getMonthValue();
                int endMonth = event.getEndDate().getDate().getMonthValue();
                if (startYear == currentYear && endYear == currentYear) {
                    addEventHelper(startMonth, endMonth, eventCopy, activityHashMap);
                } else if (startYear < currentYear && currentYear < endYear) {
                    // any month will be filled with events
                    populateMonthWithEvents(eventCopy, activityHashMap);
                } else {
                    if (startYear == currentYear) {
                        endMonth = 12;
                    } else {
                        startMonth = 1;
                    }
                    if (startMonth > currentMonth || endMonth < currentMonth) {
                        continue;
                    } else if (startMonth < currentMonth && currentMonth < endMonth) {
                        populateMonthWithEvents(eventCopy, activityHashMap);
                    } else if (startYear == currentYear && startMonth == currentMonth) {
                        populateMonthFromStartToEnd(eventCopy, activityHashMap,
                            event.getStartDate().getDate().getDayOfMonth(), getMaxDaysInMonth());
                    } else if (endYear == currentYear && endMonth == currentMonth) {
                        populateMonthFromStartToEnd(eventCopy, activityHashMap,
                            1, event.getEndDate().getDate().getDayOfMonth());
                    } else {
                        populateMonthWithEvents(eventCopy, activityHashMap);
                    }
                }
            }
        }
    }

    /**
     * Helper method to populate month with event {@code event}
     * @param startMonth
     * @param endMonth
     * @param event
     * @param activityHashMap
     */
    public void addEventHelper(int startMonth, int endMonth, Event event,
                               HashMap<Integer, ArrayList<Activity>> activityHashMap) {
        if (startMonth > currentMonth || endMonth < currentMonth) {
            return;
        } else {
            // check if month in-between
            if (startMonth < currentMonth && currentMonth < endMonth) {
                // means the whole month has the event
                populateMonthWithEvents(event, activityHashMap);
            } else if (startMonth == currentMonth && endMonth == currentMonth) {
                // populate from start date to end-date
                populateMonthFromStartToEnd(event, activityHashMap,
                    event.getStartDate().getDate().getDayOfMonth(),
                    event.getEndDate().getDate().getDayOfMonth());
            } else if (startMonth == currentMonth) {
                // populate from start to last day of month
                populateMonthFromStartToEnd(event, activityHashMap,
                    event.getStartDate().getDate().getDayOfMonth(),
                    getMaxDaysInMonth());

            } else {
                // populate from first day of month to end
                populateMonthFromStartToEnd(event, activityHashMap, 1,
                    event.getEndDate().getDate().getDayOfMonth());
            }
        }
    }

    /**
     * Populates month with event that span over multiple days.
     * @param event event to be shown
     * @param activityHashMap underlying data structure to store activities
     * @param startDate start date of event
     * @param endDate end date of event
     */
    public void populateMonthFromStartToEnd(Event event, HashMap<Integer, ArrayList<Activity>> activityHashMap,
                                            int startDate, int endDate) {
        for (int i = startDate; i <= endDate; i++) {
            if (activityHashMap.containsKey(i)) {
                activityHashMap.get(i).add(event);
            } else {
                ArrayList<Activity> activities = new ArrayList<>();
                activities.add(event);
                activityHashMap.put(i, activities);
            }
        }
    }

    /**
     * Populates months with event.
     * @param event event to be shown
     * @param activityHashMap underlying data structure to store activities
     */
    public void populateMonthWithEvents(Event event, HashMap<Integer, ArrayList<Activity>> activityHashMap) {
        int totalDaysInMonth = getMaxDaysInMonth();

        for (int i = 1; i <= totalDaysInMonth; i++) {
            if (activityHashMap.containsKey(i)) {
                activityHashMap.get(i).add(event);
            } else {
                ArrayList<Activity> activities = new ArrayList<>();
                activities.add(event);
                activityHashMap.put(i, activities);
            }
        }
    }

    public int getMaxDaysInMonth() {
        LocalDateTime monthDetails = LocalDateTime.of(currentYear, currentMonth, 1, 0, 0);
        int totalDaysInMonth = monthDetails.getMonth().maxLength(); // get total days in current month

        // need to adjust totalDaysInMonth for Feb (leap year)
        LocalDate date = LocalDate.of(currentYear, currentMonth, 1);
        if (!date.isLeapYear() && date.getMonth() == Month.FEBRUARY) {
            totalDaysInMonth--; // need to minus 1 as not leap year
        }
        return totalDaysInMonth;
    }
}
