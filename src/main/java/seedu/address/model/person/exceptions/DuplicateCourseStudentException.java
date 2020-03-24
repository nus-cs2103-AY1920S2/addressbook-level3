package seedu.address.model.person.exceptions;

public class DuplicateCourseStudentException extends RuntimeException{
    public DuplicateCourseStudentException() {
        super("Operation would result in duplicate CourseStudent");
    }
}
