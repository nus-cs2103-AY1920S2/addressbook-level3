package NASA.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import NASA.model.module.ModuleName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import NASA.commons.exceptions.IllegalValueException;
import NASA.model.activity.Activity;
import NASA.model.module.Module;
import NASA.model.activity.UniqueActivityList;
import NASA.model.module.ModuleCode;


/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String moduleName;
    private final List<JsonAdaptedActivity> activityList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("moduleName") String moduleName,
                             List<JsonAdaptedActivity> activities) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        if (activities != null) {
            this.activityList.addAll(activities);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().moduleCode;
        moduleName= source.getModuleName().toString();
        activityList.addAll(source.getActivities().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedActivity::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Activity> moduleActivities = new ArrayList<>();
        for (JsonAdaptedActivity activity : activityList) {
            moduleActivities.add(activity.toModelType());
        }

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

        final UniqueActivityList uniqueActivityList = new UniqueActivityList();
        uniqueActivityList.setActivities(moduleActivities);

        return new Module(modelModuleCode, modelModuleName, uniqueActivityList);
    }

}

