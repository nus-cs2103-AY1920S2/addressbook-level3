package fithelper.logic.parser.diary;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DIARY_CONTENT;

import java.util.stream.Stream;

import fithelper.logic.diary.AppendDiaryCommand;
import fithelper.logic.parser.ArgumentMultimap;
import fithelper.logic.parser.ArgumentTokenizer;
import fithelper.logic.parser.Parser;
import fithelper.logic.parser.ParserUtil;
import fithelper.logic.parser.Prefix;
import fithelper.logic.parser.exceptions.ParseException;

import fithelper.model.diary.Content;
import fithelper.model.diary.DiaryDate;

/**
 * Parses input arguments and creates a new AppendDiaryCommand object
 */
public class AppendDiaryCommandParser implements Parser<AppendDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppendDiaryCommand
     * and returns an AppendDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppendDiaryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_DIARY_CONTENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_DIARY_CONTENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppendDiaryCommand.MESSAGE_USAGE));
        }

        DiaryDate diaryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Content content = ParserUtil.parseContent(argMultimap.getValue(PREFIX_DIARY_CONTENT).get());
        AppendDiaryCommand.AppendDiaryDescriptor appendDiaryDescriptor = new AppendDiaryCommand.AppendDiaryDescriptor();
        appendDiaryDescriptor.setDiaryDate(diaryDate);
        appendDiaryDescriptor.setContent(content);

        return new AppendDiaryCommand(diaryDate.toString(), appendDiaryDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

