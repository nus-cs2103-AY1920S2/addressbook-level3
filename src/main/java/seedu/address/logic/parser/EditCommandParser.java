package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPEC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModuleManager;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;

/**
 * Parses input arguments and creates a new NewCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns a EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException, DateTimeException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COURSE_NAME, PREFIX_CURRENT_SEMESTER, PREFIX_SPEC,
                        PREFIX_MODULE, PREFIX_SEMESTER, PREFIX_GRADE);

        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) { // EDIT MODULE
            // Check that module already exists
            String moduleCodeString = argMultimap.getValue(PREFIX_MODULE).get();
            moduleCodeString = moduleCodeString.trim();
            moduleCodeString = moduleCodeString.toUpperCase();
            ModuleCode moduleCode = new ModuleCode(moduleCodeString);

            Module existingModule = null;
            ArrayList<Module> inList = null;
            Module module = ModuleManager.getModule(moduleCode);
            HashMap<Integer, ArrayList<Module>> hashMap = Profile.getHashMap();
            for (ArrayList<Module> list: hashMap.values()) {
                for (Module moduleItr: list) {
                    if (module.isSameModule(moduleItr)) {
                        existingModule = moduleItr;
                        inList = list;
                    }
                }
            }
            if (existingModule == null) {
                throw new ParseException(String.format("Error: Module does not exist", EditCommand.MESSAGE_USAGE));
            }

            int inSemester = getKey(hashMap, inList);

            // Module exists
            if (arePrefixesPresent(argMultimap, PREFIX_SEMESTER)) {
                String semester = argMultimap.getValue(PREFIX_SEMESTER).get();
                if (!ParserUtil.isInteger(semester)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                int intSemester = Integer.parseInt(semester);
                hashMap.get(inSemester).remove(existingModule);
                Profile.addModule(intSemester, existingModule);
                updateStatus(Profile.getCurrentSemester());
            }
            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                String grade = argMultimap.getValue(PREFIX_GRADE).get();
                existingModule.getPersonal().setGrade(grade);
            }
            return new EditCommand(existingModule);
        } else { // EDIT PROFILE
            if (!arePrefixesPresent(argMultimap, PREFIX_NAME) && !arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)
                    && !arePrefixesPresent(argMultimap, PREFIX_CURRENT_SEMESTER)
                    && !arePrefixesPresent(argMultimap, PREFIX_SPEC)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
                Profile.setName(name);
            }
            if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
                CourseName courseName = ParserUtil.parseCourseName(argMultimap.getValue(PREFIX_COURSE_NAME).get());
                Profile.setCourse(courseName);
            }
            if (arePrefixesPresent(argMultimap, PREFIX_CURRENT_SEMESTER)) {
                String currentSemesterString = argMultimap.getValue(PREFIX_CURRENT_SEMESTER).get();
                if (!ParserUtil.isInteger(currentSemesterString)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                int currentSemester = Integer.parseInt(currentSemesterString);
                Profile.setCurrentSemester(currentSemester);
                updateStatus(currentSemester);
            }
            if (arePrefixesPresent(argMultimap, PREFIX_SPEC)) {
                String specialisation = argMultimap.getValue(PREFIX_GRADE).get();
                Profile.setSpecialisation(specialisation);
            }
            return new EditCommand();
        }
    }

    /**
     * Updates statuses of all modules in the Profile
     */
    private void updateStatus(int currentSemester) {
        HashMap<Integer, ArrayList<Module>> hashMap = Profile.getHashMap();
        for (ArrayList<Module> list: hashMap.values()) {
            int semester = getKey(hashMap, list);
            for (Module moduleItr: list) {
                if (semester < currentSemester) {
                    moduleItr.getPersonal().setStatus("completed");
                } else if (semester == currentSemester) {
                    moduleItr.getPersonal().setStatus("in progress");
                } else {
                    moduleItr.getPersonal().setStatus("not taken");
                }
            }
        }
    }

    /**
     * Returns key of the given value
     */
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
