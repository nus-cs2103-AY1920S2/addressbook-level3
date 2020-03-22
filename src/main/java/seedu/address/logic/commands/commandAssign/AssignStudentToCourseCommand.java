package seedu.address.logic.commands.commandAssign;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddAssignmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourseStudent.CourseStudent;
import seedu.address.model.person.Courseid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Studentid;
import seedu.address.model.tag.Tag;

/** This class will be in charge of assigning stuff (e.g students, teacher, etc) to a course. */
public class AssignStudentToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "Invalid course id";

    private final AssignDescriptor assignDescriptor;
    private Set<Tag> ArrayList;
    public static final String MESSAGE_DUPLICATE_COURSESTUDENT = "This student has already been assigned to this course";

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
        List<Course> lastShownList = model.getFilteredCourseList();
        // TODO: Override equals by checking ID only then no need for this "stupid" for loop
        String courseidString = this.assignDescriptor.getAssignID(PREFIX_COURSEID).value;
        String studentidString = this.assignDescriptor.getAssignID(PREFIX_STUDENTID).value;

        for (Course course : lastShownList) {
            if (course.getId().value.equals(courseidString)) {
                Courseid courseid = ParserUtil.parseCourseid(courseidString);
                Studentid studentid = ParserUtil.parseStudentid(studentidString);

                // TODO: Allow adding of tags
                CourseStudent courseStudent = new CourseStudent(courseid, studentid, new HashSet<Tag>());
                if (model.hasCourseStudent(courseStudent)) {
                    throw new CommandException(MESSAGE_DUPLICATE_COURSESTUDENT);
                }

                model.addCourseStudent(courseStudent);

                return new CommandResult(String.format(MESSAGE_ASSIGNMENT_SUCCESS));
            }
        }
        throw new CommandException(MESSAGE_INVALID_COURSE_ID);
    }
}
