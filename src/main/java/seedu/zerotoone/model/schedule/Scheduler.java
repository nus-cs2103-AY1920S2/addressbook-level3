package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.zerotoone.model.workout.Workout;

/**
 * STEPH_TODO_JAVADOC
 */
public class Scheduler {

    private final ScheduleList scheduleList;
    private ScheduledWorkoutList scheduledWorkoutList;
    private SortedList<ScheduledWorkout> sortedScheduledWorkoutList;

    public Scheduler(ScheduleList scheduleList) {
        this.scheduleList = new ScheduleList(scheduleList);
        this.scheduledWorkoutList = new ScheduledWorkoutList();
        this.sortedScheduledWorkoutList = new SortedList<>(
                scheduledWorkoutList.getScheduledWorkoutList(),
                ScheduledWorkout.getComparator());
        populateSortedScheduledWorkoutList();
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return sortedScheduledWorkoutList;
    }

    /**
     *
     * @param schedule
     * @return
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);

        return scheduleList.hasSchedule(schedule);
    }

    /**
     *
     * @param schedule
     */
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);

        scheduleList.addSchedule(schedule);

        populateSortedScheduledWorkoutList();
    }

    public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
        requireAllNonNull(scheduleToEdit, editedSchedule);

        scheduleList.setSchedule(scheduleToEdit, editedSchedule);

        populateSortedScheduledWorkoutList();
    }

    /**
     *
     * @param scheduledWorkoutToDelete
     */
    public void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete) {
        requireNonNull(scheduledWorkoutToDelete);

        Schedule scheduleToDelete = scheduledWorkoutToDelete.getSchedule();
        scheduleList.removeSchedule(scheduleToDelete);

        populateSortedScheduledWorkoutList();
    }

    /**
     *
     * @param workoutToDelete
     */
    public void deleteWorkoutFromSchedule(Workout workoutToDelete) {
        requireNonNull(workoutToDelete);

        ScheduleList scheduleListCopy = new ScheduleList(scheduleList); // to avoid concurrentModificationException
        for (Schedule schedule : scheduleListCopy.getScheduleList()) {
            if (schedule.getWorkoutToSchedule().isSameWorkout(workoutToDelete)) {
                scheduleList.removeSchedule(schedule);
            }
        }

        populateSortedScheduledWorkoutList();
    }

    /**
     *
     * @param workoutToEdit
     * @param editedWorkout
     */
    public void editWorkoutInSchedule(Workout workoutToEdit, Workout editedWorkout) {
        requireAllNonNull(workoutToEdit, editedWorkout);

        ScheduleList scheduleListCopy = new ScheduleList(scheduleList); // to avoid concurrentModificationException
        for (Schedule scheduleToEdit : scheduleListCopy.getScheduleList()) {
            if (scheduleToEdit.getWorkoutToSchedule().isSameWorkout(workoutToEdit)) {
                Schedule editedSchedule = new OneTimeSchedule(editedWorkout, scheduleToEdit.getDateTime());
                scheduleList.setSchedule(scheduleToEdit, editedSchedule);
            }
        }

        populateSortedScheduledWorkoutList();
    }

    /**
     *
     */
    public void populateSortedScheduledWorkoutList() {
        List<ScheduledWorkout> newScheduledWorkouts = scheduleList.getScheduleList().stream()
                .map(schedule -> schedule.getScheduledWorkout(DateTime.now()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        setScheduledWorkouts(newScheduledWorkouts);
    }

    public void setScheduledWorkouts(List<ScheduledWorkout> scheduledWorkouts) {
        this.scheduledWorkoutList.setScheduledWorkouts(scheduledWorkouts);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Scheduler other = (Scheduler) obj;
        return scheduleList.equals(other.scheduleList);
    }
}
