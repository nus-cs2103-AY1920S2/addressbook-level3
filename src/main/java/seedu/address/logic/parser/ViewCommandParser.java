package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.stubs.ViewInternshipCommandStub;
import seedu.address.logic.commands.stubs.ViewResumeCommandStub;
import seedu.address.logic.commands.view.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Item;

public class ViewCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        // The code is actually identical to DeleteCommand
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM);

            if (!argMultimap.getValue(PREFIX_ITEM).isPresent()) {
                throw new ParseException(Item.MESSAGE_CONSTRAINTS);
            }

            String preamble = argMultimap.getPreamble();
            Index index = ParserUtil.parseIndex(preamble);

            String itemType = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_ITEM).get());

            switch(itemType) {
            case("res"):
                return new ViewResumeCommandStub(index);
            case("int"):
                return new ViewInternshipCommandStub(index);
            default:
                // Should not have reached here
                // TODO: Use a better Exception here
                throw new ParseException("The item type is not detected! Something is wrong");
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
