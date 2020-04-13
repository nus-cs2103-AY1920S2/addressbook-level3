//@@author potatocombat

package tatracker.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;
import tatracker.model.module.Module;
import tatracker.model.session.Session;

/**
 * An Immutable TaTracker that is serializable to JSON format.
 */
@JsonRootName(value = "tatracker")
class JsonSerializableTaTracker {

    public static final String MESSAGE_DUPLICATE_SESSIONS = "Session list contains duplicate session(s).";
    public static final String MESSAGE_DUPLICATE_DONE_SESSIONS = "Done session list contains duplicate session(s).";
    public static final String MESSAGE_DUPLICATE_MODULES = "Module list contains duplicate module(s).";
    public static final String MESSAGE_INVALID_RATE =
            "Rate must be an integer greater than 0 representing the dollars paid per hour.";

    private final List<JsonAdaptedSession> sessions = new ArrayList<>();
    private final List<JsonAdaptedSession> doneSessions = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    private final int rate;

    /**
     * Constructs a {@code JsonSerializableTaTracker} with the given lists.
     */
    @JsonCreator
    public JsonSerializableTaTracker(@JsonProperty("sessions") List<JsonAdaptedSession> sessions,
                                     @JsonProperty("doneSessions") List<JsonAdaptedSession> doneSessions,
                                     @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                     @JsonProperty("rate") int rate) {
        this.sessions.addAll(sessions);
        this.doneSessions.addAll(doneSessions);
        this.modules.addAll(modules);

        this.rate = rate;
    }

    /**
     * Converts a given {@code ReadOnlyTaTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTaTracker}.
     */
    public JsonSerializableTaTracker(ReadOnlyTaTracker source) {
        sessions.addAll(source.getSessionList()
                .stream()
                .map(JsonAdaptedSession::new)
                .collect(Collectors.toList()));

        doneSessions.addAll(source.getDoneSessionList()
                .stream()
                .map(JsonAdaptedSession::new)
                .collect(Collectors.toList()));

        modules.addAll(source.getModuleList()
                .stream()
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));

        rate = source.getRate();
    }

    /**
     * Converts this Ta Tracker into the model's {@code TaTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaTracker toModelType() throws IllegalValueException {
        // ==== Sessions ====
        final Set<Session> modelSessions = new HashSet<>();
        for (JsonAdaptedSession jsonAdaptedSession : sessions) {
            Session session = jsonAdaptedSession.toModelType();
            if (modelSessions.contains(session)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SESSIONS);
            }
            modelSessions.add(session);
        }

        // ==== Done Sessions ====
        final Set<Session> modelDoneSessions = new HashSet<>();
        for (JsonAdaptedSession jsonAdaptedSession : doneSessions) {
            Session doneSession = jsonAdaptedSession.toModelType();
            if (modelDoneSessions.contains(doneSession)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DONE_SESSIONS);
            }
            modelDoneSessions.add(doneSession);
        }

        // ==== Modules ====
        final Map<String, Module> modelModules = new HashMap<>();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (modelModules.containsKey(module.getIdentifier())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULES);
            }
            modelModules.put(module.getIdentifier(), module);
        }

        if (rate <= 0) {
            throw new IllegalValueException(MESSAGE_INVALID_RATE);
        }

        // ==== Build ====
        TaTracker taTracker = new TaTracker();
        modelSessions.forEach(taTracker::addSession);
        modelDoneSessions.forEach(taTracker::addDoneSession);
        modelModules.values().forEach(taTracker::addModule);

        taTracker.setRate(rate);

        return taTracker;
    }
}
