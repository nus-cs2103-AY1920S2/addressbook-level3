package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
        assertParseSuccess(parser, "1", new NearbyCommand("1"));
        assertParseSuccess(parser, "   1   ", new NearbyCommand("1"));
        assertParseSuccess(parser, "central", new NearbyCommand("central"));
        assertParseSuccess(parser, "east", new NearbyCommand("east"));
        assertParseSuccess(parser, "north-east", new NearbyCommand("north-east"));
        assertParseSuccess(parser, "west", new NearbyCommand("west"));
        assertParseSuccess(parser, "north", new NearbyCommand("north"));
    }

    @ParameterizedTest
    @MethodSource("invalidArgs")
    void parse_invalidArgs_throwsParseException(String arg) {
        assertParseFailure(parser, arg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
    }
}
