package seedu.eylah.diettracker.logic.commands;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.diettracker.model.DietModel;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows Diet Tracker usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String DIVIDER = "--------------------------------\n";

    public static final String SHOWING_HELP_MESSAGE = "Welcome to Diet Tracker! The following commands are "
            + "available:\n"
            + DIVIDER
            + "1. add - Use this to add a food item to your Diet Tracker.\n"
            + "   USAGE: add -n NAME -c CALORIES\n"
            + "   EXAMPLE: add -n Mushroom Aglio -c 360\n"
            + DIVIDER
            + "2. delete - Use this to delete a food item at the specified index in your list.\n"
            + "   USAGE: delete INDEX\n "
            + "   EXAMPLE: delete 1\n"
            + DIVIDER
            + "3. edit - Use this to edit a food item at the specified index in your list.\n"
            + "   USAGE: edit -i INDEX [-n NAME] [-c CALORIES]\n"
            + "   EXAMPLE: edit -i 1 -n Prawn Aglio -c 520\n"
            + DIVIDER
            + "4. list - Use this to list the food for a given period of time or food of a certain tag.\n"
            + "   USAGE: list [-a] [-d NUMDAYS] [-t TAG]\n"
            + "   EXAMPLE: list -a\n"
            + DIVIDER
            + "5. mode - Use this to change the mode (GAIN, MAINTAIN, LOSS) of the Diet Tracker.\n"
            + "   USAGE: mode [-g] [-l] [-m]\n"
            + "   EXAMPLE: mode -g\n"
            + DIVIDER
            + "6. height - Use this to add your personal height to the Diet Tracker.\n"
            + "   USAGE: height HEIGHT\n"
            + "   EXAMPLE: height 170.2\n"
            + DIVIDER
            + "7. weight - Use this to add your personal weight to the Diet Tracker.\n"
            + "   USAGE: weight WEIGHT\n"
            + "   EXAMPLE: weight 65.7\n"
            + DIVIDER
            + "8. bmi - Use this to calculate your bmi.\n"
            + "   USAGE: bmi [-h HEIGHT] [-w WEIGHT]\n"
            + "   EXAMPLE: bmi -h 170.2 -w 65.7\n"
            + DIVIDER
            + "9. back - Use this to go back to EYLAH main application window in order to switch between Diet Tracker"
            + " and Expense Splitter.\n"
            + "   USAGE: back\n"
            + "   EXAMPLE: back\n"
            + "We hope you enjoy your usage of EYLAH Diet Tracker!";

    @Override
    public CommandResult execute(DietModel model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
    }
}
