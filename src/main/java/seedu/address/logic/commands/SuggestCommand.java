package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Suggests a person/activity/place.
 */
public class SuggestCommand extends Command {
    public static final String COMMAND_WORD = "suggest";

    public static final String KEYWORD_PERSON = "person";
    public static final String KEYWORD_PLACE = "place";
    public static final String KEYWORD_ACTIVITY = "activity";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Suggests a person based on time spent/ "
                    + "place or activity based on frequency \n"
                    + "Parameters: "
                    + KEYWORD_PERSON
                    + "/"
                    + KEYWORD_ACTIVITY
                    + "/"
                    + KEYWORD_PLACE
                    + "\nExample: "
                    + COMMAND_WORD
                    + " person";

    public static final String MESSAGE_PERSON_SUGGEST_SUCCESS = "Person suggestion provided.";
    public static final String MESSAGE_ACTIVITY_SUGGEST_SUCCESS = "Activity suggestion provided.";
    public static final String MESSAGE_PLACE_SUGGEST_SUCCESS = "Place suggestion provided.";
    public static final String MESSAGE_INVALID_PARAMETER = "The entered parameter is invalid.\n";


    private final String suggestParameter;

    public SuggestCommand(String suggestParameter) {
        requireNonNull(suggestParameter);
        this.suggestParameter = suggestParameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (suggestParameter) {

        case KEYWORD_PERSON:
            model.suggestPerson();
            return new CommandResult(String.format(MESSAGE_PERSON_SUGGEST_SUCCESS, suggestParameter));

        case KEYWORD_ACTIVITY:
            model.suggestActivity();
            return new CommandResult(String.format(MESSAGE_ACTIVITY_SUGGEST_SUCCESS, suggestParameter),
                    ViewType.SUGGEST_ACTIVITY);

        case KEYWORD_PLACE:
            model.suggestPlace();
            return new CommandResult(String.format(MESSAGE_PLACE_SUGGEST_SUCCESS, suggestParameter),
                    ViewType.SUGGEST_PLACE);

        default:
            throw new CommandException(MESSAGE_INVALID_PARAMETER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestCommand // instanceof handles nulls
                && suggestParameter.equals(((SuggestCommand) other).suggestParameter));
    }

}
