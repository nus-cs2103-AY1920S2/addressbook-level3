package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.LiterallyNoConditions;
import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.messages.UserSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListAllUsersCommand implements AppCommand, UserStorageAccess {
    public static final String COMMAND_WORD = "users";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("all");

    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public AppCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        return this;
    }

    @Override
    public AppMessage execute(AppStorage dao) {
        Conditions cond = new LiterallyNoConditions();
        ArrayList resp  = dao.search(cond);
        UserSummaryMessage result = new UserSummaryMessage("Identifying user records.", false);
        result.setToDisplayList(resp);
        return result;
    }
}
