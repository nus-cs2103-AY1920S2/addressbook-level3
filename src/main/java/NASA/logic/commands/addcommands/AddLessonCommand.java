package NASA.logic.commands.addcommands;

import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static NASA.logic.parser.CliSyntax.PREFIX_DATE;
import static NASA.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static NASA.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static NASA.logic.parser.CliSyntax.PREFIX_NOTE;

import NASA.model.activity.Lesson;
import NASA.model.module.ModuleCode;

public class AddLessonCommand extends AddCommand {

    public static final String COMMAND_WORD = "lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the module's activity list. "
            + "Parameters:"
            + PREFIX_MODULE + "MODULE CODE"
            + PREFIX_DATE + "DATE"
            + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME"
            + PREFIX_PRIORITY + "PRIORITY"
            + PREFIX_NOTE + "NOTE" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233"
            + PREFIX_DATE + "2020-02-12"
            + PREFIX_ACTIVITY_NAME + "Tutorial"
            + PREFIX_PRIORITY + "1"
            + PREFIX_NOTE + "Remember to study content before coming.";

    public AddLessonCommand(Lesson lesson, ModuleCode moduleCode) {
        super(lesson, moduleCode);
    }
}
