package hirelah.logic.parser;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirelah.logic.commands.CommandTestUtility.RANDOM_STRING;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE;
import static hirelah.logic.commands.CommandTestUtility.VALID_DOUBLE;
import static hirelah.logic.commands.CommandTestUtility.VALID_INTEGER;
import static hirelah.logic.commands.CommandTestUtility.VALID_METRIC_SINGLE;
import static hirelah.logic.commands.CommandTestUtility.VALID_NEGATIVE;
import static hirelah.logic.commands.CommandTestUtility.VALID_ZERO;
import static hirelah.logic.commands.CommandTestUtility.WHITESPACE;
import static hirelah.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static hirelah.logic.parser.CliSyntax.PREFIX_BEST;
import static hirelah.logic.parser.CliSyntax.PREFIX_METRIC;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import hirelah.logic.commands.BestCommand;
import hirelah.logic.commands.ListIntervieweeCommand;
import hirelah.model.hirelah.BestParameter;

public class IntervieweeParserTest {
    private static final String EMPTY_STRING = "";
    private IntervieweeParser parser = new IntervieweeParser();
    @Test
    public void parse_noArgument_success() {
        assertParseSuccess(parser, "", new ListIntervieweeCommand());
    }

    @Test
    public void parse_completeBestArgument_success() {
        assertParseSuccess(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER,
                new BestCommand(VALID_INTEGER, BestParameter.OVERALL));

        assertParseSuccess(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE,
                new BestCommand(VALID_INTEGER, VALID_ATTRIBUTE_PERSISTENCE, BestParameter.ATTRIBUTE));

        assertParseSuccess(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_METRIC
                + WHITESPACE + VALID_METRIC_SINGLE,
                new BestCommand(VALID_INTEGER, VALID_METRIC_SINGLE, BestParameter.METRIC));

    }

    @Test
    public void parse_noBestArgument_failure() {
        assertParseFailure(parser, WHITESPACE + RANDOM_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(IntervieweeParser.TEMPLATE,
                        ListIntervieweeCommand.MESSAGE_USAGE, BestCommand.MESSAGE_USAGE)));
    }

    @Test
    public void parse_numberOfIntervieweesNotInteger_failure() {
        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                        + WHITESPACE + VALID_DOUBLE
                        + WHITESPACE + PREFIX_ATTRIBUTE
                        + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                        + WHITESPACE + PREFIX_METRIC
                        + WHITESPACE + VALID_METRIC_SINGLE,
                BestCommandParser.MESSAGE_SIZE_NOT_AN_INTEGER);

        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                        + WHITESPACE + RANDOM_STRING
                        + WHITESPACE + PREFIX_ATTRIBUTE
                        + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                        + WHITESPACE + PREFIX_METRIC
                        + WHITESPACE + VALID_METRIC_SINGLE,
                BestCommandParser.MESSAGE_SIZE_NOT_AN_INTEGER);
    }

    @Test
    public void parse_numberOfIntervieweesNonPositive_failure() {
        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_ZERO
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_METRIC
                + WHITESPACE + VALID_METRIC_SINGLE,
                BestCommandParser.MESSAGE_NON_POSITIVE_SIZE);

        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_NEGATIVE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_METRIC
                + WHITESPACE + VALID_METRIC_SINGLE,
                BestCommandParser.MESSAGE_NON_POSITIVE_SIZE);
    }

    @Test
    public void parse_bestArgumentMissingProperty_success() {
        assertParseSuccess(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_ATTRIBUTE,
                new BestCommand(VALID_INTEGER, EMPTY_STRING, BestParameter.ATTRIBUTE));

        assertParseSuccess(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_METRIC,
                new BestCommand(VALID_INTEGER, EMPTY_STRING, BestParameter.METRIC));
    }

    @Test
    public void parse_bestArgumentMultipleProperty_failure() {
        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_METRIC
                + WHITESPACE + VALID_METRIC_SINGLE,
                BestCommandParser.MESSAGE_MULTIPLE_PARAMETERS_PROVIDED);

        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_METRIC,
                BestCommandParser.MESSAGE_MULTIPLE_PARAMETERS_PROVIDED);

        assertParseFailure(parser, WHITESPACE + PREFIX_BEST
                + WHITESPACE + VALID_INTEGER
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + PREFIX_METRIC
                + WHITESPACE + VALID_METRIC_SINGLE,
                BestCommandParser.MESSAGE_MULTIPLE_PARAMETERS_PROVIDED);
    }
}
