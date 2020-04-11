package hirelah.logic.parser;

import static hirelah.logic.commands.CommandTestUtility.INVALID_SCORE_CONTAINS_ALPHABETS;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE;
import static hirelah.logic.commands.CommandTestUtility.VALID_METRIC_LONG;
import static hirelah.logic.commands.CommandTestUtility.VALID_METRIC_SINGLE;
import static hirelah.logic.commands.CommandTestUtility.VALID_SCORE_DOUBLE;
import static hirelah.logic.commands.CommandTestUtility.VALID_SCORE_INTEGER;
import static hirelah.logic.commands.CommandTestUtility.WHITESPACE;
import static hirelah.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static hirelah.logic.parser.CliSyntax.PREFIX_WEIGHTAGE;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import hirelah.logic.commands.AddMetricCommand;

public class AddMetricCommandParserTest {
    private AddMetricCommandParser parser = new AddMetricCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                new AddMetricCommand(VALID_METRIC_SINGLE, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_INTEGER)))));

        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_DOUBLE,
                new AddMetricCommand(VALID_METRIC_SINGLE, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_DOUBLE)))));

        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_LONG
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                new AddMetricCommand(VALID_METRIC_LONG, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_INTEGER)))));
    }

    @Test
    public void parse_allFieldsPresent_invalidWeight() {
        assertParseFailure(parser, WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + INVALID_SCORE_CONTAINS_ALPHABETS,
                AddMetricCommandParser.MESSAGE_INVALID_WEIGHTAGE_FORMAT);
    }

    @Test
    public void parse_missingAttributeField() {
        assertParseFailure(parser, WHITESPACE + VALID_METRIC_SINGLE
                        + WHITESPACE + PREFIX_WEIGHTAGE
                        + WHITESPACE + VALID_SCORE_INTEGER,
                String.format(AddMetricCommandParser.MESSAGE_INCOMPLETE_ARGUMENT, AddMetricCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingWeightageField() {
        assertParseFailure(parser, WHITESPACE + VALID_METRIC_SINGLE
                        + WHITESPACE + PREFIX_ATTRIBUTE
                        + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE,
                String.format(AddMetricCommandParser.MESSAGE_INCOMPLETE_ARGUMENT, AddMetricCommand.MESSAGE_USAGE));
    }
}
