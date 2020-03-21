package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseRequirement;

/**
 * Jackson-friendly version of {@link JsonCourse}.
 */
class JsonCourse {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String courseName;
    private final List<JsonCourseRequirement> requirements;
    //private final List<JsonCourseFocusArea> focusAreas;

    @JsonCreator
    public JsonCourse(@JsonProperty("courseName") String courseName,
                      @JsonProperty("requirements") List <JsonCourseRequirement> requirements) {
        this.courseName = courseName;
        this.requirements = requirements;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public Course toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (courseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Course"));
        } else if (requirements == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Requirement"));
        }

        List<CourseRequirement> courseRequirements = new ArrayList<>();
        for (JsonCourseRequirement requirement : requirements) {
            CourseRequirement modelRequirement = requirement.toModelType();
            courseRequirements.add(modelRequirement);
        }

        return new Course(courseName, courseRequirements);
    }
}


