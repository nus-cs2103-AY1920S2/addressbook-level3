package com.notably.logic.parser;

import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.block.Path;
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements CommandParser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        return new DeleteCommand(new Path(args));
    }

}
