package tatracker.model;

import javafx.collections.ObservableList;

import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

/**
 * Unmodifiable view of a ta-tracker.
 */
public interface ReadOnlyTaTracker {

    /**
     * Returns an unmodifiable view of the student list.
     * This list will not contain any duplicate student.
     */
    ObservableList<Student> getCurrentlyShownStudentList();

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns an unmodifiable view of the groups list.
     * This list will not contain any duplicate groups.
     */
    ObservableList<Group> getCurrentlyShownGroupList();

    /**
     * Returns an unmodifiable view of the sessions list.
     * This list will not contain any duplicate sessions.
     */
    ObservableList<Session> getSessionList();

    /**
     * Returns an unmodifiable view of the done sessions list.
     * This list will not contain any duplicate sessions.
     */
    ObservableList<Session> getDoneSessionList();

    /**
     * Returns an unmodifiable view of the student list.
     * This list will contain ALL students in TA-Tracker.
     */
    ObservableList<Student> getCompleteStudentList();

    /**
     * Returns the number of hours spent teaching.
     */
    long getTotalHours();

    /**
     * Returns the rate per hour of teaching.
     */
    int getRate();

    /**
     * Returns the total amount earned by teaching.
     */
    long getTotalEarnings();
}
