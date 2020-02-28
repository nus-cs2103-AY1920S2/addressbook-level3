package seedu.address.observer;

import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.StudentAddressBook;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.util.Constant;

import java.util.ArrayList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CourseParticipantObserver implements PropertyChangeListener {
    public CourseParticipantObserver(StudentAddressBook studentAddressBook, CourseAddressBook courseAddressBook) {
        studentAddressBook.addChangeListener(this);
        courseAddressBook.addChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String eventPropertyName = event.getPropertyName();
        if (eventPropertyName.equals(Constant.COURSE_REMOVE_SIGNAL)) {
            Course removedCourse = (Course) event.getNewValue();
            // TODO: Synchronize student address book
        } else if (eventPropertyName.equals(Constant.STUDENT_REMOVE_SIGNAL)) {
            Student removedStudent = (Student) event.getNewValue();
            // TODO: Synchronize course address book
        }
    }
}
