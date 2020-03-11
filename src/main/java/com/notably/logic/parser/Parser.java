package com.notably.logic.parser;

import com.notably.logic.commands.Command;
import com.notably.logic.parser.exceptions.ParseException;

public interface Parser<T extends Command> {

    T parse(String userInput) throws ParseException;
}
