package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT;

import java.time.DayOfWeek;
import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Finds the next lesson in Mod Manager that is happening soon.
 * Keyword matching is case insensitive.
 */
public class LessonFindCommand extends LessonCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CLASS + " " + COMMAND_WORD_FIND
            + ": Finds the next class\n"
            + "Example: " + COMMAND_GROUP_CLASS + " " + COMMAND_WORD_FIND + " " + PREFIX_NEXT
            + " or " + COMMAND_GROUP_CLASS + " " + COMMAND_WORD_FIND + " " + PREFIX_AT + " MONDAY";

    private final DayOfWeek day;

    public LessonFindCommand(DayOfWeek day) {
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String result;
        if (day == null) {
            Lesson lesson;
            lesson = model.findNextLesson();
            if (lesson == null) {
                result = "No more class this week";
            } else {
                result = "Next class:\n" + lesson.toString();
                updateList(model.findModule(lesson.getModuleCode()).get(), model);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            List<Lesson> lessonsOfTheDay = model.findLessonByDay(day);
            if (lessonsOfTheDay.size() == 0) {
                result = "No class on " + day.toString();
            } else {
                sb.append("Classes on ").append(day.toString()).append("\n");
                for (Lesson l : lessonsOfTheDay) {
                    sb.append("\u2022 ").append(l.toString()).append("\n");
                }
                result = sb.toString();
            }
        }
        return new CommandResult(result, CommandType.LESSON);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonFindCommand // instanceof handles nulls
                && day == ((LessonFindCommand) other).day); // state check
    }

}
