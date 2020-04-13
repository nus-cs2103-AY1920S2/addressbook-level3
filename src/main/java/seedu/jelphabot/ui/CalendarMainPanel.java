package seedu.jelphabot.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.util.DateUtil;
import seedu.jelphabot.logic.Logic;
import seedu.jelphabot.logic.commands.CommandResult;
import seedu.jelphabot.model.calendar.CalendarDate;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayPredicate;

//@@author alam8064
/**
 * The Calendar Main Panel. Provides the basic application layout containing the calendar task list and the
 * calendar panel.
 */
public class CalendarMainPanel extends UiPart<Region> {

    private static final String FXML = "CalendarMainPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarMainPanel.class);

    private Logic logic;

    private CalendarTaskListPanel calendarTaskListPanel;

    private CalendarPanel calendarPanel;

    @FXML
    private TabPane mainWindowTabPane;

    @FXML
    private StackPane calendarTaskListPanelPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public CalendarMainPanel(TabPane mainWindowTabPane, Logic logic) {
        super(FXML);
        logger.info("Initialising calendar main panel stage");
        this.mainWindowTabPane = mainWindowTabPane;
        this.logic = logic;

        initialiseCalendarMainPanel();
    }

    public CalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    /**
     * Switches to display the calendar panel tab.
     */
    public void show() {
        logger.fine("Showing calendar panel of application.");
        mainWindowTabPane.getSelectionModel().select(2);
    }

    /**
     * Returns true if the calendar panel is currently being shown.
     */
    public boolean isShowing() {
        return mainWindowTabPane.isPressed();
    }

    /**
     * Initialises and sets up the calendar main panel with the calendar task list, as well as the
     * calendar panel.
     */
    private void initialiseCalendarMainPanel() {
        calendarTaskListPanel = new CalendarTaskListPanel(logic.getFilteredCalendarTaskList());
        logic.updateFilteredCalendarTaskList(new TaskDueWithinDayPredicate(DateUtil.getDateToday()));
        calendarTaskListPanelPlaceholder.getChildren().add(calendarTaskListPanel.getRoot());

        calendarPanel = new CalendarPanel(CalendarDate.getCurrentDate(), mainWindowTabPane);
        calendarPanelPlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    /**
     * Updates the view of the calendar panel.
     * @param commandResult Represents the results of the calendar command.
     */
    public void updateCalendarPanel(CommandResult commandResult) {
        LocalDate date = commandResult.getDate();
        YearMonth yearMonth = commandResult.getYearMonth();
        calendarPanel.removeHighlightedDay();

        if (date != null && yearMonth == null) { //change date
            if (date.getMonthValue() == calendarPanel.getCalendarMonth()) {
                int dayIndex = date.getDayOfMonth();
                if (date.equals(DateUtil.getDateToday())) {
                    calendarPanel.highlightToday();
                } else {
                    calendarPanel.highlightDay(dayIndex);
                }
            }
        } else if (date == null && yearMonth != null) { //change month view
            LocalDate firstDayOfMonth = yearMonth.atDay(1);
            CalendarDate newDate = new CalendarDate(firstDayOfMonth);
            calendarPanel.changeMonthYearLabel(yearMonth);
            calendarPanel.fillGridPane(newDate);
            calendarPanel.highlightDay(1);
        } else { //change today
            LocalDate today = DateUtil.getDateToday();
            LocalDate firstDay = today.withDayOfMonth(1);
            CalendarDate firstDayDate = new CalendarDate(firstDay);
            YearMonth todayYearMonth = YearMonth.now();
            calendarPanel.changeMonthYearLabel(todayYearMonth);
            calendarPanel.fillGridPane(firstDayDate);
            calendarPanel.highlightToday();
        }
    }

}
