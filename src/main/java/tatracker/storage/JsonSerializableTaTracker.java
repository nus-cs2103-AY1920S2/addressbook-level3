package tatracker.storage;

import java.util.ArrayList;
import java.util.List;
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
    public static final String MESSAGE_DUPLICATE_MODULES = "Module list contains duplicate module(s).";

    private final List<JsonAdaptedSession> sessions = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTaTracker} with the given lists.
     */
    @JsonCreator
    public JsonSerializableTaTracker(@JsonProperty("sessions") List<JsonAdaptedSession> sessions,
                                     @JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.sessions.addAll(sessions);
        this.modules.addAll(modules);
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

        modules.addAll(source.getModuleList()
                .stream()
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Ta Tracker into the model's {@code TaTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TaTracker toModelType() throws IllegalValueException {
        // ==== Sessions ====
        final List<Session> modelSessions = new ArrayList<>();
        for (JsonAdaptedSession jsonAdaptedSession : sessions) {
            Session session = jsonAdaptedSession.toModelType();
            if (modelSessions.contains(session)) {
                throw new IllegalArgumentException(MESSAGE_DUPLICATE_SESSIONS);
            }
            modelSessions.add(session);
        }

        // ==== Modules ====
        final List<Module> modelModules = new ArrayList<>();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (modelModules.contains(module)) {
                throw new IllegalArgumentException(MESSAGE_DUPLICATE_MODULES);
            }
            modelModules.add(module);
        }

        // ==== Build ====
        TaTracker taTracker = new TaTracker();
        modelSessions.forEach(taTracker::addSession);
        modelModules.forEach(taTracker::addModule);

        return taTracker;
    }
}
