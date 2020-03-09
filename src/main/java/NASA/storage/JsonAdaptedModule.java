package NASA.storage;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import NASA.commons.exceptions.IllegalValueException;
import NASA.model.activity.Activity;
import NASA.model.module.Module;
import NASA.model.activity.UniqueActivityList;
import NASA.model.module.ModuleCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String moduleCode;

    private final List<JsonAdaptedActivity> activityList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode, List<JsonAdaptedActivity> activities) {
        this.moduleCode = moduleCode;
        if (activities != null) {
            this.activityList.addAll(activities);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().moduleCode;
        activityList.addAll(source.getActivities().stream()
                .map(JsonAdaptedActivity::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Activity> moduleActivities = new ArrayList<>();
        for (JsonAdaptedActivity activity : activityList) {
            moduleActivities.add(Activity.toModelType());
        }

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        final Set<Activity> modelActivities = new HashSet<>(moduleActivities);
        return new Module(modelModuleCode, modelActivities);
    }

}

