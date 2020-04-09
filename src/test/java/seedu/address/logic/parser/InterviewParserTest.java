package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListAttributeCommand;
import seedu.address.logic.commands.ListMetricCommand;
import seedu.address.logic.commands.ListQuestionCommand;
import seedu.address.logic.commands.OpenResumeCommand;
import seedu.address.logic.commands.UploadResumeCommand;
import seedu.address.logic.commands.interview.EndCommand;
import seedu.address.logic.commands.interview.ScoreCommand;
import seedu.address.logic.commands.interview.StartQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_DUMMY_VALUE;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_QUESTION_BLANK;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_QUESTION_NUMBER_1;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_SCORE_1;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_SCORE_2;
import static seedu.address.logic.commands.CommandTestUtility.VALID_COMMAND_RESUME;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEW_COMMAND_ATTRIBUTES;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEW_COMMAND_END;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEW_COMMAND_METRICS;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEW_COMMAND_QUESTIONS;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEW_COMMAND_SET;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEW_COMMAND_START;
import static seedu.address.logic.commands.CommandTestUtility.VALID_QUESTION_NUMBER_1;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;

class InterviewParserTest {

    InterviewParser parser = new InterviewParser();

    @Test
    void parseCommand_openResume_success() throws ParseException {
        Command result = parser.parseCommand(VALID_COMMAND_RESUME + WHITESPACE + VALID_INTERVIEWEE_JANE);
        assertEquals(result, new OpenResumeCommand("Jane Doe"));
    }

    @Test
    void parseCommand_openResume_failure() throws ParseException {
        Command result = parser.parseCommand(VALID_COMMAND_RESUME + WHITESPACE);
        assertEquals(result, new OpenResumeCommand(""));
    }

    @Test
    void parseCommand_endInterview_success() throws ParseException {
        Command result = parser.parseCommand(VALID_INTERVIEW_COMMAND_END);
        assertEquals(result, new EndCommand());
    }

    @Test
    void parseCommand_listOfAttributes_success() throws ParseException {
        Command result = parser.parseCommand(VALID_INTERVIEW_COMMAND_ATTRIBUTES);
        assertEquals(result, new ListAttributeCommand());
    }

    @Test
    void parseCommand_listOfQuestions_success() throws ParseException {
        Command result = parser.parseCommand(VALID_INTERVIEW_COMMAND_QUESTIONS);
        assertEquals(result, new ListQuestionCommand());
    }

    @Test
    void parseCommand_listOfMetrics_success() throws ParseException {
        Command result = parser.parseCommand(VALID_INTERVIEW_COMMAND_METRICS);
        assertEquals(result, new ListMetricCommand());
    }

    @Test
    void parseCommand_startQuestion_success() throws ParseException {
        Command result = parser.parseCommand(VALID_INTERVIEW_COMMAND_START + WHITESPACE + VALID_QUESTION_NUMBER_1);
        assertEquals(result, new StartQuestionCommand(1));
    }

    @Test
    void parseCommand_startQuestion_failure() throws ParseException {
        Assert.assertThrows(ParseException.class, Messages.INVALID_QUESTION_NUMBER_MESSAGE,
                () -> parser.parseCommand(VALID_INTERVIEW_COMMAND_START + WHITESPACE + INVALID_QUESTION_NUMBER_1));

        Assert.assertThrows(ParseException.class, Messages.INVALID_QUESTION_NUMBER_MESSAGE,
                () -> parser.parseCommand(VALID_INTERVIEW_COMMAND_START
                        + WHITESPACE + INVALID_QUESTION_NUMBER_1 + INVALID_DUMMY_VALUE));

        Assert.assertThrows(ParseException.class, Messages.INVALID_QUESTION_NUMBER_MESSAGE,
                () -> parser.parseCommand(VALID_INTERVIEW_COMMAND_START
                        + WHITESPACE + INVALID_QUESTION_BLANK));
    }

    @Test
    void parseCommand_setAttributeScore_success() throws ParseException {
        Command firstResult = parser.parseCommand(VALID_INTERVIEW_COMMAND_SET + WHITESPACE
                + VALID_ATTRIBUTE_INTEGRITY + WHITESPACE + VALID_ATTRIBUTE_SCORE_1);
        assertEquals(firstResult, new ScoreCommand("integrity", 5.35));

        Command secondResult = parser.parseCommand(VALID_INTERVIEW_COMMAND_SET + WHITESPACE
                + VALID_ATTRIBUTE_PERSISTENCE + WHITESPACE + VALID_ATTRIBUTE_SCORE_2);
        assertEquals(secondResult, new ScoreCommand("persistence", 10));
    }
}
