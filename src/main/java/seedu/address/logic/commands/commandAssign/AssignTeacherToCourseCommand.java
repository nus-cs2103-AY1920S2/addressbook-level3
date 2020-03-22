package seedu.address.logic.commands.commandAssign;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.person.ID;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import java.util.List;
import java.util.Set;

/** This class will be in charge of assigning stuff (e.g students, teacher, etc) to a course. */
public class AssignTeacherToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";

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
    public CommandResult execute(Model model) throws CommandException {
        List<Course> lastShownList = model.getFilteredCourseList();
        // TODO: Override equals by checking ID only then no need for this "stupid" for loop
        for (Course course : lastShownList) {
            if (course.getId().value.equals(this.assignDescriptor.getAssignID(PREFIX_COURSEID).value)) {
                Course targetCourse = course;
                targetCourse.setTeacherID(this.assignDescriptor.getAssignID(PREFIX_TEACHERID));
                model.setCourse(targetCourse, targetCourse);
                model.updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
                return new CommandResult(String.format(MESSAGE_ASSIGNMENT_SUCCESS));
            }
        }
        throw new CommandException(MESSAGE_INVALID_COURSE_ID);
    }
}
