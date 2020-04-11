package nasa.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.UniqueDeadlineList;
import nasa.model.activity.UniqueEventList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String moduleName;
    private final List<JsonAdaptedDeadline> deadlineList = new ArrayList<>();
    private final List<JsonAdaptedEvent> eventList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("moduleName") String moduleName,
                             @JsonProperty("deadlines") List<JsonAdaptedDeadline> deadlines,
                             @JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        if (deadlines != null) {
            this.deadlineList.addAll(deadlines);
        }
        if (events != null) {
            this.eventList.addAll(events);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().moduleCode;
        moduleName = source.getModuleName().toString();
        deadlineList.addAll(source.getDeadlineList().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedDeadline::new)
                .collect(Collectors.toList()));
        eventList.addAll(source.getEventList().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (moduleName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleName.class.getSimpleName()));
        }
        if (!ModuleName.isValidModuleName(moduleName)) {
            throw new IllegalValueException(ModuleName.MESSAGE_CONSTRAINTS);
        }
        final ModuleName modelModuleName = new ModuleName(moduleName);

        final List<Deadline> moduleDeadlines = new ArrayList<>();
        for (JsonAdaptedDeadline deadline : deadlineList) {
            moduleDeadlines.add(deadline.toModelType());
        }

        final UniqueDeadlineList uniqueDeadlineList = new UniqueDeadlineList();
        uniqueDeadlineList.setActivities(moduleDeadlines);

        final List<Event> moduleEvents = new ArrayList<>();
        for (JsonAdaptedEvent event : eventList) {
            moduleEvents.add(event.toModelType());
        }

        final UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.setActivities(moduleEvents);

        final Module module = new Module(modelModuleCode, modelModuleName);
        module.setDeadlines(uniqueDeadlineList);
        module.setEvents(uniqueEventList);
        return module;
    }

}

