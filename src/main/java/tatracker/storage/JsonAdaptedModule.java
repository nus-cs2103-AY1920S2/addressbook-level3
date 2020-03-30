package tatracker.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_SESSIONS = "Session list contains duplicate session(s).";
    public static final String MESSAGE_DUPLICATE_GROUPS = "Group list contains duplicate group(s).";

    private final String id;
    private final String name;
    private final List<JsonAdaptedSession> sessions = new ArrayList<>();
    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("id") String id,
                             @JsonProperty("name") String name,
                             @JsonProperty("sessions") List<JsonAdaptedSession> sessions,
                             @JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        this.id = id;
        this.name = name;
        if (sessions != null) {
            this.sessions.addAll(sessions);
        }
        if (groups != null) {
            this.groups.addAll(groups);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        id = source.getIdentifier();
        name = source.getName();
        sessions.addAll(source.getSessionList().stream()
                .map(JsonAdaptedSession::new)
                .collect(Collectors.toList()));
        groups.addAll(source.getGroupList().stream()
                .map(JsonAdaptedGroup::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        // ==== ID ====
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module id"));
        }

        // ==== Name ====
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module name"));
        }

        // ==== Done Sessions ====
        final List<Session> modelDoneSessions = new ArrayList<>();
        for (JsonAdaptedSession jsonAdaptedSession : sessions) {
            Session doneSession = jsonAdaptedSession.toModelType();
            if (modelDoneSessions.contains(doneSession)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SESSIONS);
            }
            modelDoneSessions.add(doneSession);
        }

        // ==== Groups ====
        final List<Group> modelGroups = new ArrayList<>();
        for (JsonAdaptedGroup jsonAdaptedGroup : groups) {
            Group group = jsonAdaptedGroup.toModelType();
            if (modelGroups.contains(group)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROUPS);
            }
            modelGroups.add(group);
        }

        // ==== Build ====
        Module module = new Module(id, name);
        modelDoneSessions.forEach(module::addDoneSession);
        modelGroups.forEach(module::addGroup);

        return module;
    }

}
