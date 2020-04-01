/*
package tatracker.model.util;

import java.time.LocalDateTime;

import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;

/**
 * Contains utility methods for populating {@code TaTracker} with sample session data.
 */
/*
public class SampleDataUtilSession {
    public static Session[] getSampleSessions() {
        return new Session[]{
                //session add m/CS2103T s/14:00 e/16:00 d/19-02-2020 t/consultation n/with Alice and Bob`
                //   public Session(LocalDateTime start, LocalDateTime end, SessionType type, int recurring, String moduleCode,
                //String description)
                new Session(new LocalDateTime(2020 - 04 - 31 12:00),
                            new LocalDateTime(2020 - 04 - 31 14:00),
                            new SessionType("Consultation"), 1,
                            new Module("CS2103T"),
                            "with Alice and Bob"),
                new Session(new LocalDateTime(2020 - 04 - 10 10:00),
                            new LocalDateTime(2020 - 03 - 10 11:00),
                            new SessionType("Lab"), 2,
                            new Module("CS2030"),
                            "prepare notes"),
                new Session(new LocalDateTime(2020 - 06 - 26 14:00),
                            new LocalDateTime(2020 - 06 - 26 15:00),
                            new SessionType("Tutorial"), 1,
                            new Module("CS2103T"),
                            "check work"),
                new Session(new LocalDateTime(2020 - 04 - 29 09:00),
                            new LocalDateTime(2020 - 04 - 29 12:00),
                            new SessionType("Grading"), 0,
                            new Module("CS2103T"),
                            "grade group 1 first"),
                new Session(new LocalDateTime(2020 - 05 - 26 12:00),
                            new LocalDateTime(2020 - 05 - 26 14:00),
                            new SessionType("Preparation"), 0,
                            new Module("CS2030"),
                            "clarifications for Bob's question")
        };
    }

    public static ReadOnlyTaTracker getSampleTaTracker() {
        TaTracker sampleAb = new TaTracker();
        for (Session sampleSession : getSampleSessions()) {
            sampleAb.addSession(sampleSession);
        }
        return sampleAb;
    }

}

*/
