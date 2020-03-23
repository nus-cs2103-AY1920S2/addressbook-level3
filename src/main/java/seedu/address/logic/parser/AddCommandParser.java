package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModuleManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.DeadlineList;
import seedu.address.model.profile.course.module.personal.Personal;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException, DateTimeException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_SEMESTER, PREFIX_GRADE,
                        PREFIX_TASK, PREFIX_DEADLINE);

        // To check if Module argument exists since it is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Check that module code format is correct
        String moduleCodeString = argMultimap.getValue(PREFIX_MODULE).get();
        moduleCodeString = moduleCodeString.trim();
        moduleCodeString = moduleCodeString.toUpperCase();

        // Create module object
        ModuleCode moduleCode = new ModuleCode(moduleCodeString);
        Module module = ModuleManager.getModule(moduleCode);

        // Create Personal object
        Personal personal = new Personal();
        module.setPersonal(personal);

        // Get Semester
        String semester = argMultimap.getValue(PREFIX_SEMESTER).get();
        if (!ParserUtil.isInteger(semester)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        int intSemester = Integer.parseInt(semester);
        module.getPersonal().setSemester(intSemester);

        // Check for duplicate modules
        Module existingModule = null;
        HashMap<Integer, ArrayList<Module>> hashMap = Profile.getHashMap();
        for (ArrayList<Module> list: hashMap.values()) {
            for (Module moduleItr: list) {
                if (module.isSameModule(moduleItr)) {
                    existingModule = moduleItr;
                }
            }
        }

        if (existingModule != null) { // Module exists
            // Module exists, check if grade needs updating or task needs adding
            if (!arePrefixesPresent(argMultimap, PREFIX_GRADE) && !arePrefixesPresent(argMultimap, PREFIX_TASK)
                    && !arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                throw new ParseException(String.format("Error: Module already exists as "
                        + existingModule.getPersonal().getStatus() + ", "
                        + "please specify date or add a deadline", AddCommand.MESSAGE_USAGE));
            }

            // Check if grade needs to be updated
            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                String grade = argMultimap.getValue(PREFIX_GRADE).get();
                existingModule.getPersonal().setGrade(grade);
            }

            //Check if deadlineList needs to be updated
            if (arePrefixesPresent(argMultimap, PREFIX_TASK) && arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                DeadlineList deadlineList = existingModule.getPersonal().getDeadlineList();
                String task = argMultimap.getValue(PREFIX_TASK).get();
                String deadlineString = argMultimap.getValue(PREFIX_DEADLINE).get();
                String[] splitDeadline = deadlineString.split(" ");
                String date = splitDeadline[0];
                String time = splitDeadline[1];

                Deadline deadline = new Deadline(task, date, time);
                deadlineList.addDeadline(deadline);
            } else if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                DeadlineList deadlineList = existingModule.getPersonal().getDeadlineList();
                String task = argMultimap.getValue(PREFIX_TASK).get();
                Deadline deadline = new Deadline(task);
                deadlineList.addDeadline(deadline);
            }

            return new AddCommand(existingModule, true);

        } else { // Module does not exist

            // Add tasks to deadlinelist
            DeadlineList deadlineList = personal.getDeadlineList();
            if (arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_DEADLINE)) {
                String task = argMultimap.getValue(PREFIX_TASK).get();
                String deadlineString = argMultimap.getValue(PREFIX_DEADLINE).get();
                String[] splitDeadline = deadlineString.split(" ");
                String date = splitDeadline[0];
                String time = splitDeadline[1];

                Deadline deadline = new Deadline(task, date, time);
                deadlineList.addDeadline(deadline);
            } else if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                String task = argMultimap.getValue(PREFIX_TASK).get();
                Deadline deadline = new Deadline(task);
                deadlineList.addDeadline(deadline);
            }

            // Add grade if present
            String grade = null;
            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                grade = argMultimap.getValue(PREFIX_GRADE).get();
                personal.setGrade(grade);
            }

            // From current semester, determine status and update Personal
            int currentSemester = Integer.parseInt(Profile.getCurrentSemester());
            if (intSemester < currentSemester) {
                personal.setStatus("completed");
            } else if (intSemester == currentSemester) {
                personal.setStatus("in progress");
            } else {
                personal.setStatus("not taken");
            }

            // Add module to Profile class
            Profile.addModule(Integer.valueOf(semester), module);

            return new AddCommand(module);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
