package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Scanner;

import javafx.util.Pair;
import seedu.address.logic.commands.task.TaskDeleteCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskNumManager;



/**
 * Parses input arguments and creates a new TaskDeleteCommand object
 */
public class TaskDeleteCommandParser implements Parser<TaskDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TaskDeleteCommand
     * and returns a TaskDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDeleteCommand.MESSAGE_USAGE));
        }
        String toUse = argMultimap.getPreamble();

        Pair<ModuleCode, Integer> pair = parseTaskNumString(toUse);
        ModuleCode moduleCode = pair.getKey();
        int taskNum = pair.getValue();

        return new TaskDeleteCommand(moduleCode, taskNum);
    }

    /**
     * Takes the String and parses the ModuleCode and TaskNum from it.
     */
    private Pair<ModuleCode, Integer> parseTaskNumString(String inp) throws ParseException {
        assert !inp.isEmpty();

        Scanner sc = new Scanner(inp);
        String modCodeString = sc.next();
        if (!ModuleCode.isValidModuleCode(modCodeString)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        ModuleCode moduleCode = new ModuleCode(modCodeString);

        if (!sc.hasNextInt()) {
            throw new ParseException(TaskNumManager.MESSAGE_USAGE_CONSTRAINTS);
        }
        int taskNum = sc.nextInt();

        if (sc.hasNext()) {
            throw new ParseException(TaskNumManager.MESSAGE_USAGE_CONSTRAINTS);
        }
        sc.close();

        return new Pair<ModuleCode, Integer>(moduleCode, taskNum);
    }
}
