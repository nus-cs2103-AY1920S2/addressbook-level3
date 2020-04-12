package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.LiterallyNoConditions;
import seedu.address.logic.messages.UserSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ListAllPersonCommand implements AppCommand, UserStorageAccess {
    public static final String COMMAND_WORD = "person";

    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public AppCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        return this;
    }

    @Override
    public UserSummaryMessage execute(AppStorage dao) {
        Conditions cond = new LiterallyNoConditions();
        ArrayList resp  = dao.search(cond);
        UserSummaryMessage result = new UserSummaryMessage("Identifying all user records.", false);
        result.setToDisplayList(resp);
        return result;
    }
}
