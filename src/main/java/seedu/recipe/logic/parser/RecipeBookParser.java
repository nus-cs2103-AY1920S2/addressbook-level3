package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.commands.AddIngredientCommand;
import seedu.recipe.logic.commands.AddStepCommand;
import seedu.recipe.logic.commands.ClearCommand;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CookedCommand;
import seedu.recipe.logic.commands.DeleteCommand;
import seedu.recipe.logic.commands.DeleteIngredientCommand;
import seedu.recipe.logic.commands.DeleteStepCommand;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.EditIngredientCommand;
import seedu.recipe.logic.commands.EditStepCommand;
import seedu.recipe.logic.commands.ExitCommand;
import seedu.recipe.logic.commands.FavouriteCommand;
import seedu.recipe.logic.commands.FilterCommand;
import seedu.recipe.logic.commands.FindCommand;
import seedu.recipe.logic.commands.HelpCommand;
import seedu.recipe.logic.commands.ListCommand;
import seedu.recipe.logic.commands.ListGoalsCommand;
import seedu.recipe.logic.commands.RedoCommand;
import seedu.recipe.logic.commands.SwitchCommand;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.logic.commands.UnfavouriteCommand;
import seedu.recipe.logic.commands.plan.ClearPlanCommand;
import seedu.recipe.logic.commands.plan.DeletePlanCommand;
import seedu.recipe.logic.commands.plan.PlanCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.logic.parser.plan.DeletePlanCommandParser;
import seedu.recipe.logic.parser.plan.PlanCommandParser;

/**
 * Parses user input.
 */
public class RecipeBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListGoalsCommand.COMMAND_WORD:
            return new ListGoalsCommand();

        case CookedCommand.COMMAND_WORD:
            return new CookedCommandParser().parse(arguments);

        case FavouriteCommand.COMMAND_WORD:
            return new FavouriteCommandParser().parse(arguments);

        case UnfavouriteCommand.COMMAND_WORD:
            return new UnfavouriteCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case AddStepCommand.COMMAND_WORD:
            return new AddStepCommandParser().parse(arguments);

        case DeleteStepCommand.COMMAND_WORD:
            return new DeleteStepCommandParser().parse(arguments);

        case EditStepCommand.COMMAND_WORD:
            return new EditStepCommandParser().parse(arguments);

        case AddIngredientCommand.COMMAND_WORD:
            return new AddIngredientCommandParser().parse(arguments);

        case DeleteIngredientCommand.COMMAND_WORD:
            return new DeleteIngredientCommandParser().parse(arguments);

        case EditIngredientCommand.COMMAND_WORD:
            return new EditIngredientCommandParser().parse(arguments);

        case PlanCommand.COMMAND_WORD:
            return new PlanCommandParser().parse(arguments);

        case DeletePlanCommand.COMMAND_WORD:
            return new DeletePlanCommandParser().parse(arguments);

        case ClearPlanCommand.COMMAND_WORD:
            return new ClearPlanCommand();

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
