package seedu.zerotoone.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.log.ReadOnlyLogList;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Contains utility methods for populating {@code CompletedSessionList} with sample data.
 */
public class SampleLogDataUtil {


    public static List<CompletedWorkout> getCompletedWorkouts() {
        List<CompletedWorkout> completedWorkouts = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(90);

        for (int i = 0; i < 10; i++) {
            completedWorkouts.add(
                new CompletedWorkout(new WorkoutName("Workout " + i), getCompletedExercises(start), start, end));
        }

        return completedWorkouts;
    }

    public static List<CompletedExercise> getCompletedExercises(LocalDateTime workoutStart) {
        List<CompletedExercise> completedExercises = new ArrayList<>();
        String[] names = new String[] {"Bench Press", "Overhead Press", "Triceps Pushdown", "Push Up"};
        workoutStart = workoutStart.plusMinutes(5);

        for (int i = 0; i < 5; i++) {
            List<CompletedSet> completedSets = new LinkedList<>();
            completedSets.add(new CompletedSet(new Weight("20"), new NumReps("5"), true));
            completedSets.add(new CompletedSet(new Weight("30"), new NumReps("6"), true));
            completedSets.add(new CompletedSet(new Weight("40"), new NumReps("8"), true));

            ExerciseName name = new ExerciseName(names[i % 4]);
            LocalDateTime start = workoutStart.plusMinutes(2);
            LocalDateTime end = workoutStart.plusMinutes(10);
            completedExercises.add(new CompletedExercise(name, completedSets, start, end));
        }

        return completedExercises;
    }

    public static ReadOnlyLogList getSampleLogList() {
        LogList sampleLogList = new LogList();
        for (CompletedWorkout completedWorkout : getCompletedWorkouts()) {
            sampleLogList.addCompletedWorkout(completedWorkout);
        }
        return sampleLogList;
    }

}
