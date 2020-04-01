package com.notably.logic.parser;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.logic.commands.Command;
import com.notably.logic.commands.DeleteCommand;
import com.notably.logic.commands.EditCommand;
import com.notably.logic.commands.ExitCommand;
import com.notably.logic.commands.HelpCommand;
import com.notably.logic.commands.NewCommand;
import com.notably.logic.commands.OpenCommand;
import com.notably.logic.correction.StringCorrectionEngine;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;

/**
 * Parse in users input and generate the respective Commands
 */
public class NotablyParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final List<String> COMMAND_LIST = List.of("new", "edit", "delete",
            "open", "help", "exit");
    private static final int DISTANCE_THRESHOLD = 2;

    private Model notablyModel;
    private StringCorrectionEngine correctionEngine;

    public NotablyParser(Model notablyModel) {
        this.notablyModel = notablyModel;
        this.correctionEngine = new StringCorrectionEngine(COMMAND_LIST, DISTANCE_THRESHOLD);
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
        String commandWord = matcher.group("commandWord");
        if (commandWord.length() > 1) {
            Optional<String> correctedCommand = correctionEngine.correct(commandWord).getCorrectedItem();
            if (correctedCommand.equals(Optional.empty())) {
                throw new ParseException("Invalid command");
            }
            commandWord = correctedCommand.get();
        }

        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case NewCommand.COMMAND_WORD:
        case NewCommand.COMMAND_SHORTHAND :
            return new NewCommandParser(notablyModel).parse(arguments);

        case OpenCommand.COMMAND_WORD:
        case OpenCommand.COMMAND_SHORTHAND :
            return new OpenCommandParser(notablyModel).parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_SHORTHAND:
            return new DeleteCommandParser(notablyModel).parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_SHORTHAND:
            return new EditCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_SHORTHAND:
            return List.of(new HelpCommand());

        case ExitCommand.COMMAND_WORD:
            return List.of(new ExitCommand());

        default:
            throw new ParseException("Invalid Command Word");
        }
    }

}

