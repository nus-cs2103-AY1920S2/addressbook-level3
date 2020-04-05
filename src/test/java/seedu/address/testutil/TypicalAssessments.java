package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MATH_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SCIENCE_EXAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MATH_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SCIENCE_EXAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_MATH_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_SCIENCE_EXAM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.academics.Academics;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.Submission;

/**
 * A utility class containing a list of {@code Assessment} objects to be used in tests.
 */
public class TypicalAssessments {

    public static final Submission[] SUBMISSIONS = {
        new Submission("Simon Lam"),
        new Submission("Gerren Seow"),
        new Submission("Lee Hui Ting"),
        new Submission("Gary Syndromes"),
        new Submission("Freddy Zhang")};

    public static final Assessment SCIENCE_HOMEWORK = new AssessmentBuilder().withDescription("Science Homework")
            .withSubmissions(SUBMISSIONS).withType("homework")
            .withDate("2020-03-04").build();
    public static final Assessment CHINESE_HOMEWORK = new AssessmentBuilder().withDescription("Chinese Homework")
            .withSubmissions(SUBMISSIONS).withType("homework")
            .withDate("2020-03-04").build();
    public static final Assessment ENGLISH_HOMEWORK = new AssessmentBuilder().withDescription("English Homework")
            .withSubmissions(SUBMISSIONS).withType("homework")
            .withDate("2020-03-04").build();
    public static final Assessment MATH_EXAM = new AssessmentBuilder().withDescription("Math Exam")
            .withSubmissions(SUBMISSIONS).withType("exam")
            .withDate("2020-03-04").build();
    public static final Assessment CHINESE_EXAM = new AssessmentBuilder().withDescription("Chinese Exam")
            .withSubmissions(SUBMISSIONS).withType("exam")
            .withDate("2020-03-04").build();
    public static final Assessment ENGLISH_EXAM = new AssessmentBuilder().withDescription("English Exam")
            .withSubmissions(SUBMISSIONS).withType("exam")
            .withDate("2020-03-04").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Assessment MATH_HOMEWORK = new AssessmentBuilder()
            .withDescription(VALID_DESCRIPTION_MATH_ASSIGNMENT)
            .withSubmissions(SUBMISSIONS).withType(VALID_TYPE_MATH_ASSIGNMENT)
            .withDate(VALID_DATE_MATH_ASSIGNMENT).build();
    public static final Assessment SCIENCE_EXAM = new AssessmentBuilder()
            .withDescription(VALID_DESCRIPTION_SCIENCE_EXAM)
            .withSubmissions(SUBMISSIONS).withType(VALID_TYPE_SCIENCE_EXAM)
            .withDate(VALID_DATE_SCIENCE_EXAM).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalAssessments() {} // prevents instantiation

    /**
     * Returns an {@code Academics} with all the typical assessments.
     */
    public static Academics getTypicalAcademics() {
        Academics acad = new Academics();
        for (Assessment assessment : getTypicalAssessments()) {
            acad.addAssessment(assessment);
        }
        return acad;
    }

    public static List<Assessment> getTypicalAssessments() {
        return new ArrayList<>(Arrays.asList(SCIENCE_HOMEWORK, CHINESE_HOMEWORK, ENGLISH_HOMEWORK,
                MATH_EXAM, CHINESE_EXAM, ENGLISH_EXAM));
    }
}
