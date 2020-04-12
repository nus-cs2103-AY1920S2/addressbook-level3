package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.TimestampConditions;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.storage.BluetoothPingStorageAccess;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterTimestampCommand implements AppCommand, BluetoothPingStorageAccess {
    private static Long START_TIME;
    private static Long END_TIME;

    public static final String COMMAND_WORD = "from";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<start>\\d+) to (?<end>\\d+)");
    private final Logger logger = LogsCenter.getLogger(FilterTimestampCommand.class);

    @Override
    public FilterTimestampCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        this.START_TIME = Long.parseLong(matcher.group("start"));
        this.END_TIME   = Long.parseLong(matcher.group("end"));
        return this;
    }

    @Override
    public BluetoothPingsMessage execute(AppStorage dao) {
        Conditions cond = new TimestampConditions(this.START_TIME, this.END_TIME);
        ArrayList resp  = dao.search(cond);
        BluetoothPingsMessage result = new BluetoothPingsMessage("Filtered by timestamp", false);
        result.setToDisplayList(resp);
        return result;
    }
}
