package seedu.address.model.module;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyPlanner;



/**
 * An Immutable ModuleList that is serializable to JSON format.
 */
public class JsonSerializableLesson {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";

    private String classNo;
    private String startTime;
    private String endTime;
    private List<String> weeks;
    private String venue;
    private String day;
    private String lessonType;
    private int size;

    /**
     * Converts a given {@code ReadOnlyPlanner} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePlanner}.
     */
    public JsonSerializableLesson(ReadOnlyPlanner source) {
        // TODO: Don't think we will need to use this. KIV
        // modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Constructs a {@code JsonSerializableLesson} with the given lessons.
     */
    @JsonCreator
    public JsonSerializableLesson(
        @JsonProperty("classNo") String classNo,
        @JsonProperty("startTime") String startTime,
        @JsonProperty("endTime") String endTime,
        @JsonProperty("weeks") List<String> weeks,
        @JsonProperty("venue") String venue,
        @JsonProperty("day") String day,
        @JsonProperty("lessonType") String lessonType,
        @JsonProperty("size") int size
    ) {
        this.classNo = classNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weeks = weeks;
        this.venue = venue;
        this.day = day;
        this.lessonType = lessonType;
        this.size = size;
    }

    /**
     * Converts this Lesson into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Lesson toModelType() {
        return new Lesson(
            classNo,
            startTime,
            endTime,
            weeks,
            venue,
            day,
            lessonType,
            size
        );
    }

    public static String getMessageDuplicateModule() {
        return MESSAGE_DUPLICATE_MODULE;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Object getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
