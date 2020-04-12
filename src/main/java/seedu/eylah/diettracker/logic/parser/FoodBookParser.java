package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.parser.CommonParser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.AddCommand;
import seedu.eylah.diettracker.logic.commands.BackCommand;
import seedu.eylah.diettracker.logic.commands.BmiCommand;
import seedu.eylah.diettracker.logic.commands.DeleteCommand;
import seedu.eylah.diettracker.logic.commands.EditCommand;
import seedu.eylah.diettracker.logic.commands.ExitCommand;
import seedu.eylah.diettracker.logic.commands.HeightCommand;
import seedu.eylah.diettracker.logic.commands.HelpCommand;
import seedu.eylah.diettracker.logic.commands.ListCommand;
import seedu.eylah.diettracker.logic.commands.MetricsCommand;
import seedu.eylah.diettracker.logic.commands.ModeCommand;
import seedu.eylah.diettracker.logic.commands.WeightCommand;
import seedu.eylah.diettracker.model.DietModel;

/**
 * Parses user input.
 */
public class FoodBookParser implements CommonParser<DietModel> {

    @Override
    public Command<DietModel> parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(); // No Args so no need to Parse.
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case HeightCommand.COMMAND_WORD:
            return new HeightCommandParser().parse(arguments);
        case WeightCommand.COMMAND_WORD:
            return new WeightCommandParser().parse(arguments);
        case BmiCommand.COMMAND_WORD:
            return new BmiCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);
        case ModeCommand.COMMAND_WORD:
            return new ModeCommandParser().parse(arguments);
        case BackCommand.COMMAND_WORD:
            return new BackCommand(); // No Args so no need to Parse.
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand(); // No Args so no need to Parse.
        case MetricsCommand.COMMAND_WORD:
            return new MetricsCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
