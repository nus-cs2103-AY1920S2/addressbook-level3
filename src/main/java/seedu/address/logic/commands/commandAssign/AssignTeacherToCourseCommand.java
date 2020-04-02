package seedu.address.logic.commands.commandAssign;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import java.util.Set;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.person.ID;

/** This class will be in charge of assigning stuff (e.g students, staff, etc) to a course. */
public class AssignTeacherToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_TEACHER_ID = "There is no such staff that with ID";

    public static final String MESSAGE_SUCCESS = "Successfully assigned staff %s(%s) to course %s(%s)";

    private final AssignDescriptor assignDescriptor;

    public AssignTeacherToCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_TEACHERID);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        String courseidString = this.assignDescriptor.getAssignID(PREFIX_COURSEID).value;
        String staffidString = this.assignDescriptor.getAssignID(PREFIX_TEACHERID).value;
        String courseName = "";
        String staffName = "";

        boolean courseExists = false;
        boolean staffExists = false;
        Course foundCourse = null;
        Staff foundStaff = null;

        for (Course course : model.getFilteredCourseList()) {
            if (course.getId().value.equals(courseidString)) {
                courseName = course.getName().toString();
                courseExists = true;
                foundCourse = course;
                break;
            }
        }

        for (Staff staff : model.getFilteredStaffList()) {
            if (staff.getId().value.equals(staffidString)) {
                staffName = staff.getName().toString();
                staffExists = true;
                foundStaff = staff;
                break;
            }
        }

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!staffExists) {
            throw new CommandException(MESSAGE_INVALID_TEACHER_ID);
        } else {
            ID courseid = ParserUtil.parseCourseid(courseidString);
            ID staffid = ParserUtil.parseStaffid(staffidString);

            foundCourse.assignStaff(staffid);
            foundStaff.addCourse(courseid);

            foundCourse.processAssignedStaff(
                (FilteredList<Staff>) model.getFilteredStaffList());
            foundStaff.processAssignedCourses(
                (FilteredList<Course>) model.getFilteredCourseList());
            // Just to direct flow from calling models directly to modelManager to make better use of callback
            // A bit weird design for now
            model.set(foundCourse, foundCourse);
            model.set(foundStaff, foundStaff);

            return new CommandResult(String.format(MESSAGE_SUCCESS, staffName, staffidString, courseName, courseidString));
        }
    }
}
