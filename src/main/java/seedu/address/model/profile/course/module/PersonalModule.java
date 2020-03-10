package seedu.address.model.profile.course.module;

/**
 * Represents a PersonalModule.
 */
public class PersonalModule {

    private final SemesterData semester;
    private final String grade;

    public PersonalModule(SemesterData semester, String grade) {
        this.semester = semester;
        this.grade = grade;
    }
}
