package seedu.address.logic.parser.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.LessonEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new LessonEditCommand object
 */
public class LessonEditCommandParser implements Parser<LessonEditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LessonEditCommand
     * and returns an LessonEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_MODULE_CODE, PREFIX_AT, PREFIX_VENUE, PREFIX_TYPE, PREFIX_NEXT);

        Index index;
        if (argMultimap.getPreamble().trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE));
        }

        ModuleCode target;

        LessonEditCommand.EditLessonDescriptor editLessonDescriptor = new LessonEditCommand.EditLessonDescriptor();

        if (argMultimap.getAllValues(PREFIX_MODULE_CODE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_MODULE_CODE).size() < 2
                && argMultimap.getValue(PREFIX_TYPE) == null && argMultimap.getValue(PREFIX_AT) == null
                && argMultimap.getValue(PREFIX_VENUE) == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.numOfValuesPresent(PREFIX_MODULE_CODE) > 2) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "two", PREFIX_MODULE_CODE));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_TYPE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_TYPE));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_AT) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_VENUE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_VENUE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        List<String> mods = argMultimap.getAllValues(PREFIX_MODULE_CODE);
        target = ParserUtil.parseModuleCode(mods.get(0));
        if (mods.size() == 2) {
            editLessonDescriptor.setModuleCode(ParserUtil.parseModuleCode(mods.get(1)));
            target = ParserUtil.parseModuleCode(mods.get(0));
        }

        if (argMultimap.getValue(PREFIX_TYPE) != null) {
            editLessonDescriptor.setLessonType(ParserUtil.parseLessonType(argMultimap.getValue(PREFIX_TYPE)));
        }

        if (argMultimap.getValue(PREFIX_AT) != null) {
            DayAndTime dayAndTime = ParserUtil.parseDayAndTime(argMultimap.getValue(PREFIX_AT));
            editLessonDescriptor.setDayAndTime(dayAndTime);
        }

        if (argMultimap.getValue(PREFIX_VENUE) != null) {
            if (argMultimap.getValue(PREFIX_VENUE).equals("")) {
                editLessonDescriptor.setVenue("");
            } else {
                editLessonDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE)));
            }
        }

        return new LessonEditCommand(target, index, editLessonDescriptor);

    }
}
