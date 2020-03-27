package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_LIST;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_LIST;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        String[] trimmedArgs = args.trim().split("\\s");
        ArrayList<String> splitInputList = new ArrayList<>(Arrays.asList(trimmedArgs));
        for (int i = 0; i < splitInputList.size(); i++) {
            System.out.println(splitInputList.get(i));
        }
        DoneCommand.DoneOrderDescriptor doneOrderDescriptor = new DoneCommand.DoneOrderDescriptor();
        if (splitInputList.size() > 2) {
            // Unnecessary input
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        if (splitInputList.contains("-r")) {
            splitInputList.remove("-r");
            int indexAsInt = Integer.parseInt(splitInputList.get(0));
            Index index = Index.fromOneBased(indexAsInt);
            return new DoneCommand(index, FLAG_RETURN_LIST, doneOrderDescriptor);
        } else if (splitInputList.contains("-o")) {
            splitInputList.remove("-o");
            int indexAsInt = Integer.parseInt(splitInputList.get(0));
            Index index = Index.fromOneBased(indexAsInt);
            return new DoneCommand(index, FLAG_ORDER_LIST, doneOrderDescriptor);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
    }
}

