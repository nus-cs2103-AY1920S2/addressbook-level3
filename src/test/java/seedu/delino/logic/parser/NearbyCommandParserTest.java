package seedu.delino.logic.parser;

import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.delino.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.delino.logic.commands.NearbyCommand;

//@@author JeremyLoh
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
                Arguments.of("    "));
    }

    @Test
    void parse_validArgs_returnsNearbyCommand() {
        String orderListFlag = FLAG_ORDER_BOOK.toString();
        assertParseSuccess(parser, " " + orderListFlag + " 1",
                new NearbyCommand(orderListFlag + " 1"));
        assertParseSuccess(parser, "  " + orderListFlag + "  1   ",
                new NearbyCommand(orderListFlag + " 1"));
        assertParseSuccess(parser, " " + orderListFlag + " central",
                new NearbyCommand(orderListFlag + " central"));
        assertParseSuccess(parser, " " + orderListFlag + " east",
                new NearbyCommand(orderListFlag + " east"));
        assertParseSuccess(parser, " " + orderListFlag + " north-east",
                new NearbyCommand(orderListFlag + " north-east"));
        assertParseSuccess(parser, " " + orderListFlag + " west",
                new NearbyCommand(orderListFlag + " west"));
        assertParseSuccess(parser, " " + orderListFlag + " north",
                new NearbyCommand(orderListFlag + " north"));

        String returnListFlag = FLAG_RETURN_BOOK.toString();
        assertParseSuccess(parser, " " + returnListFlag + " 1",
                new NearbyCommand(returnListFlag + " 1"));
        assertParseSuccess(parser, " " + returnListFlag + " central",
                new NearbyCommand(returnListFlag + " central"));
        assertParseSuccess(parser, " " + returnListFlag + " east",
                new NearbyCommand(returnListFlag + " east"));
        assertParseSuccess(parser, " " + returnListFlag + " north-east",
                new NearbyCommand(returnListFlag + " north-east"));
        assertParseSuccess(parser, " " + returnListFlag + " west",
                new NearbyCommand(returnListFlag + " west"));
        assertParseSuccess(parser, " " + returnListFlag + " north",
                new NearbyCommand(returnListFlag + " north"));
    }

    @ParameterizedTest
    @MethodSource("invalidArgs")
    void parse_invalidArgs_throwsParseException(String arg) {
        assertParseFailure(parser, arg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        NearbyCommand.MESSAGE_USAGE));
    }
}
