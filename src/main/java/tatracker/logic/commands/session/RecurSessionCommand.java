package tatracker.logic.commands.session;

import java.time.LocalDateTime;

import tatracker.model.session.Session;
import tatracker.model.session.SessionType;

/**
 * Adds a session to the TATracker when a session is done and {@code Session#isRecurring};
 * Assumes that a recurring session is on a weekly basis.
 */
public class RecurSessionCommand {

    private Session sessionToRecur;

    public RecurSessionCommand(Session sessionToRecur) {
        this.sessionToRecur = sessionToRecur;
    }

    /**
     * Creates a recurring new Session
     * @return a new session
     */
    public AddSessionCommand createRecurSession() {

        LocalDateTime startTime = sessionToRecur.getStartDateTime().plusWeeks(1);
        LocalDateTime endTime = sessionToRecur.getEndDateTime();
        SessionType sessionType = sessionToRecur.getSessionType();
        boolean isRecurring = sessionToRecur.getIsRecurring();
        String moduleCode = sessionToRecur.getModuleCode();
        String description = sessionToRecur.getDescription();

        Session newSession = new Session(startTime, endTime, sessionType,
                                             isRecurring, moduleCode, description);

        return new AddSessionCommand(newSession);
    }



}
