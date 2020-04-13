//@@author Chuayijing

package tatracker.model.session;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import tatracker.commons.util.CollectionUtil;


/**
 * Tests that a {@code Session}'s arguments matches any of the keywords given.
 */
public class SessionPredicate implements Predicate<Session> {

    private LocalDate date;
    private String moduleCode;
    private SessionType sessionType;

    public SessionPredicate() {}

    /**
     * Copy constructor.
     */
    public SessionPredicate(SessionPredicate toCopy) {
        setDate(toCopy.date);
        setModuleCode(toCopy.moduleCode);
        setSessionType(toCopy.sessionType);
    }

    /**
     * Returns true if at least one field is specified.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(date, moduleCode, sessionType);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(date);
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
    }

    public Optional<String> getModuleCode() {
        return Optional.ofNullable(moduleCode);
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public Optional<SessionType> getSessionType() {
        return Optional.ofNullable(sessionType);
    }

    @Override
    public boolean test(Session session) {

        return session.getDate().equals(date)
                || session.getModuleCode().equals(moduleCode)
                || session.getSessionType().equals(sessionType);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SessionPredicate)) {
            return false;
        }
        SessionPredicate otherPredicate = (SessionPredicate) other;
        return date.equals(otherPredicate.date)
                && moduleCode.equals(otherPredicate.moduleCode)
                && sessionType.equals(otherPredicate.sessionType);
    }
}
