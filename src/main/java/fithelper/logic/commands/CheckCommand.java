package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.Set;

import fithelper.model.Model;
import fithelper.model.calorietable.CalorieEntry;
import fithelper.model.entry.Type;

/**
 * Search the pre-defined data for calorie intake/consumption of a food/sports.
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": search for calorie intake/consumption "
            + "of a food/sports from pre-defined dataset.\n"
            + "Parameters: " + PREFIX_TYPE + "TYPE" + PREFIX_KEYWORD + "KEYWORD";

    private static final String MESSAGE_SUCCESS = "Matching reference data:\n";
    private static final String MESSAGE_FAILURE_PART1 = "Sorry, no matching reference data in the ";
    private static final String MESSAGE_FAILURE_PART2 = " category for the given keyword: ";
    private static final String MESSAGE_HINT = "Note: at most 3 records are shown.\n";

    private Type checkType;
    private String keywords;

    public CheckCommand(Type checkType, String keywords) {
        this.checkType = checkType;
        this.keywords = keywords;
    }

    public Type getCheckType() {
        return checkType;
    }

    public String getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof CheckCommand) {
            CheckCommand other = (CheckCommand) obj;
            return keywords.equals(other.getKeywords()) && checkType.equals(other.getCheckType());
        } else {
            return false;
        }

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String typeValue = checkType.getValue();
        Set<CalorieEntry> result;
        if ("food".equals(typeValue)) {
            result = model.searchFoodCalorieTable(keywords);
        } else {
            result = model.searchSportsCalorieTable(keywords);
        }
        if (result.size() > 0) {
            StringBuilder userFeedback = new StringBuilder(MESSAGE_SUCCESS);
            for (CalorieEntry entry : result) {
                userFeedback.append(entry);
            }
            userFeedback.append(MESSAGE_HINT);
            return new CommandResult(userFeedback.toString());
        } else {
            return new CommandResult(MESSAGE_FAILURE_PART1 + checkType
                    + MESSAGE_FAILURE_PART2 + keywords + "\n");
        }
    }
}
