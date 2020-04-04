package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FLAG;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {
    private String newline = System.lineSeparator();
    private DeleteCommandParser parser = new DeleteCommandParser();

    /**
     * Generate a stream of invalid flag arguments for testing.
     *
     * @return Stream of arguments representing invalid flag arguments
     */
    private static Stream<Arguments> invalidFlagArgs() {
        return Stream.of(
                Arguments.of("a"),
                Arguments.of("-f 1"),
                Arguments.of("-flag_invalid "),
                Arguments.of("    no   valid arg ")
        );
    }

    /**
     * Generate a stream of invalid index arguments for testing.
     *
     * @return Stream of arguments representing invalid index arguments
     */
    private static Stream<Arguments> invalidIndexArgs() {
        return Stream.of(
                Arguments.of("1-r -r"),
                Arguments.of("-o 1-r"),
                Arguments.of("1-o -o"),
                Arguments.of("-o 1-o"),
                Arguments.of("a -o"),
                Arguments.of("-o a"),
                Arguments.of("a -r"),
                Arguments.of("-r a")
        );
    }

    @Test
    public void parse_validArgsOrderBook_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK);
        assertParseSuccess(parser, "-o 1", deleteCommand);
    }

    @Test
    public void parse_invalidIndexOrderBook_throwsParseException() {
        assertParseFailure(parser, "-o a",
                String.format(MESSAGE_INVALID_INDEX + newline + "%s", DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsReturnBook_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK);
        assertParseSuccess(parser, "-r 1", deleteCommand);
    }

    /**
     * Tests parse method with invalid index argument.
     *
     * @param args obtained from method {@code invalidIndexArgs}
     */
    @ParameterizedTest
    @MethodSource("invalidIndexArgs")
    public void parse_invalidIndexReturnBook_throwsParseException(String args) {
        assertParseFailure(parser, args,
                String.format(MESSAGE_INVALID_INDEX + newline + "%s", DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Tests parse method with invalid flag argument.
     *
     * @param args obtained from method {@code invalidFlagArgs}
     */
    @ParameterizedTest
    @MethodSource("invalidFlagArgs")
    public void parse_missingFlag_throwsParseException(String args) {
        assertParseFailure(parser, args,
                String.format(MESSAGE_MISSING_FLAG + newline + "%s", DeleteCommand.MESSAGE_USAGE));
    }
}
