package com.notably.logic.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.logic.commands.Command;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.commands.EditCommand;
import com.notably.logic.commands.ExitCommand;
import com.notably.logic.commands.HelpCommand;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Parse in users input and generate the respective Commands
 */
public class NotablyParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private Model notablyModel;

    public NotablyParser(Model notablyModel) {
        this.notablyModel = notablyModel;
    }
    /**
     * Create list of different Commands base on user input.
     * @param userInput supplied by the user.
     * @return list of commands.
     * @throws ParseException when there is a invalid input string.
     */
    public List<? extends Command> parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format("Invalid Command"));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case NewCommand.COMMAND_WORD:
            return new NewCommandParser(notablyModel).parse(arguments);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser(notablyModel).parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser(notablyModel).parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return List.of(new HelpCommand());

        case ExitCommand.COMMAND_WORD:
            return List.of(new ExitCommand());

        default:
            throw new ParseException("Invalid Command");
        }
    }

}

