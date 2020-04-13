package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.UNREACHABLE_STATEMENT_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;

import java.util.Arrays;

import seedu.address.logic.commands.find.FindCommand;
import seedu.address.logic.commands.find.FindInternshipCommand;
import seedu.address.logic.commands.find.FindNoteCommand;
import seedu.address.logic.commands.find.FindProjectCommand;
import seedu.address.logic.commands.find.FindResumeCommand;
import seedu.address.logic.commands.find.FindSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.model.util.ItemUtil;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM);

        if (argMultimap.getPreamble().isEmpty() && !argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        String trimmedPreamble = argMultimap.getPreamble().trim();
        if (trimmedPreamble.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            throw new ParseException(Item.MESSAGE_CONSTRAINTS);
        }

        String[] nameKeywords = trimmedPreamble.split("\\s+");

        String itemType = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_ITEM).get());

        switch (itemType) {
        case ItemUtil.RESUME_ALIAS:
            return new FindResumeCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        case ItemUtil.INTERNSHIP_ALIAS:
            return new FindInternshipCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        case ItemUtil.PROJECT_ALIAS:
            return new FindProjectCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        case ItemUtil.SKILL_ALIAS:
            return new FindSkillCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        case ItemUtil.NOTE_ALIAS:
            return new FindNoteCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        default:
            throw new AssertionError(UNREACHABLE_STATEMENT_REACHED);
        }
    }

}
