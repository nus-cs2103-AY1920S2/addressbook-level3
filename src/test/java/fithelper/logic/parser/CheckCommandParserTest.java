package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.commands.CommandTestUtil.APPLE;
import static fithelper.logic.commands.CommandTestUtil.BANANA;
import static fithelper.logic.commands.CommandTestUtil.FOOD;
import static fithelper.logic.commands.CommandTestUtil.FOOD_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.SPORTS;
import static fithelper.logic.commands.CommandTestUtil.SPORTS_ACRONYM;
import static fithelper.logic.commands.CommandTestUtil.SWIM;
import static fithelper.logic.commands.CommandTestUtil.WHITE_SPACE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fithelper.logic.commands.CheckCommand;
import fithelper.model.entry.Type;

public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_fieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);

        // type missing
        assertParseFailure(parser, WHITE_SPACE + PREFIX_KEYWORD + APPLE, expectedMessage);

        // keywords missing
        assertParseFailure(parser, WHITE_SPACE + PREFIX_TYPE + FOOD, expectedMessage);

        // both missing
        assertParseFailure(parser, WHITE_SPACE, expectedMessage);
    }

    @Test
    public void parse_invalidFieldValues_failure() {
        // invalid type
        assertParseFailure(parser, WHITE_SPACE + PREFIX_TYPE + "exercise" + WHITE_SPACE + PREFIX_KEYWORD
                + APPLE, Type.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateFieldsPresent_success() {
        // for all cases where there are duplicate inputs for the same field, only the last input is read and parsed

        // duplicate type
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_TYPE + FOOD + WHITE_SPACE + PREFIX_TYPE
                        + SPORTS_ACRONYM + WHITE_SPACE + PREFIX_KEYWORD + SWIM,
                new CheckCommand(new Type(SPORTS), SWIM));

        // duplicate keywords
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_TYPE + FOOD_ACRONYM + WHITE_SPACE
                        + PREFIX_KEYWORD + BANANA + WHITE_SPACE + PREFIX_KEYWORD + APPLE,
                new CheckCommand(new Type(FOOD), APPLE));

        // duplicate in both fields
        assertParseSuccess(parser, WHITE_SPACE + PREFIX_KEYWORD + BANANA + WHITE_SPACE
                        + PREFIX_TYPE + FOOD + WHITE_SPACE + PREFIX_TYPE + SPORTS_ACRONYM + WHITE_SPACE
                        + PREFIX_KEYWORD + APPLE + WHITE_SPACE + PREFIX_KEYWORD + SWIM,
                new CheckCommand(new Type(SPORTS), SWIM));
    }
}
