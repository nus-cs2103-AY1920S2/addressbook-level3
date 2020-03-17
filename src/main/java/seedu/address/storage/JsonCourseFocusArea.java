package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.module.ModularCredits;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Jackson-friendly version of {@link CourseFocusArea}.
 */
class JsonCourseFocusArea {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String focusAreaName;
    private final List<String> modules;
    private final String modularCredits;

    @JsonCreator
    public JsonCourseFocusArea(@JsonProperty("sectionName") String focusAreaName,
                      @JsonProperty("modules") List<String> modules,
                      @JsonProperty("modularCredits") String modularCredits) {
        this.focusAreaName = focusAreaName;
        this.modules = modules;
        this.modularCredits = modularCredits;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code CourseFocusArea} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public CourseFocusArea toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (focusAreaName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Focus Area"));
        } else if (modules == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module"));
        } else if (modularCredits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Modular Credits"));
        }

        List<ModuleCode> modelModuleCodes = new ArrayList<>();
        modules.forEach(module -> modelModuleCodes.add(new ModuleCode(module)));
        final ModularCredits modelModuleCredit = new ModularCredits(modularCredits);

        return new CourseFocusArea(focusAreaName, modelModuleCodes, modelModuleCredit);
    }

}

