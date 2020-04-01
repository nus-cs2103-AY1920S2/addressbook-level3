package seedu.address.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;

import java.util.stream.Stream;

import seedu.address.logic.commands.module.ModuleGradeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.grades.LetterGrade;
import seedu.address.model.module.ModuleCode;

public class ModuleGradeCommandParser implements Parser<ModuleGradeCommand> {
    public static final String MESSAGE_GRADE_INVALID = "Grade is invalid: %1$s";

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeclareMajorCommand
     * and returns a DeclareMajorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ModuleGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRADE);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleGradeCommand.MESSAGE_USAGE));
        }

        try {
            ModuleCode moduleCode = new ModuleCode(argMultimap.getPreamble());

            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                String letterGradeString = argMultimap.getValue(PREFIX_GRADE).get();
                try {
                    LetterGrade letterGrade = LetterGrade.valueOf(letterGradeString);
                    return new ModuleGradeCommand(moduleCode, letterGrade);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(String.format(MESSAGE_GRADE_INVALID, letterGradeString));
                }
            } else {
                return new ModuleGradeCommand(moduleCode);
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
