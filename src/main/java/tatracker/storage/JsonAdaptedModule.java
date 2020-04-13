//@@author potatocombat

package tatracker.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.group.Group;
import tatracker.model.module.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    // public static final String MESSAGE_DUPLICATE_SESSIONS =
    // "Module's list of sessions contains duplicate session(s).";
    public static final String MESSAGE_DUPLICATE_GROUPS = "Module's list of groups contains duplicate group(s).";

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    public static final String MISSING_MODULE_ID = String.format(MISSING_FIELD_MESSAGE_FORMAT, "id");
    public static final String MISSING_MODULE_NAME = String.format(MISSING_FIELD_MESSAGE_FORMAT, "name");

    private final String id;
    private final String name;
    // private final List<JsonAdaptedSession> sessions = new ArrayList<>();
    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("id") String id,
                             @JsonProperty("name") String name,
                             // @JsonProperty("sessions") List<JsonAdaptedSession> sessions,
                             @JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        this.id = id;
        this.name = name;
        // if (sessions != null) {
        //     this.sessions.addAll(sessions);
        // }
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
            throw new IllegalValueException(MISSING_MODULE_ID);
        }
        if (id.isBlank()) {
            throw new IllegalValueException(Module.CONSTRAINTS_MODULE_CODE);
        }

        // ==== Name ====
        if (name == null) {
            throw new IllegalValueException(MISSING_MODULE_NAME);
        }
        if (name.isBlank()) {
            throw new IllegalValueException(Module.CONSTRAINTS_MODULE_NAME);
        }

        // // ==== Done Sessions ====
        // final Set<Session> modelDoneSessions = new HashSet<>();
        // for (JsonAdaptedSession jsonAdaptedSession : sessions) {
        //     Session doneSession = jsonAdaptedSession.toModelType();
        //     if (modelDoneSessions.contains(doneSession)) {
        //         throw new IllegalValueException(MESSAGE_DUPLICATE_SESSIONS);
        //     }
        //     modelDoneSessions.add(doneSession);
        // }

        // ==== Groups ====
        final Map<String, Group> modelGroups = new HashMap<>();
        for (JsonAdaptedGroup jsonAdaptedGroup : groups) {
            Group group = jsonAdaptedGroup.toModelType();
            if (modelGroups.containsKey(group.getIdentifier())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROUPS);
            }
            modelGroups.put(group.getIdentifier(), group);
        }

        // ==== Build ====
        Module module = new Module(id, name);
        // modelDoneSessions.stream().forEach(module::);
        modelGroups.values().forEach(module::addGroup);

        return module;
    }
}
