package hirelah.logic.parser;

import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE;
import static hirelah.logic.commands.CommandTestUtility.VALID_METRIC_EDITED;
import static hirelah.logic.commands.CommandTestUtility.VALID_METRIC_LONG;
import static hirelah.logic.commands.CommandTestUtility.VALID_METRIC_SINGLE;
import static hirelah.logic.commands.CommandTestUtility.VALID_SCORE_DOUBLE;
import static hirelah.logic.commands.CommandTestUtility.VALID_SCORE_INTEGER;
import static hirelah.logic.commands.CommandTestUtility.WHITESPACE;
import static hirelah.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static hirelah.logic.parser.CliSyntax.PREFIX_NAME;
import static hirelah.logic.parser.CliSyntax.PREFIX_WEIGHTAGE;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import hirelah.logic.commands.EditMetricCommand;

public class EditMetricCommandParserTest {
    private static final String EMPTY_STRING = "";
    private EditMetricCommandParser parser = new EditMetricCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                new EditMetricCommand(VALID_METRIC_EDITED, VALID_METRIC_SINGLE, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_INTEGER)))));

        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_LONG
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                new EditMetricCommand(VALID_METRIC_EDITED, VALID_METRIC_LONG, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_INTEGER)))));

        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_DOUBLE,
                new EditMetricCommand(VALID_METRIC_EDITED, VALID_METRIC_SINGLE, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_DOUBLE)))));
    }

    @Test
    public void parse_editedNameAbsent_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                new EditMetricCommand(VALID_METRIC_EDITED, EMPTY_STRING, new ArrayList<>(
                        Collections.singletonList(VALID_ATTRIBUTE_PERSISTENCE)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_INTEGER)))));
    }

    @Test
    public void parse_attributeAndWeightAbsent_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE,
                new EditMetricCommand(VALID_METRIC_EDITED, VALID_METRIC_SINGLE, new ArrayList<>(),
                        new ArrayList<>()));
    }

    @Test
    public void parse_onlyAttributeAbsent_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                String.format(EditMetricCommandParser.MESSAGE_INCOMPLETE_ARGUMENT, EditMetricCommand.MESSAGE_USAGE));

        assertParseFailure(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                String.format(EditMetricCommandParser.MESSAGE_INCOMPLETE_ARGUMENT, EditMetricCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyWeightAbsent_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE,
                String.format(EditMetricCommandParser.MESSAGE_INCOMPLETE_ARGUMENT, EditMetricCommand.MESSAGE_USAGE));

        assertParseFailure(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE,
                String.format(EditMetricCommandParser.MESSAGE_INCOMPLETE_ARGUMENT, EditMetricCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_weightMissingValue_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE,
                EditMetricCommandParser.MESSAGE_INVALID_WEIGHTAGE_FORMAT);

        assertParseFailure(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                + WHITESPACE + PREFIX_WEIGHTAGE,
                EditMetricCommandParser.MESSAGE_INVALID_WEIGHTAGE_FORMAT);
    }

    @Test
    public void parse_attributeMissingValue_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_METRIC_EDITED
                + WHITESPACE + PREFIX_NAME
                + WHITESPACE + VALID_METRIC_SINGLE
                + WHITESPACE + PREFIX_ATTRIBUTE
                + WHITESPACE + PREFIX_WEIGHTAGE
                + WHITESPACE + VALID_SCORE_INTEGER,
                new EditMetricCommand(VALID_METRIC_EDITED, VALID_METRIC_SINGLE, new ArrayList<>(
                        Collections.singletonList(EMPTY_STRING)),
                        new ArrayList<>(Collections.singletonList(Double.parseDouble(VALID_SCORE_INTEGER)))));
    }
}
