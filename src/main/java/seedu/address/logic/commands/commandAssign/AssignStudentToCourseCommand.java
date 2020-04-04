package seedu.address.logic.commands.commandAssign;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.HashMap;
import java.util.Set;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

/** This class will be in charge of assigning stuff (e.g students, teacher, etc) to a course. */
public class AssignStudentToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID!";
    public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such student that with ID!";
    public static final String MESSAGE_COURSE_ALREADY_CONTAINS_STUDENT = "Course already has that student assigned to it!";
    public static final String MESSAGE_STUDENT_ALREADY_COURSE = "Student is assigned to the course already!";

    public static final String MESSAGE_SUCCESS = "Successfully assigned student %s (%s) to course %s (%s)";

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

        // Check whether both IDs even exists
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID studentID = this.assignDescriptor.getAssignID(PREFIX_STUDENTID);

        boolean courseExists = model.hasCourse(courseID);
        boolean studentExists = model.hasStudent(studentID);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!studentExists) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_ID);
        } else {
            Course assignedCourse = model.getCourse(courseID);
            Student assigningStudent = model.getStudent(studentID);

            boolean assignedCourseContainsStudent = assignedCourse.containsStudent(studentID);
            boolean assigningStudentContainsCourse = assigningStudent.containsCourse(courseID);

            if (assignedCourseContainsStudent) {
                throw new CommandException(MESSAGE_COURSE_ALREADY_CONTAINS_STUDENT);
            } else if (assigningStudentContainsCourse) {
            throw new CommandException(MESSAGE_STUDENT_ALREADY_COURSE);
            } else {
                EdgeManager.assignStudentToCourse(studentID, courseID);

                return new CommandResult(String.format(MESSAGE_SUCCESS,
                        assigningStudent.getName(), studentID.value,
                        assignedCourse.getName(), courseID.value));

            }
        }
    }
}
