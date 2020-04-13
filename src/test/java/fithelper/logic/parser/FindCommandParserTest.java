package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseFailure;
import static fithelper.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import fithelper.logic.commands.FindCommand;
import fithelper.model.entry.NameContainsKeywordsPredicate;
import fithelper.model.entry.Type;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> keywords = Arrays.asList("Noodles", "Mala");
        FindCommand expectedFindCommand =
                new FindCommand(new Type("food"), new NameContainsKeywordsPredicate(keywords));
        assertParseSuccess(parser,
                FindCommand.COMMAND_WORD + " " + PREFIX_KEYWORD + keywords.stream()
                .collect(Collectors.joining(" ")), expectedFindCommand);
    }
}

