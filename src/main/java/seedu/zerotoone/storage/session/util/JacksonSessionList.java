package seedu.zerotoone.storage.session.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.session.SessionList;

/**
 * An Immutable SessionList that is serializable to JSON format.
 */
@JsonRootName(value = "sessionlist")
public class JacksonSessionList {

    private final List<JacksonSession> sessions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSessionList} with the given sessions.
     */
    @JsonCreator
    public JacksonSessionList(@JsonProperty("sessions") List<JacksonSession> sessions) {
        this.sessions.addAll(sessions);
    }

    /**
     * Converts a given {@code ReadOnlySessionList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSessionList}.
     */
    public JacksonSessionList(ReadOnlySessionList source) {
        sessions.addAll(source.getSessionList().stream().map(JacksonSession::new).collect(Collectors.toList()));
    }

    /**
     * Converts this session list into the model's {@code SessionList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SessionList toModelType() throws IllegalValueException {
        SessionList sessionList = new SessionList();
        for (JacksonSession jsonAdaptedSession : sessions) {
            Session session = jsonAdaptedSession.toModelType();
            sessionList.addSession(session);
        }
        return sessionList;
    }

}
