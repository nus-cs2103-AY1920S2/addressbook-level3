package seedu.address.model.event.tutorial;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import seedu.address.model.event.Location;
import seedu.address.model.mod.ModCode;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * Represents a Tutorial in TAble.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Tutorial {

    public static final int NUM_OF_WEEKS = 11;

    private ModCode modCode;
    private TutorialName tutorialName;
    private DayOfWeek weekday;
    private LocalTime beginTime;
    private LocalTime endTime;
    private Location location;
    private ArrayList<Student> enrolledStudents;
    private ArrayList<ArrayList<Boolean>> studentAttendance;

    /**
     * Constructor for Tutorial to initialize an empty Tutorial class with no enrolled students, and
     * empty attendance sheet.
     * @param modCode Module code of the tutorial
     * @param tutorialName Name of the tutorial
     * @param weekday Day of the week that the tutorial takes place
     * @param beginTime Time that the tutorial begins
     * @param endTime Time that the tutorial ends
     * @param location Location of the tutorial
     */
    public Tutorial(ModCode modCode, TutorialName tutorialName, DayOfWeek weekday,
                    LocalTime beginTime, LocalTime endTime, Location location) {

        requireAllNonNull(modCode, tutorialName, weekday, beginTime, endTime, location);
        this.modCode = modCode;
        this.tutorialName = tutorialName;
        this.weekday = weekday;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        this.enrolledStudents = new ArrayList<Student>();

        ArrayList<ArrayList<Boolean>> studentAttendance = new ArrayList<ArrayList<Boolean>>();
        for (int i = 0; i < NUM_OF_WEEKS; i++) {
            ArrayList<Boolean> week = new ArrayList<Boolean>();
            studentAttendance.add(week);
        }
        this.studentAttendance = studentAttendance;
    }

    /**
     * Constructor for Tutorial to initialize a Tutorial class with enrolled students, and attendance sheet.
     * @param modCode Module code of the tutorial
     * @param tutorialName Name of the tutorial
     * @param weekday Day of the week that the tutorial takes place
     * @param beginTime Time that the tutorial begins
     * @param endTime Time that the tutorial ends
     * @param location Location of the tutorial
     * @param enrolledStudents ArrayList of enrolled Students
     * @param studentAttendance Attendance sheet for enrolled Students
     */
    public Tutorial(ModCode modCode, TutorialName tutorialName, DayOfWeek weekday,
                    LocalTime beginTime, LocalTime endTime, Location location, ArrayList<Student> enrolledStudents,
                    ArrayList<ArrayList<Boolean>> studentAttendance) {

        requireAllNonNull(modCode, tutorialName, weekday, beginTime, endTime, location, enrolledStudents,
                studentAttendance);
        this.modCode = modCode;
        this.tutorialName = tutorialName;
        this.weekday = weekday;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        this.enrolledStudents = enrolledStudents;
        this.studentAttendance = studentAttendance;
    }

    /**
     * Adds a new {@code student} to the tutorial,
     * and adds a new row in the attendance sheet for the student for all the weeks, default as false (ie. absent).
     */
    public void setEnrolledStudents(Student student) {
        enrolledStudents.add(student);
        for (int i = 0; i < NUM_OF_WEEKS; i++) {
            ArrayList<Boolean> week = studentAttendance.get(i);
            week.add(false);
        }
    }

    /**
     * Removes given {@code student} from the tutorial
     * and removes the row in the attendance sheet corresponding to the student for all the weeks.
     */
    public void removeEnrolledStudent(Student student) {
        int targetIndex = enrolledStudents.indexOf(student);
        enrolledStudents.remove(student);
        for (int i = 0; i < NUM_OF_WEEKS; i++) {
            ArrayList<Boolean> week = studentAttendance.get(i);
            week.remove(targetIndex);
        }
    }

    /**
     * Marks the given {@code student} as present in the tutorial for the specified week.
     */
    public void markPresent(Student student, int week) {
        int targetIndex = enrolledStudents.indexOf(student);
        studentAttendance.get(week).set(targetIndex, true);
    }

    /**
     * Marks the given {@code student} as absent in the tutorial for the specified week.
     */
    public void markAbsent(Student student, int week) {
        int targetIndex = enrolledStudents.indexOf(student);
        studentAttendance.get(week).set(targetIndex, false);
    }

    /**
     * Copies the list of students' emails onto the user's OS clipboard.
     */
    public void copyTutorialEmails() {
        String[] emails = new String[enrolledStudents.size()];
        for (int i = 0; i < enrolledStudents.size(); i++) {
            emails[i] = enrolledStudents.get(i).getEmail().toString();
        }
        String emailStr = String.join("; ", emails);

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent emailListString = new ClipboardContent();
        emailListString.putString(emailStr);
        clipboard.setContent(emailListString);
    }

    public ModCode getModCode() {
        return modCode;
    }

    public TutorialName getTutorialName() {
        return tutorialName;
    }

    public DayOfWeek getDay() {
        return weekday;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    public String getDayAndTime() {
        String day = weekday.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return day + " " + beginTime.format(formatter) + "-" + endTime.format(formatter);
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Student getEnrolledStudentAt(int index) {
        return this.enrolledStudents.get(index);
    }

    public ArrayList<ArrayList<Boolean>> getAttendance() {
        return studentAttendance;
    }

    /**
     * Returns the attendance of a tutorial for a specified week
     * @param week Specified week of attendance to be retrieved
     * @return ArrayList of attendance represented by Boolean for the specified week
     */
    public ArrayList<Boolean> getAttendanceWeek(int week) {
        return studentAttendance.get(week);
    }

    /**
     * Returns attendance for whole semester of specified student
     */
    public ArrayList<Boolean> getAttendanceofStudent(Student student) throws IndexOutOfBoundsException {
        int index = enrolledStudents.indexOf(student);
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Boolean> attendanceList = new ArrayList<Boolean>();
        for (ArrayList<Boolean> week : studentAttendance) {
            attendanceList.add(week.get(index));
        }
        return attendanceList;
    }

    /**
     * Returns attendance for whole semester of student at given index in tutorial's enrolled students.
     */
    public ArrayList<Boolean> getAttendanceofStudent(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= enrolledStudents.size()) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Boolean> attendanceList = new ArrayList<Boolean>();
        for (ArrayList<Boolean> week : studentAttendance) {
            attendanceList.add(week.get(index));
        }
        return attendanceList;
    }

    /**
     * Returns true if both tutorials have the same module and tutorial name fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        // same tutorial as long as same modCode and tutorialName
        Tutorial otherTutorial = (Tutorial) other;
        return otherTutorial.getModCode().equals(getModCode())
                && otherTutorial.getTutorialName().equals(getTutorialName());
    }

    /**
     * Returns true if both tutorials timing clash, where the tutorials are set in periods
     * which overlap.
     */
    public boolean timeClash(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial)) {
            return false;
        }

        // no time clash if same day
        Tutorial otherTutorial = (Tutorial) other;
        if (otherTutorial.getDay().getValue() != this.getDay().getValue()) {
            return false;
        }

        // other tutorial is existing tutorial
        // this tutorial is tutorial to be added
        return !((getBeginTime().compareTo(otherTutorial.getBeginTime()) < 0
                && getEndTime().compareTo(otherTutorial.getBeginTime()) <= 0)
                || (getBeginTime().compareTo(otherTutorial.getEndTime()) >= 0
                && getEndTime().compareTo(otherTutorial.getEndTime()) > 0));
    }

    /**
     * Returns true if the other tutorial already contains the specified student.
     */
    public boolean tutorialStudentClash(Student student) {
        boolean hasDuplicateStudent = false;
        for (Student s : this.getEnrolledStudents()) {
            if (s.getMatricNumber().equals(student.getMatricNumber())) {
                hasDuplicateStudent = true;
                break;
            }
        }
        return hasDuplicateStudent;
    }

    /**
     * Replaces the student {@code studentToEdit} in the list with {@code editedStudent}.
     * {@code target} must exist in the list.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the list.
     */
    public void editEnrolledStudent(Student studentToEdit, Student editedStudent) {
        requireAllNonNull(studentToEdit, editedStudent);

        int index = enrolledStudents.indexOf(studentToEdit);
        if (index == -1) {
            throw new StudentNotFoundException();
        }

        if (!studentToEdit.isSameStudent(editedStudent) && enrolledStudents.contains(editedStudent)) {
            throw new DuplicateStudentException();
        }

        enrolledStudents.set(index, editedStudent);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Module Code: ")
                .append(getModCode())
                .append(" Tutorial Name: ")
                .append(getTutorialName())
                .append(" Day: ")
                .append(getDay())
                .append(" Begin TIme: ")
                .append(getBeginTime())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Location: ")
                .append(getLocation());
        return builder.toString();
    }
}
