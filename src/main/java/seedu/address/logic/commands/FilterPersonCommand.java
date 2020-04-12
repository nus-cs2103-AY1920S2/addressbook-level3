package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.PersonIDConditions;
import seedu.address.logic.messages.UserSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterPersonCommand implements AppCommand, UserStorageAccess {
    private static int PERSON_ID;

    public static final String COMMAND_WORD = "person_by";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<personId>\\d+)");
    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public FilterPersonCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        this.PERSON_ID = Integer.parseInt(matcher.group("personId"));
        return this;
    }

    @Override
    public UserSummaryMessage execute(AppStorage dao) {
        Conditions cond = new PersonIDConditions(this.PERSON_ID);
        ArrayList resp  = dao.search(cond);
        UserSummaryMessage result = new UserSummaryMessage("Identifying by user id", false);
        result.setToDisplayList(resp);
        return result;
    }
}
