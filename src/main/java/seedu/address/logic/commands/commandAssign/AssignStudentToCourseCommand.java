package seedu.address.logic.commands.commandAssign;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.Set;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

/** This class will be in charge of assigning stuff (e.g students, teacher, etc) to a course. */
public class AssignStudentToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such student that with ID";
    public static final String MESSAGE_SUCCESS = "Successfully added student %s(%s) to course %s(%s)";

    private final AssignDescriptor assignDescriptor;
    private Set<Tag> ArrayList;

    public AssignStudentToCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_STUDENTID);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        // if student exists
        // if course exists
        // if student not already assigned to the course
        // if course doesn't already have the student

        ID courseid = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID studentid = this.assignDescriptor.getAssignID(PREFIX_STUDENTID);

        boolean courseExists = model.hasCourse(courseid);
        boolean studentExists = model.hasStudent(studentid);

        String courseName = "";
        String studentName = "";

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!studentExists) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_ID);
        } else {
            Course foundCourse = model.getCourse(courseid);
            Student foundStudent = model.getStudent(studentid);
            foundCourse.addStudent(studentid);
            foundStudent.addCourse(courseid);
            foundCourse.processAssignedStudents(
                (FilteredList<Student>) model.getFilteredStudentList());
            foundStudent.processAssignedCourses(
                (FilteredList<Course>) model.getFilteredCourseList());
            model.updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, studentName, studentid.value, courseName, courseid.value));
        }

    }
}
