package com.notably.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.notably.logic.commands.Command;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.commands.EditCommand;
import com.notably.logic.commands.ExitCommand;
import com.notably.logic.commands.HelpCommand;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * Parse in users input and generate the respective Commands
 */
public class NotablyParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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
            return new NewCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            List<Command> helpCommand = new ArrayList<>();
            helpCommand.add(new HelpCommand());
            return helpCommand;

        case ExitCommand.COMMAND_WORD:
            List<Command> exitCommand = new ArrayList<>();
            exitCommand.add(new ExitCommand());
            return exitCommand;

        default:
            throw new ParseException("Invalid Command");
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

