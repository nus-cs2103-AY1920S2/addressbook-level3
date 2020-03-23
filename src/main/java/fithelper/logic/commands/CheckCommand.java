package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.exceptions.CommandException;
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
    private static final String MESSAGE_FAILURE = "Sorry, no reference data have been found for your keyword.\n";


    private Type checkType;
    private String keyword;

    public CheckCommand(Type checkType, String keyword) {
        this.checkType = checkType;
        this.keyword = keyword;
    }

    public Type getCheckType() {
        return checkType;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof CheckCommand) {
            CheckCommand other = (CheckCommand) obj;
            return keyword.equals(other.getKeyword()) && checkType.equals(other.getCheckType());
        } else {
            return false;
        }

    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);
        String typeValue = checkType.getValue();
        List<? extends CalorieEntry> result;
        if ("food".equals(typeValue)) {
            result = model.searchFoodCalorieTable(keyword);
        } else {
            result = model.searchSportsCalorieTable(keyword);
        }
        if (result.size() > 0) {
            String userFeedback = MESSAGE_SUCCESS;
            for (CalorieEntry entry : result) {
                userFeedback += entry;
            }
            return new CommandResult(userFeedback);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
