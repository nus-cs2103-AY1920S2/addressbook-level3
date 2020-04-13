package seedu.address.ui.calendar;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.UiPart;

/**
 * Represents a calendar date cell in the calendar window.
 */
public class CalendarDay extends UiPart<Region> {

    private static final String FXML = "CalendarDay.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private LocalDate date;
    private ObservableList<Consult> consults;
    private ObservableList<Tutorial> tutorials;
    private ObservableList<Reminder> reminders;

    @FXML
    private StackPane calendarDayStackPane;

    /**
     * Creates a CalendarDay.
     */
    public CalendarDay() {
        super(FXML);
        consults = FXCollections.observableArrayList();
        tutorials = FXCollections.observableArrayList();
        reminders = FXCollections.observableArrayList();
    }

    public StackPane getCalendarDayStackPane() {
        logger.fine("Return StackPane");
        return calendarDayStackPane;
    }

    /**
     * Sets the date of this {@code CalendarDay} to the specified date.
     *
     * */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the date of this {@code CalendarDay}.
     *
     * @return The date of this {@code CalendarDay}.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Adds the consult that is happening on this day to the {@Code CalendarDay}.
     */
    public void addConsult(Consult consult) {
        consults.add(consult);
    }

    /**
     * Remove all consults on this {@Code CalendarDay}.
     */
    public void removeConsults() {
        consults.clear();
    }

    /**
     * Updates the number of consults on this CalendarDay.
     */
    public void updateNumConsults() {
        int noConsults = consults.size();
        if (noConsults > 0) {
            Text consultsCount;
            if (noConsults == 1) {
                consultsCount = new Text("1 consult");
            } else {
                consultsCount = new Text(noConsults + " consults");
            }
            StackPane.setAlignment(consultsCount, Pos.BOTTOM_CENTER);
            calendarDayStackPane.getChildren().add(consultsCount);
        }
    }

    /**
     * Adds the tutorial that is happening on this day to the {@Code CalendarDay}.
     */
    public void addTutorial(Tutorial tutorial) {
        tutorials.add(tutorial);
    }

    /**
     * Removes all tutorials on this {@Code CalendarDay}.
     */
    public void removeTutorials() {
        tutorials.clear();
    }

    /**
     * Updates the number of tutorials on this CalendarDay.
     */
    public void updateNumTutorials() {
        int noTutorials = tutorials.size();
        if (noTutorials > 0) {
            Text tutorialsCount;
            if (noTutorials == 1) {
                tutorialsCount = new Text("1 tutorial");
            } else {
                tutorialsCount = new Text(noTutorials + " tutorials");
            }
            StackPane.setAlignment(tutorialsCount, Pos.CENTER);
            calendarDayStackPane.getChildren().add(tutorialsCount);
        }
    }

    /**
     * Adds the reminder that is happening on this day to the {@Code CalendarDay} if it is not done yet.
     */
    public void addReminder(Reminder reminder) {
        if (!reminder.getDone()) {
            reminders.add(reminder);
        }
    }

    /**
     * Remove all reminders on this {@Code CalendarDay}.
     */
    public void removeReminders() {
        reminders.clear();
    }

    /**
     * Updates the number of reminders on this CalendarDay.
     */
    public void updateNumReminders() {
        int noReminders = reminders.size();
        if (noReminders > 0) {
            Text remindersCount;
            if (noReminders == 1) {
                remindersCount = new Text("  1 reminder");
            } else {
                remindersCount = new Text("  " + noReminders + " reminders");
            }
            StackPane.setAlignment(remindersCount, Pos.TOP_CENTER);
            calendarDayStackPane.getChildren().add(remindersCount);
        }
    }
}
