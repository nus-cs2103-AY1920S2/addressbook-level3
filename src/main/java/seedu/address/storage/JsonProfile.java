package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.module.*;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.personal.Grade;
import seedu.address.model.profile.course.module.personal.Personal;
import seedu.address.model.profile.course.module.personal.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

                final ModuleCode moduleCode = new ModuleCode(module.getModuleCode());
                final Title title = new Title(module.getTitle());
                final Description description = new Description(module.getDescription());
                final ModularCredits modularCredits = new ModularCredits(module.getModuleCredit());
                final PrereqList prereqList = new PrereqList(module.getPrerequisite());
                final SemesterData semesterData = new SemesterData(module.getSemesterData());
                Module mod = new Module(moduleCode, title, prereqList, modularCredits, description, semesterData);

                Personal personal = new Personal();
                if (module.getGrade() != null) {
                    personal.setGrade(module.getGrade());
                }
                personal.setStatus(module.getStatus());
                // TODO: Implement functions for DeadlineList
                mod.setPersonal(personal);
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

class JsonPersonalModule {
    private final String moduleCode;
    private final String title;
    private final String description;
    private final String moduleCredit;
    private final String prerequisite;
    private final List<String> semesterData;
    private String status;
    private String grade;
    private List<JsonDeadline> deadlines;

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

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
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.moduleCredit = moduleCredit;
        this.prerequisite = prerequisite;
        List<String> semesters = new ArrayList<>();
        semesterData.forEach(semData -> semesters.add(semData.getSemester()));
        this.semesterData = semesters;
        this.status = status;
        this.grade = grade;
        this.deadlines = deadlines;
    }

    public String getModuleCode() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return moduleCode;
    }

    public String getTitle() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }
        return title;
    }

    public String getDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        return description;
    }

    public String getModuleCredit() throws IllegalValueException {
        if (moduleCredit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModularCredits.class.getSimpleName()));
        }
        if (!ModularCredits.isValidCredits(moduleCredit)) {
            throw new IllegalValueException(ModularCredits.MESSAGE_CONSTRAINTS);
        }
        return moduleCredit;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public List<String> getSemesterData() throws IllegalValueException {
        if (semesterData == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SemesterData.class.getSimpleName()));
        }
        return semesterData;
    }

    public String getStatus() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return status;
    }

    public String getGrade() throws IllegalValueException {
        if (grade == null) {
            return null;
        }
        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        return grade;
    }

    public List<JsonDeadline> getDeadlines() {
        return deadlines;
    }
}

class JsonDeadline {
    //private @JsonProperty("description") String description;
    //private @JsonProperty("date") String date;
    //private @JsonProperty("time") String time;
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

    public String getDescription() {
        return description;
    }

    public String getDate() throws IllegalValueException {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Deadline's date field should be a valid date in the format: YYYY-MM-DD");
        }
        return date;
    }

    public String getTime() throws IllegalValueException {
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Deadline's time field should be a valid time in the format HH:mm");
        }
        return time;
    }
}
