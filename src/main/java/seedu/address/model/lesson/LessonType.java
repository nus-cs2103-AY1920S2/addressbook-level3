package seedu.address.model.lesson;

/**
 * Represents the different types of lessons.
 */
public enum LessonType {
    LEC, TUT, SEC, REC, LAB;

    public static final String MESSAGE_CONSTRAINTS =
            "Class type can only be either LEC, TUT, SEC, REC or LAB and should not be blank";
}

