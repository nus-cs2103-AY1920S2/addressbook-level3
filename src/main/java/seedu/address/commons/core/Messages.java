package seedu.address.commons.core;

//@@author wanxuanong
/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_COURSE = "Error: This course is invalid or unavailable";
    public static final String MESSAGE_INVALID_COURSE_FOCUS_AREA = "Error: This focus area is not available";
    public static final String MESSAGE_INVALID_MODULE = "Error: Invalid module code(s): %1$s";
    public static final String MESSAGE_INVALID_SEMESTER = "Error: No modules have been added to this semester";

    public static final String MESSAGE_EMPTY_PROFILE_LIST = "Error: No profile has been created.";
    public static final String MESSAGE_EMPTY_MODULE_DATA = "No module data has been added to any semesters.";

    public static final String MESSAGE_MISSING_COURSE = "Error: Please input a course name";
    public static final String MESSAGE_MISSING_COURSE_FOCUS_AREA = "Error: Please input a focus area";
    public static final String MESSAGE_MISSING_MODULE = "Error: Please input a module code";
    public static final String MESSAGE_MISSING_SEMESTER = "Error: Please input a semester";
    public static final String MESSAGE_MISSING_NAME = "Error: Please input a name";
    public static final String MESSAGE_MISSING_OLD_TASK = "Error: Please provide the name of the task you are trying "
            + "to edit.";
    public static final String MESSAGE_MISSING_NEW_TASK_OR_DEADLINE = "Error: Please specify a new task name or "
            + "deadline to be edited.";

    public static final String MESSAGE_MODULE_NOT_ADDED = "Error: This module has not been added before.";
    public static final String MESSAGE_MULTIPLE_MODULES_ADD_TASK = "Error: You cannot add a task to multiple modules";
    public static final String MESSAGE_MULTIPLE_MODULES_DELETE_TASK = "Error: You cannot delete a task from "
            + "multiple modules";
    public static final String MESSAGE_ADD_FUTURE_GRADE_ERROR = "You cannot add a grade to future semesters!";
    public static final String MESSAGE_DEADLINE_DOES_NOT_EXIST = "Error: Deadline to be edited does not exist";
    public static final String MESSAGE_DUPLICATE_TASK = "You have already added a task with the same task name to this module!";
}
