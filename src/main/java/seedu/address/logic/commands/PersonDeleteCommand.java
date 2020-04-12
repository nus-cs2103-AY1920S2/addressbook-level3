package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.LiterallyNoConditions;
import seedu.address.logic.conditions.PersonIDConditions;
import seedu.address.logic.messages.UserSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.Person;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonDeleteCommand implements AppCommand, UserStorageAccess {
    public static final String COMMAND_WORD = "person_delete";

    private static final Pattern COMMAND_FORMAT = Pattern.compile("/userid (?<userid>\\d+)");

    private int USERID;
    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public AppCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        this.USERID = Integer.parseInt(matcher.group("userid"));
        return this;
    }

    @Override
    public UserSummaryMessage execute(AppStorage dao) {
        Conditions deleteCondition = new PersonIDConditions(this.USERID);
        ArrayList<Person> candidateToDelete = dao.search(deleteCondition);
        dao.delete(candidateToDelete);
        UserSummaryMessage result = new UserSummaryMessage("User deleted", false);

        Conditions cond = new LiterallyNoConditions();
        ArrayList resp  = dao.search(cond);
        result.setToDisplayList(resp);
        return result;
    }
}
