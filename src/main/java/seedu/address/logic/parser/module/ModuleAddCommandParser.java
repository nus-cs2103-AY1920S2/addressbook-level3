package seedu.address.logic.parser.module;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.module.ModuleAddCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

public class ModuleAddCommandParser implements Parser<ModuleAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeclareMajorCommand
     * and returns a DeclareMajorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ModuleAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            ModuleCode moduleCode = new ModuleCode(args);
            return new ModuleAddCommand(moduleCode);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
