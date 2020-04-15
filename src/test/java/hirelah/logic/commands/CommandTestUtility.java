package hirelah.logic.commands;

/**
 * Contains helper fields for testing commands.
 */
public class CommandTestUtility {
    public static final String VALID_COMMAND_ADD = "add";
    public static final String VALID_COMMAND_EDIT = "edit";
    public static final String VALID_COMMAND_DELETE = "delete";
    public static final String VALID_PLURAL_ATTRIBUTE = "attributes";
    public static final String VALID_PLURAL_INTERVIEWEE = "interviewees";
    public static final String VALID_PLURAL_METRIC = "metrics";
    public static final String VALID_PLURAL_QUESTION = "questions";
    public static final String VALID_COMMAND_FINALISE = "finalise";
    public static final String VALID_COMMAND_OPEN = "open";
    public static final String VALID_COMMAND_GOTO = "goto";
    public static final String VALID_COMMAND_INTERVIEW = "interview";
    public static final String VALID_COMMAND_EXIT = "exit";
    public static final String VALID_COMMAND_HELP = "help";
    public static final String VALID_COMMAND_RESUME = "resume";

    public static final String VALID_INTERVIEW_COMMAND_END = ":end";
    public static final String VALID_INTERVIEW_COMMAND_START = ":start";
    public static final String VALID_INTERVIEW_COMMAND_ATTRIBUTES = ":attributes";
    public static final String VALID_INTERVIEW_COMMAND_QUESTIONS = ":questions";
    public static final String VALID_INTERVIEW_COMMAND_METRICS = ":metrics";
    public static final String VALID_INTERVIEW_COMMAND_SET = ":set";


    public static final String VALID_PROPERTY_INTERVIEWEE = "interviewee";
    public static final String VALID_PROPERTY_ATTRIBUTE = "attribute";
    public static final String VALID_PROPERTY_QUESTION = "question";
    public static final String VALID_PROPERTY_METRIC = "metric";

    public static final String VALID_ALIAS_JANE = "Jane";
    public static final String VALID_INTERVIEWEE_JANE = "Jane Doe";
    public static final String VALID_INTERVIEWEE_JANICE = "Janice Doe";

    public static final String VALID_ATTRIBUTE_TEAM_WORK = "team work";
    public static final String VALID_ATTRIBUTE_PERSISTENCE = "persistence";
    public static final String VALID_ATTRIBUTE_INTEGRITY = "integrity";

    public static final String VALID_METRIC_SINGLE = "single";
    public static final String VALID_METRIC_LONG = "this is a very long metric name";
    public static final String VALID_METRIC_EDITED = "this is an edited metric name";
    public static final String VALID_SCORE_INTEGER = "5";
    public static final String VALID_SCORE_DOUBLE = "3.14";

    public static final String VALID_QUESTION_WHAT = "What is this question?";
    public static final String VALID_NUMBER_1 = "1";
    public static final String VALID_QUESTION_NUMBER_14 = "q14";
    public static final String VALID_QUESTION_NUMBER_1 = "q1";
    public static final String VALID_TIME_30 = "30.00";
    public static final String VALID_TIME_123 = "123.45";
    public static final String VALID_ATTRIBUTE_SCORE_1 = "5.35";
    public static final String VALID_ATTRIBUTE_SCORE_2 = "10";

    public static final String VALID_SESSION = "CEO Interview";

    public static final int VALID_INTEGER = 5;
    public static final int VALID_ZERO = 0;
    public static final int VALID_NEGATIVE = -1;
    public static final double VALID_DOUBLE = 3.14;

    public static final String INVALID_QUESTION_NUMBER_1 = "q14a";
    public static final String INVALID_QUESTION_NUMBER_2 = "qabc";
    public static final String INVALID_QUESTION_NUMBER_3 = "q 154";
    public static final String INVALID_QUESTION_BLANK = "q";
    public static final String INVALID_TIME_123 = "123,2";
    public static final String INVALID_TIME_1234 = "1234";

    public static final String VALID_PATH_DOWNLOADS = "/Downloads";
    public static final String INVALID_DUMMY_VALUE = "dummy";

    public static final String INVALID_SCORE_CONTAINS_ALPHABETS = "12s4";

    public static final String WHITESPACE = " ";
    public static final String RANDOM_STRING = "This is a very random string";
}
