package seedu.address.storage;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;
import seedu.address.model.module.ModuleCode;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Class's %s field is missing!";

    private final String moduleCode;
    private final String type;
    private final String venue;
    private final String dayAndTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("moduleCode") String moduleCode, @JsonProperty("type") String type,
                             @JsonProperty("dayAndTime") String dayAndTime, @JsonProperty("venue") String venue) {
        this.moduleCode = moduleCode;
        this.type = type;
        this.dayAndTime = dayAndTime;
        this.venue = venue;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        moduleCode = source.getModuleCode().toString();
        type = source.getType().toString();
        dayAndTime = source.getDayAndTime().getDay().toString() + " "
                + source.getDayAndTime().getStartTime().toString() + " "
                + source.getDayAndTime().getEndTime().toString();
        venue = source.getVenue();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }

        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }

        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonType.class.getSimpleName()));
        }

        if (!Lesson.isValidType(type)) {
            throw new IllegalValueException(LessonType.MESSAGE_CONSTRAINTS);
        }

        final LessonType modelType = Lesson.convertStringToLessonType(type);

        if (dayAndTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DayAndTime.class.getSimpleName()));
        }

        if (!DayAndTime.isValidDayAndTime(dayAndTime)) {
            throw new IllegalValueException(DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);
        }

        final DayAndTime modelDayAndTime;

        try {
            String[] splitted = dayAndTime.trim().split(" ");
            modelDayAndTime = new DayAndTime(DayOfWeek.valueOf(splitted[0]), LocalTime.parse(splitted[1]),
                    LocalTime.parse(splitted[2]));
        } catch (InvalidTimeRangeException e) {
            throw new IllegalValueException(DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);
        }

        return new Lesson(modelModuleCode, modelType, modelDayAndTime, venue);
    }
}
