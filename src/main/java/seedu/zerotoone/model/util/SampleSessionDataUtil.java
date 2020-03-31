package seedu.zerotoone.model.util;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.session.SessionList;
import seedu.zerotoone.model.session.SessionSet;

/**
 * Contains utility methods for populating {@code CompletedSessionList} with sample data.
 */
public class SampleSessionDataUtil {
    public static Session[] getSampleSessions() {
        Session[] sessions = new Session[5];
        String[] names = new String[] {"Bench Press", "Overhead Press", "Triceps Pushdown"};


        for (int i = 0; i < 5; i++) {
            List<SessionSet> sessionSets = new LinkedList<>();
            sessionSets.add(new SessionSet(new Weight("99"), new NumReps("8"), true));
            sessionSets.add(new SessionSet(new Weight("99"), new NumReps("8"), true));
            sessionSets.add(new SessionSet(new Weight("99"), new NumReps("8"), true));

            ExerciseName name = new ExerciseName(names[i % 3]);
            LocalDateTime start = LocalDateTime.now().minusDays(i);
            LocalDateTime end = start.plusHours(2);
            sessions[i] = new Session(name, sessionSets, start, end);
        }

        return sessions;
    }

    public static ReadOnlySessionList getSampleSessionList() {
        SessionList sampleSessionList = new SessionList();
        for (Session session : getSampleSessions()) {
            sampleSessionList.addSession(session);
        }
        return sampleSessionList;
    }

}
