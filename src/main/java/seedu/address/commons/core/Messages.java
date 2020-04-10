package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_COURSE = "Error: This course is invalid or unavailable";
    public static final String MESSAGE_INVALID_COURSE_FOCUS_AREA = "Error: This focus area is not available";
    public static final String MESSAGE_INVALID_MODULE = "Error: This module does not exist";
    public static final String MESSAGE_INVALID_SEMESTER = "Error: No modules have been added to this semester";
    public static final String MESSAGE_INVALID_SEMESTER_FORMAT = "Please enter the year in the form Y.S, where "
            + "Y is the year (positive integer less than 10) and S is the semester (1 or 2).";
    public static final String MESSAGE_INVALID_GRADE = "Module's grade field should contain only "
            + "one of these values: [A+, A, A-, B+, B, B-, C+, C, D+, D, F, S, U, CS, CU]";
    public static final String MESSAGE_INVALID_DATETIME = "Error: Invalid date or time!";

    public static final String MESSAGE_EMPTY_PROFILE_LIST = "Error: No profile has been created.";
    public static final String MESSAGE_EMPTY_MODULE_DATA = "No module data has been added to any semesters.";

    public static final String MESSAGE_MISSING_COURSE = "Error: Please input a course name";
    public static final String MESSAGE_MISSING_COURSE_FOCUS_AREA = "Error: Please input a focus area";
    public static final String MESSAGE_MISSING_MODULE = "Error: Please input a module code";
    public static final String MESSAGE_MISSING_SEMESTER = "Error: Please input a semester";
    public static final String MESSAGE_MISSING_NAME = "Error: Please input a name";
    public static final String MESSAGE_MISSING_FOCUS_AREA = "Error: Please input a focus area";
    public static final String MESSAGE_MISSING_OLD_TASK = "Error: Please provide the name of the task you are trying "
            + "to edit.";
    public static final String MESSAGE_MISSING_NEW_TASK_OR_DEADLINE = "Error: Please specify a new task name or "
            + "deadline to be edited.";

    public static final String MESSAGE_MODULE_NOT_ADDED = "Error: This module has not been added before.";
    public static final String MESSAGE_ADD_FUTURE_GRADE_ERROR = "You cannot add a grade to future semesters!";
    public static final String MESSAGE_DEADLINE_DOES_NOT_EXIST = "Error: Deadline to be edited does not exist";

    public static final String MESSAGE_DUPLICATE_MODULE = "Error: Module already exists as PLANNING, "
            + "please specify specify name and deadline if you would like to add a task";
}
