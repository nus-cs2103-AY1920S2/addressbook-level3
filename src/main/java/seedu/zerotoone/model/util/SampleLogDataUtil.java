package seedu.zerotoone.model.util;

import static seedu.zerotoone.model.util.SampleWorkoutDataUtil.getSampleWorkouts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.log.ReadOnlyLogList;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.workout.Workout;

/**
 * Contains utility methods for populating {@code CompletedSessionList} with sample data.
 */
public class SampleLogDataUtil {
    public static List<CompletedWorkout> getCompletedWorkouts() {
        List<CompletedWorkout> completedWorkouts = new ArrayList<>();

        final int dayCount = 10;

        int[] workoutTimeVariance = new int[] {5, 9, 22, 31, 33, 45, 59, 51, 62, 70};

        Workout[] workouts = getSampleWorkouts();
        LocalDateTime start = LocalDateTime.now().minusDays(dayCount);
        LocalDateTime end = LocalDateTime.now().minusDays(dayCount);

        for (int i = 0; i < dayCount; i++) {
            completedWorkouts.add(
                new CompletedWorkout(workouts[i % 3].getWorkoutName(),
                    getCompletedExercises(start, workouts[i % 3].getWorkoutExercises()),
                    start,
                    end.plusMinutes(workoutTimeVariance[i])));

            start = start.plusDays(1);
            end = end.plusDays(1);
        }

        return completedWorkouts;
    }

    public static List<CompletedExercise> getCompletedExercises(LocalDateTime workoutStart, List<Exercise> exercises) {
        List<CompletedExercise> completedExercises = new ArrayList<>();
        workoutStart = workoutStart.plusMinutes(5);

        for (Exercise curr : exercises) {
            List<CompletedSet> completedSets = new LinkedList<>();

            for (int j = 0; j < curr.getExerciseSets().size(); j++) {
                ExerciseSet set = curr.getExerciseSets().get(j);
                CompletedSet completedSet = new CompletedSet(set.weight, set.numReps, (j % 3) != 0);
                completedSets.add(completedSet);
            }

            ExerciseName name = curr.getExerciseName();
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
