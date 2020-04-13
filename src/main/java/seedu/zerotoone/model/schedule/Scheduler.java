package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Main class for the Scheduler. Used to store all the lists related to Scheduler.
 */
public class Scheduler {

    private static final Logger logger = LogsCenter.getLogger(Scheduler.class);

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
     * Returns true if a {@code Schedule} is already present in the Schedule list.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);

        logger.fine(String.format("Checking whether already has schedule: %s", schedule));

        return scheduleList.hasSchedule(schedule);
    }

    /**
     * Adds a {@code Schedule} to the Schedule list.
     */
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);

        logger.fine(String.format("Adding schedule: %s", schedule));

        scheduleList.addSchedule(schedule);

        populateSortedScheduledWorkoutList();
    }

    public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
        requireAllNonNull(scheduleToEdit, editedSchedule);

        logger.fine(String.format("Replacing old schedule (%s) with new schedule (%s)",
                scheduleToEdit,
                editedSchedule));

        scheduleList.setSchedule(scheduleToEdit, editedSchedule);

        populateSortedScheduledWorkoutList();
    }

    /**
     * Removes {@code Schedule} from the Schedule list.
     */
    public void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete) {
        requireNonNull(scheduledWorkoutToDelete);

        logger.fine(String.format("Deleting ScheduledWorkout: %s", scheduledWorkoutToDelete.getScheduledWorkoutName()));

        Schedule scheduleToDelete = scheduledWorkoutToDelete.getSchedule();
        scheduleList.removeSchedule(scheduleToDelete);

        populateSortedScheduledWorkoutList();
    }

    /**
     * Removes all instances {@code Schedule} from the Schedule list that are
     * related to the given {@code WorkoutName}.
     */
    public void deleteWorkoutNameFromSchedule(WorkoutName workoutNameToDelete) {
        requireNonNull(workoutNameToDelete);

        ScheduleList scheduleListCopy = new ScheduleList(scheduleList); // to avoid concurrentModificationException
        for (Schedule schedule : scheduleListCopy.getScheduleList()) {
            if (schedule.getWorkoutNameToSchedule().equals(workoutNameToDelete)) {
                scheduleList.removeSchedule(schedule);
            }
        }

        populateSortedScheduledWorkoutList();
    }

    /**
     * Updates all instances {@code Schedule} from the Schedule list that are
     * related to the given {@code WorkoutName} with the new {@code WorkoutName}.
     */
    public void editWorkoutNameInSchedule(WorkoutName workoutNameToEdit, WorkoutName editedWorkoutName) {
        requireAllNonNull(workoutNameToEdit, editedWorkoutName);

        ScheduleList scheduleListCopy = new ScheduleList(scheduleList); // to avoid concurrentModificationException
        for (Schedule scheduleToEdit : scheduleListCopy.getScheduleList()) {
            if (scheduleToEdit.getWorkoutNameToSchedule().equals(workoutNameToEdit)) {
                Schedule editedSchedule = new OneTimeSchedule(editedWorkoutName, scheduleToEdit.getDateTime());
                scheduleList.setSchedule(scheduleToEdit, editedSchedule);
            }
        }

        populateSortedScheduledWorkoutList();
    }

    /**
     * Populates the scheduled workout list by querying schedules in the schedule list.
     */
    public void populateSortedScheduledWorkoutList() {
        logger.fine("Populating SortedScheduledWorkoutList");

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
