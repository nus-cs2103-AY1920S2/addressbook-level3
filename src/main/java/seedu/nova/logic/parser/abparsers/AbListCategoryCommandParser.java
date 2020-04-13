package seedu.nova.logic.parser.abparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.Arrays;
import java.util.Set;

import seedu.nova.logic.commands.abcommands.AbListCategoryCommand;
import seedu.nova.logic.commands.abcommands.AbListClassmateCommand;
import seedu.nova.logic.commands.abcommands.AbListTeammateCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.category.Category;
import seedu.nova.model.person.CategoryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new AbFindCommand object
 */
public class AbListCategoryCommandParser implements Parser<AbListCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AbFindCommand
     * and returns a AbFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbListCategoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);
        Set<Category> categoryList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CATEGORY));

        if (categoryList.size() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AbListCategoryCommand.MESSAGE_USAGE));
        } else if (categoryList.size() > 1) {
            throw new ParseException("Please only provide 1 category.");
        }

        if (categoryList.iterator().next().toString().equals("classmate")) {
            String[] classmateList = {"classmate"};
            return new AbListClassmateCommand(new CategoryContainsKeywordsPredicate(Arrays.asList(classmateList)));
        } else {
            String[] teammateList = {"teammate"};
            return new AbListTeammateCommand(new CategoryContainsKeywordsPredicate(Arrays.asList(teammateList)));
        }
    }

}
