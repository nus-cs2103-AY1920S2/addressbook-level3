package fithelper.logic.parser.diary;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.stream.Stream;

import fithelper.logic.commands.FindCommand;
import fithelper.logic.diary.FindDiaryCommand;
import fithelper.logic.parser.ArgumentMultimap;
import fithelper.logic.parser.ArgumentTokenizer;
import fithelper.logic.parser.Parser;
import fithelper.logic.parser.ParserUtil;
import fithelper.logic.parser.Prefix;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.diary.DiaryContentContainsKeywordsPredicate;
import fithelper.model.diary.DiaryDate;
import fithelper.model.diary.DiaryDatePredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindDiaryCommandParser implements Parser<FindDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDiaryCommand
     * and returns a FindDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDiaryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_KEYWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_KEYWORD)) {
             return new FindDiaryCommand(null, null);
        }
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            DiaryDate diaryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            return new FindDiaryCommand(null, new DiaryDatePredicate(diaryDate));
        }
        //if the name is not specified, the keyword cannot be empty
        String trimmedArgs = argMultimap.getValue(PREFIX_KEYWORD).get().trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDiaryCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDiaryCommand(new DiaryContentContainsKeywordsPredicate(Arrays.asList(nameKeywords)), null);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


