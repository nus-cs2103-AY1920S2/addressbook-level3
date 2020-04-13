package seedu.address.storage;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Location;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialName;
import seedu.address.model.mod.ModCode;
import seedu.address.model.student.Student;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */

class JsonAdaptedTutorial {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";
    private static final String INVALID_TIME_FORMAT = "Invalid time format!";
    private static final String INVALID_DAY_FORMAT = "Invalid day format!";

    private final String moduleCode;
    private final String tutorialName;
    private final String weekday;
    private final String beginTime;
    private final String endTime;
    private final String location;
    private final List<JsonAdaptedStudent> enrolledStudents = new ArrayList<>();
    private final List<JsonAdaptedAttendanceWeek> studentAttendance = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given tutorial details.
     */
    @JsonCreator
    public JsonAdaptedTutorial(@JsonProperty("moduleCode") String moduleCode,
                              @JsonProperty("tutorialName") String tutorialName,
                              @JsonProperty("weekday") String weekday,
                              @JsonProperty("beginTime") String beginTime,
                              @JsonProperty("endTime") String endTime,
                              @JsonProperty("location") String location,
                               @JsonProperty("enrolledStudents") List<JsonAdaptedStudent> enrolledStudents,
                               @JsonProperty("studentAttendance") List<JsonAdaptedAttendanceWeek> studentAttendance) {
        this.moduleCode = moduleCode;
        this.tutorialName = tutorialName;
        this.weekday = weekday;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        if (enrolledStudents != null) {
            this.enrolledStudents.addAll(enrolledStudents);
        }
        if (studentAttendance != null) {
            this.studentAttendance.addAll(studentAttendance);
        }
    }

    /**
     * Converts a given {@code Tutorial} into this class for Jackson use.
     */
    public JsonAdaptedTutorial(Tutorial source) {
        moduleCode = source.getModCode().toString();
        tutorialName = source.getTutorialName().toString();
        weekday = Integer.toString(source.getDay().getValue());
        beginTime = source.getBeginTime().toString();
        endTime = source.getEndTime().toString();
        location = source.getLocation().eventLocation;
        enrolledStudents.addAll(source.getEnrolledStudents().stream()
                .map(JsonAdaptedStudent::new)
                .collect(Collectors.toList()));
        studentAttendance.addAll(source.getAttendance().stream()
                .map(JsonAdaptedAttendanceWeek::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's {@code Tutorial} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutorial.
     */
    public Tutorial toModelType() throws IllegalValueException {

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "MODULE_CODE"));
        }

        if (tutorialName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "TUTORIAL_NAME"));
        }

        ModCode modelModuleCode;
        TutorialName modelTutorialName;

        modelModuleCode = new ModCode(moduleCode);
        modelTutorialName = new TutorialName(tutorialName);

        DayOfWeek modelWeekday;

        try {
            modelWeekday = DayOfWeek.of(Integer.parseInt(weekday));
        } catch (DateTimeException ex) {
            throw new IllegalValueException(INVALID_DAY_FORMAT);
        }


        if (beginTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "BEGIN_TIME"));
        }

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "END_TIME"));
        }

        LocalTime modelBeginTime;
        LocalTime modelEndTime;

        try {
            modelBeginTime = LocalTime.parse(this.beginTime);
            modelEndTime = LocalTime.parse(this.endTime);
        } catch (DateTimeParseException ex) {
            throw new IllegalValueException(INVALID_TIME_FORMAT);
        }

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "PLACE"));
        }

        final Location modelLocation = new Location(location);

        final ArrayList<Student> modelEnrolledStudents = new ArrayList<Student>();
        final ArrayList<ArrayList<Boolean>> modelStudentAttendance = new ArrayList<ArrayList<Boolean>>();

        for (int i = 0; i < Tutorial.NUM_OF_WEEKS; i++) {
            ArrayList<Boolean> week = new ArrayList<Boolean>();
            modelStudentAttendance.add(week);
        }

        if (!enrolledStudents.isEmpty()) {
            for (JsonAdaptedStudent jsonStudent : enrolledStudents) {
                Student student = jsonStudent.toModelType();
                modelEnrolledStudents.add(student);
            }
        }

        if (!studentAttendance.isEmpty()) {
            for (int i = 0; i < studentAttendance.size(); i++) {
                ArrayList<Boolean> week = studentAttendance.get(i).toModelType();
                modelStudentAttendance.set(i, week);
            }
        }

        return new Tutorial(modelModuleCode, modelTutorialName, modelWeekday, modelBeginTime, modelEndTime,
                modelLocation, modelEnrolledStudents, modelStudentAttendance);
    }
}
