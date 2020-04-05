package seedu.address.model.notes;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Represents Notes of a student.
 * 4 main details are stored.
 * Student, Priority, Timestamp and Content.
 */
public class Notes {

    public static final String MESSAGE_CONSTRAINTS =
            "Student's name should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String PRIORITY_HIGH = "HIGH";
    public static final String PRIORITY_MEDIUM = "MEDIUM";
    public static final String PRIORITY_LOW = "LOW";

    private final String student;
    private final String content;
    private final String priority;
    private final String dateTime;

    /**
     * Notes constructor
     * Every field must be present.
     * Timestamp of note is automatically generated and saved.
     * @param student, representing the name of student.
     * @param content, representing the content to be stored in the note.
     * @param priority, representing the priority to be stored in the note.
     */
    public Notes(String student, String content, String priority) {
        requireAllNonNull(student, content, priority);
        checkArgument(isValidName(student), MESSAGE_CONSTRAINTS);
        this.student = student;
        this.content = content;
        this.priority = checkPriority(priority);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        this.dateTime = formatter.format(date);
    }

    /**
     * Overloaded Notes constructor which is used when Json data is drawn from addressbook.json
     * This allows initial timestamp to be immutable.
     * All fields must be present.
     * @param student
     * @param content
     * @param priority
     * @param dateTime
     */
    public Notes(String student, String content, String priority, String dateTime) {
        requireAllNonNull(student, content, priority, dateTime);
        checkArgument(isValidName(student), MESSAGE_CONSTRAINTS);
        this.student = student;
        this.content = content;
        this.priority = priority;
        this.dateTime = dateTime;
    }

    /**
     * Converts user input into formatted Priority.
     * @param priority
     * @return a String with formatted priority.
     */
    public String checkPriority(String priority) {
        switch (priority.toUpperCase()) {
        case PRIORITY_HIGH:
            return PRIORITY_HIGH;
        case PRIORITY_MEDIUM:
            return PRIORITY_MEDIUM;
        case PRIORITY_LOW:
            return PRIORITY_LOW;
        default:
            return PRIORITY_LOW;
        }
    }

    /**
     * Getter of String student
     * @return the student's name.
     */
    public String getStudent() {
        return this.student;
    }

    /**
     * Getter of String content
     * @return the note's content.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Getter of Note's timestamp
     * @return note's timestamp.
     */
    public String getDateTime() {
        return this.dateTime;
    }

    /**
     * Getter of Note's priority
     * @return note's priority.
     */
    public String getPriority() {
        return this.priority;
    }

    /**
     * Setter of String student
     * @param newStudent
     * @return a new Notes object with updated student.
     */
    public Notes setStudent(String newStudent) {
        return new Notes(newStudent, getContent(), getPriority());
    }

    /**
     * Setter of String content
     * @param newContent
     * @return a new Notes object with updated note content.
     */
    public Notes setContent(String newContent) {
        return new Notes(getStudent(), newContent, getPriority());
    }

    /**
     * Setter of String newPriority
     * @param newPriority
     * @return a new Notes object with updated note content.
     */
    public Notes setPriority(String newPriority) {
        return new Notes(getStudent(), getContent(), newPriority);
    }

    /**
     * Method which checks the validity of a Student's name.
     * @param test
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both notes have the same information.
     */
    public boolean isSameNote(Notes otherNotes) {
        return this.equals(otherNotes);
    }

    @Override
    public String toString() {
        return "[Notes]"
                + " Student:'" + getStudent() + '\''
                + ", Content: '" + getContent() + '\''
                + ", Priority: '" + getPriority() + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notes notes = (Notes) o;
        return Objects.equals(student, notes.student)
                && Objects.equals(content, notes.content)
                && Objects.equals(dateTime, notes.dateTime)
                && Objects.equals(priority, notes.priority);
    }

}
