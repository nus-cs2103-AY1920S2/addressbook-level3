package com.notably.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.logic.commands.Command;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.parser.exceptions.ParseException;

public class NotablyParser<T extends Command> {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

//    public boolean checkArgument(String string) {
//        String[] temp = string.split("/");
//        for(String object: temp) {
//        }`
//    }

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
//        CorrectionEngineStub correctionEngineStub = new CorrectionEngineStub();
        if (!matcher.matches()) {
            throw new ParseException(String.format("Invalid Command"));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
            case NewCommand.COMMAND_WORD:
                return new NewCommandParser().parse(arguments);

            case OpenCommand.COMMAND_WORD:
                return new OpenCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            default:
                throw new ParseException("Invalid Command");
        }
    }
}