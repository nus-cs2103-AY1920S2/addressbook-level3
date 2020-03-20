package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.course.CourseRequirement;
import seedu.address.model.profile.course.module.ModularCredits;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Jackson-friendly version of {@link CourseRequirement}.
 */
class JsonCourseRequirement{

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String requirementName;
    private final List<String> modules;
    private final String modularCredits;

    @JsonCreator
    public JsonCourseRequirement(@JsonProperty("requirementName") String requirementName,
                               @JsonProperty("modules") List<String> modules,
                               @JsonProperty("modularCredits") String modularCredits) {
        this.requirementName = requirementName;
        this.modules = modules;
        this.modularCredits = modularCredits;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code CourseRequirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public CourseRequirement toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (requirementName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Requirement"));
        } else if (modules == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module"));
        } else if (modularCredits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Modular Credits"));
        }

        List<ModuleCode> modelModuleCodes = new ArrayList<>();
        modules.forEach(module -> modelModuleCodes.add(new ModuleCode(module)));
        final ModularCredits modelModuleCredit = new ModularCredits(modularCredits);

        return new CourseRequirement(requirementName, modelModuleCodes, modelModuleCredit);
    }

}

