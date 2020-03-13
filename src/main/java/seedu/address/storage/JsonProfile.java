package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;
import seedu.address.model.profile.course.module.personal.Personal;
import seedu.address.model.profile.course.module.personal.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

class JsonProfile {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Profile's %s field is missing!";

    private String name;
    private String course;
    private String specialisation;
    private String semester;
    private List<JsonSemesterRecord> records;

    @JsonCreator
    public JsonProfile(@JsonProperty("name") String name,
            @JsonProperty("course") String course,
            @JsonProperty("specialisation") String specialisation,
            @JsonProperty("current semester") String semester,
            @JsonProperty("records") List<JsonSemesterRecord> records) {
        this.name = name;
        this.course = course;
        this.specialisation = specialisation;
        this.semester = semester;
        this.records = records;
    }

    public Profile toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        } else if (course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Course.class.getSimpleName()));
        } else if (semester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "current semester"));
        } else if (records == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "records"));
        }

        // Handle invalid values
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        // TODO: Validation for course - Completely alphabetical

        Name profileName = new Name(name);
        Course profileCourse = new Course(course);
        Profile profile = new Profile(profileName, profileCourse, semester, specialisation);

        for (JsonSemesterRecord record : records) {
            int semester = Integer.parseInt(record.getSemester());
            for (JsonPersonalModule module : record.getModules()) {
                Module mod = module.toModelType();
                profile.addModule(semester, mod);
            }
        }

        return profile;
    }
}

class JsonSemesterRecord {
    private String semester;
    private List<JsonPersonalModule> modules;

    @JsonCreator
    public JsonSemesterRecord(@JsonProperty("semester") String semester,
            @JsonProperty("modules") List<JsonPersonalModule> modules) {
        this.semester = semester;
        this.modules = modules;
    }

    public String getSemester() throws IllegalValueException {
        if (!semester.matches("\\d+")) {
            throw new IllegalValueException("Semester number should be a positive integer");
        }
        return semester;
    }

    public List<JsonPersonalModule> getModules() {
        return modules;
    }
}

class JsonPersonalModule extends JsonModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private String status;
    private String grade;
    private List<JsonDeadline> deadlines;

    @JsonCreator
    public JsonPersonalModule(@JsonProperty("moduleCode") String moduleCode,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("moduleCredit") String moduleCredit,
            @JsonProperty("prerequisite") String prerequisite,
            @JsonProperty("semesterData") List<JsonSemesterData> semesterData,
            @JsonProperty("status") String status,
            @JsonProperty("grade") String grade,
            @JsonProperty("deadlines") List<JsonDeadline> deadlines) {
        super(moduleCode, title, description, moduleCredit, prerequisite, semesterData);
        this.status = status;
        this.grade = grade;
        this.deadlines = deadlines;
    }

    @Override
    public Module toModelType() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        } else if (grade != null && !Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }

        Module module = super.toModelType();
        Personal personal = new Personal();
        if (grade != null) {
            personal.setGrade(grade);
        }
        personal.setStatus(status);
        if (deadlines != null) {
            for (JsonDeadline deadline : deadlines) {
                personal.addDeadline(deadline.toModelType());
            }
        }
        module.setPersonal(personal);
        return module;
    }
}

class JsonDeadline {
    private String description;
    private String date;
    private String time;

    @JsonCreator
    public JsonDeadline(@JsonProperty("description") String description,
            @JsonProperty("date") String date,
            @JsonProperty("time") String time) {
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public Deadline toModelType() throws IllegalValueException {
        try {
            LocalDate.parse(date);
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Deadline's date field should be a valid date in the format YYYY-MM-DD "
                    + "and time field should be a valid time in the format HH:mm");
        }

        try {
            return new Deadline(description, date, time);
        } catch (DateTimeException e) {
            throw new IllegalValueException("Unknown error occurred");
        }
    }
}
