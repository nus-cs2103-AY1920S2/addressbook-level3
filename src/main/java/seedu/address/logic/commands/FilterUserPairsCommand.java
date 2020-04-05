package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.UserPairsConditions;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUserPairsCommand implements AppCommand {
    private int USER_A;
    private int USER_B;
    public static final String COMMAND_WORD = "pairs";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<userA>\\d+) and (?<userB>\\d+)");
    private final Logger logger = LogsCenter.getLogger(FilterUserPairsCommand.class);

    @Override
    public FilterUserPairsCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        this.USER_A = Integer.parseInt(matcher.group("userA"));
        this.USER_B = Integer.parseInt(matcher.group("userB"));
        return this;
    }

    @Override
    public BluetoothPingsMessage execute(AppStorage dao) {
        Conditions cond = new UserPairsConditions(USER_A, USER_B);
        ArrayList resp  = dao.search(cond);
        BluetoothPingsMessage result = new BluetoothPingsMessage("Identifying recods with User Pairs.", false);
        result.setToDisplayList(resp);
        return result;
    }
}
