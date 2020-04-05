package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.storage.UserStorageAccess;

import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ListUsersCommand implements AppCommand, UserStorageAccess {
    public static final String COMMAND_WORD = "users";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<start>\\d+) to (?<end>\\d+)");
    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public AppCommand validate(String arguments) throws ParseException {
        return null;
    }

    @Override
    public AppMessage execute(AppStorage dao) {
        return null;
    }
}
