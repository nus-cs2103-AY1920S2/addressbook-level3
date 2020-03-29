package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.SORT_TYPE;
import static tatracker.logic.parser.Prefixes.TYPE;

import java.util.List;

import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.Prefixes;
import tatracker.model.Model;

/**
 * Command to sort all students in all groups of all modules.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.SORT;

    public static final List<Prefix> PARAMETERS = List.of(SORT_TYPE);

    public static final String INFO = "Sorts all students in TA-Tracker with in-built rules.";
    public static final String USAGE = Prefixes.getUsages(PARAMETERS);
    public static final String EXAMPLE = Prefixes.getExamples(SORT_TYPE);

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students in"
            + "all groups of all modules in the TA-Tracker. "
            + "Parameters: "
            + TYPE + "SORT TYPE "
            + "Example: " + COMMAND_WORD + " "
            + TYPE + "alphabetically"
            + "\nOther variations include group code and module code.";

    public static final String MESSAGE_SUCCESS = "The modules have been sorted.";
    private static final int FIRST_MODULE_INDEX = 0;
    private static final int FIRST_GROUP_INDEX = 0;

    private final String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (type.equalsIgnoreCase("alphabetically")
                || type.equalsIgnoreCase("alpha")) {
            model.sortModulesAlphabetically();
        } else if (type.equalsIgnoreCase("rating asc")) {
            model.sortModulesByRatingAscending();
        } else {
            model.sortModulesByRatingDescending();
        }

        if (model.getFilteredModuleList().isEmpty()) {
            model.setFilteredGroupList();
            model.setFilteredStudentList();
        } else {
            model.updateGroupList(FIRST_MODULE_INDEX);
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
            } else {
                model.updateStudentList(FIRST_GROUP_INDEX, FIRST_MODULE_INDEX);
            }
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
