package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.LiterallyNoConditions;
import seedu.address.logic.messages.UserSummaryMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bluetooth.Person;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonAddCommand implements AppCommand, UserStorageAccess {
    public static final String COMMAND_WORD = "person_add";

    private static final Pattern COMMAND_FORMAT = Pattern.compile(  "/name (?<name>\\w.*) " +
                                                                    "/mobile (?<mobile>\\S+) " +
                                                                    "/nric (?<nric>\\S+) " +
                                                                    "/age (?<age>\\d+)");

    private Person person;
    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public AppCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        String name = matcher.group("name");
        String mobile = matcher.group("mobile");
        String nric = matcher.group("nric");
        int age = Integer.parseInt(matcher.group("age"));
        this.person = new Person(name).withNric(nric).withMobile(mobile).withAge(age);
        return this;
    }

    @Override
    public UserSummaryMessage execute(AppStorage dao) {
        String message;
        try {
            dao.create(this.person);
            message = "User addition successful";
        }
        catch (Exception ex) {
            message = "User addition failed with " + ex.getStackTrace();
        }

        Conditions cond = new LiterallyNoConditions();
        ArrayList resp  = dao.search(cond);
        UserSummaryMessage result = new UserSummaryMessage(message, false);
        result.setToDisplayList(resp);
        return result;
    }
}
