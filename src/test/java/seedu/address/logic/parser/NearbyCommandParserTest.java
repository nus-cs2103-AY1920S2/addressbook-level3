package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.logic.commands.NearbyCommand;

class NearbyCommandParserTest {
    private NearbyCommandParser parser = new NearbyCommandParser();

    /**
     * Used to generate invalid arguments for Nearby Command Parser.
     *
     * @return Stream of Arguments containing invalid input
     */
    private static Stream<Arguments> invalidArgs() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("    "),
                Arguments.of("central 1000"));
    }

    @Test
    void parse_validArgs_returnsNearbyCommand() {
        String flag = FLAG_ORDER_LIST.toString();
        assertParseSuccess(parser, " " + flag + " 1", new NearbyCommand(flag + " 1"));
        assertParseSuccess(parser, "  " + flag + "  1   ", new NearbyCommand(flag + " 1"));
        assertParseSuccess(parser, " " + flag + " central", new NearbyCommand(flag + " central"));
        assertParseSuccess(parser, " " + flag + " east", new NearbyCommand(flag + " east"));
        assertParseSuccess(parser, " " + flag + " north-east", new NearbyCommand(flag + " north-east"));
        assertParseSuccess(parser, " " + flag + " west", new NearbyCommand(flag + " west"));
        assertParseSuccess(parser, " " + flag + " north", new NearbyCommand(flag + " north"));
    }

    @ParameterizedTest
    @MethodSource("invalidArgs")
    void parse_invalidArgs_throwsParseException(String arg) {
        assertParseFailure(parser, arg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
    }
}
