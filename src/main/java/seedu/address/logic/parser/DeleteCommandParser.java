package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.UNREACHABLE_STATEMENT_REACHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delete.DeleteCommand;
import seedu.address.logic.commands.delete.DeleteInternshipCommand;
import seedu.address.logic.commands.delete.DeleteNoteCommand;
import seedu.address.logic.commands.delete.DeleteProjectCommand;
import seedu.address.logic.commands.delete.DeleteResumeCommand;
import seedu.address.logic.commands.delete.DeleteSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;
import seedu.address.model.util.ItemUtil;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM);

        if (argMultimap.getPreamble().isEmpty() && !argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        if (!argMultimap.getValue(PREFIX_ITEM).isPresent()) {
            throw new ParseException(Item.MESSAGE_CONSTRAINTS);
        }

        String itemType = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_ITEM).get());

        switch (itemType) {
        case ItemUtil.RESUME_ALIAS:
            return new DeleteResumeCommand(index);
        case ItemUtil.INTERNSHIP_ALIAS:
            return new DeleteInternshipCommand(index);
        case ItemUtil.PROJECT_ALIAS:
            return new DeleteProjectCommand(index);
        case ItemUtil.SKILL_ALIAS:
            return new DeleteSkillCommand(index);
        case ItemUtil.NOTE_ALIAS:
            return new DeleteNoteCommand(index);
        default:
            throw new AssertionError(UNREACHABLE_STATEMENT_REACHED);
        }
    }

}
