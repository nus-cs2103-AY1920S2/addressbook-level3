package com.notably.logic.parser;

import java.util.List;

import com.notably.logic.commands.Command;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Command Parser interface.
 * @param <T> returns a Command.
 */
public interface CommandParser<T extends Command> {
    List<T> parse(String userInput) throws ParseException;
}
