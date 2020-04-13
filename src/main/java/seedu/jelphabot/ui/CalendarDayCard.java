package seedu.jelphabot.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import seedu.jelphabot.model.calendar.CalendarDate;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayPredicate;

//@@author alam8064
/**
 * UI component that displays the day on the calendar.
 */
public class CalendarDayCard extends UiPart<Region> {

    private static final String FXML = "CalendarDayCard.fxml";

    private CalendarDate calendarDate;
    private ObservableList<Task> tasks;

    @FXML
    private Label calendarDay;

    @FXML
    private Circle circleDay;

    @FXML
    private Circle dotTasks;

    public CalendarDayCard(CalendarDate calendarDate) {
        super(FXML);
        this.calendarDate = calendarDate;
        updateTasks();
        calendarDay.setText(String.valueOf(calendarDate.getDay()));
    }

    public CalendarDate getDate() {
        return calendarDate;
    }

    private void setDotVisible() {
        dotTasks.setFill(Paint.valueOf("#20B2AA"));
    }

    private void setDotVisibleUrgent() {
        dotTasks.setFill(Paint.valueOf("#FF0000"));
    }

    private void setDotInVisible() {
        dotTasks.setFill(Color.TRANSPARENT);
    }

    /**
     * Updates the tasks due on the calendar date belonging to a calendar day card.
     */
    public void updateTasks() {
        ObservableList<Task> allTasks = MainWindow.getLogic().getFilteredTaskList();
        TaskDueWithinDayPredicate predicate = new TaskDueWithinDayPredicate(calendarDate.getDate());
        tasks = allTasks.filtered(predicate);
        if (tasks.size() == 0) {
            setDotInVisible();
        } else if (tasks.size() > 3) {
            setDotVisibleUrgent();
        } else {
            setDotVisible();
        }
    }

    /**
     * Highlights today's date on the calendar.
     */
    public void highlightToday() {
        calendarDay.setTextFill(Paint.valueOf("#ffffff"));
        calendarDay.setStyle("-fx-font-weight:bold");
        circleDay.setFill(Paint.valueOf("#4169E1"));
    }

    /**
     * Remove highlight for today's date on the calendar.
     */
    public void removeHighlightedToday() {
        calendarDay.setTextFill(Paint.valueOf("#4169E1"));
        circleDay.setFill(Color.TRANSPARENT);
    }

    /**
     * Remove highlight for a date on the calendar.
     */
    public void removeHighlightedDay() {
        circleDay.setFill(Color.TRANSPARENT);
    }

    /**
     * Highlights the day card on the calendar when calendar command is run.
     */
    public void highlightDay() {
        circleDay.setFill(Paint.valueOf("#B0C4DE"));
    }

    /**
     * Sets the colour of labels for dates this month.
     */
    public void setSameMonthDay() {
        calendarDay.setTextFill(Paint.valueOf("000000"));
    }

    /**
     * Sets the colour of labels for trailing dates from the previous and next months.
     */
    public void setDiffMonthDay() {
        calendarDay.setTextFill(Paint.valueOf("808080"));
        calendarDay.setOpacity(0.75);
    }
}
