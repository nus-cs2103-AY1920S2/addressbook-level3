package seedu.zerotoone.model.util;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.session.SessionList;
import seedu.zerotoone.model.session.CompletedSet;

/**
 * Contains utility methods for populating {@code CompletedSessionList} with sample data.
 */
public class SampleSessionDataUtil {
    public static CompletedExercise[] getSampleSessions() {
        CompletedExercise[] completedExercises = new CompletedExercise[5];
        String[] names = new String[] {"Bench Press", "Overhead Press", "Triceps Pushdown"};


        for (int i = 0; i < 5; i++) {
            List<CompletedSet> completedSets = new LinkedList<>();
            completedSets.add(new CompletedSet(new Weight("99"), new NumReps("8"), true));
            completedSets.add(new CompletedSet(new Weight("99"), new NumReps("8"), true));
            completedSets.add(new CompletedSet(new Weight("99"), new NumReps("8"), true));

            ExerciseName name = new ExerciseName(names[i % 3]);
            LocalDateTime start = LocalDateTime.now().minusDays(i);
            LocalDateTime end = start.plusHours(2);
            completedExercises[i] = new CompletedExercise(name, completedSets, start, end);
        }

        return completedExercises;
    }

    public static ReadOnlySessionList getSampleSessionList() {
        SessionList sampleSessionList = new SessionList();
        for (CompletedExercise completedExercise : getSampleSessions()) {
            sampleSessionList.addSession(completedExercise);
        }
        return sampleSessionList;
    }

}
