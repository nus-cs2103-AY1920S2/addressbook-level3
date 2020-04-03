package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.DangerConditions;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterDangerCommand implements AppCommand {
    private int THRESHOLD;

    public static final String COMMAND_WORD = "danger";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<threshold>\\d+)");
    private final Logger logger = LogsCenter.getLogger(FilterDangerCommand.class);

    @Override
    public AppCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        this.THRESHOLD = Integer.parseInt(matcher.group("threshold"));
        return this;
    }

    @Override
    public AppCommandResult execute(AppStorage dao) {
        Conditions cond = new DangerConditions(this.THRESHOLD);
        ArrayList resp  = dao.search(cond);
        AppCommandResult result = new AppCommandResult("Identified dangerous users", false);
        result.setToDisplayList(resp);
        return result;
    }

}
