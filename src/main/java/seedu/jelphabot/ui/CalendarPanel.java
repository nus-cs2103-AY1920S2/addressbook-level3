package seedu.jelphabot.ui;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.util.DateUtil;
import seedu.jelphabot.model.calendar.CalendarDate;

//@@author alam8064
/**
 * Panel containing the calendar view to be displayed.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private static ArrayList<CalendarDayCard> dayCardsInMonth;
    private static ArrayList<CalendarDayCard> allDayCards;
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    private CalendarDate calendarDate;
    private CalendarDayCard highlightedDay;

    @FXML
    private TabPane mainWindowTabPane;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthYear;

    public CalendarPanel(CalendarDate calendarDate, TabPane mainWindowTabPane) {
        super(FXML);
        logger.info("Initialising calendar panel stage");
        this.mainWindowTabPane = mainWindowTabPane;
        this.calendarDate = calendarDate;
        monthYear.setText(calendarDate.getMonthName() + ", " + calendarDate.getYear());

        CalendarDate firstDay = calendarDate.getFirstDay();
        fillGridPane(firstDay);
        highlightToday();
    }

    /**
     * Fills the grid pane of the calendar.
     * @param firstDay The date representing the first day of the month.
     */
    public void fillGridPane(CalendarDate firstDay) {
        calendarGrid.getChildren().clear();
        int weekIndex = firstDay.getDayOfWeek() - 1;
        CalendarDate currDate = null;
        if (weekIndex != 0) {
            int lengthPrevMonth = firstDay.getLengthPrevMonth();
            int day = lengthPrevMonth - weekIndex + 1;
            currDate = firstDay.createPrevMonthDate(day);
        } else {
            currDate = firstDay;
        }

        dayCardsInMonth = new ArrayList<>();
        allDayCards = new ArrayList<>();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                CalendarDayCard calendarDayCard = new CalendarDayCard(currDate);
                allDayCards.add(calendarDayCard);
                if (currDate.isSameMonth(firstDay.getMonth())) {
                    calendarDayCard.setSameMonthDay();
                    dayCardsInMonth.add(calendarDayCard);
                } else {
                    calendarDayCard.setDiffMonthDay();
                }
                calendarGrid.add(calendarDayCard.getRoot(), col, row);
                currDate = currDate.dateNextDay();
            }
        }
    }

    /**
     * Updates the tasks in all the calendar day cards in the calendar panel.
     */
    public void updateDayCards() {
        for (CalendarDayCard day: allDayCards) {
            day.updateTasks();
        }
    }

    /**
     * Updatest the MonthYear Label of the Calendar Panel with the inputted parameter.
     * @param yearMonth Specifies the year and month of the calendar to be set to.
     */
    public void changeMonthYearLabel(YearMonth yearMonth) {
        calendarDate = new CalendarDate(yearMonth.atEndOfMonth());
        monthYear.setText(calendarDate.getMonthName() + ", " + calendarDate.getYear());
    }

    public int getCalendarMonth() {
        return calendarDate.getMonth();
    }

    private static CalendarDayCard getDayCard(int dayIndex) {
        return CalendarPanel.dayCardsInMonth.get(dayIndex - 1);
    }

    private CalendarDayCard getHighlightedDay() {
        return highlightedDay;
    }

    private void setHighlightedDay(int dayIndex) {
        highlightedDay = dayCardsInMonth.get(dayIndex - 1);
    }

    private boolean isTodayHighlighted() {
        return highlightedDay.getDate().isToday();
    }

    /**
     * Highlights today's date on the calendar.
     */
    public void highlightToday() {
        int dayIndexOfToday = DateUtil.getDateToday().getDayOfMonth();
        getDayCard(dayIndexOfToday).highlightToday();
        setHighlightedDay(dayIndexOfToday);
    }

    /**
     * Removes the highlight for the current highlighted day on the calendar.
     */
    public void removeHighlightedDay() {
        if (isTodayHighlighted()) {
            getHighlightedDay().removeHighlightedToday();
        } else {
            getHighlightedDay().removeHighlightedDay();
        }
    }

    /**
     * Highlights the selected day card on the calendar when calendar command is run.
     * @param index The index of the specified day card from the dayCardsInMonth array list.
     */
    public void highlightDay(int index) {
        getDayCard(index).highlightDay();
        setHighlightedDay(index);
    }
}
