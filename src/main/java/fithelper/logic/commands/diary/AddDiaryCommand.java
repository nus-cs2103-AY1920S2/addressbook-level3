package fithelper.logic.commands.diary;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DIARYCONTENT;
import static java.util.Objects.requireNonNull;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.diary.Diary;

/**
 * Adds a diary to FitHelper.
 */
public class AddDiaryCommand extends Command {

    public static final String COMMAND_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a diary to FitHelper. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_DIARYCONTENT + "DIARY CONTENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-31"
            + PREFIX_DIARYCONTENT + "Today is my birthday. I ate a huge birthday cake, but I also went to the gym with"
            + " my friends. Everything was just perfect. In my twenty, I'm gonna turn heat up.";

    public static final String MESSAGE_SUCCESS = "New Diary added: %1$s";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in FitHelper";

    private final Diary toAdd;

    /**
     * Creates an AddDiaryCommand to add the specified {@code Diary}
     */
    public AddDiaryCommand(Diary diary) {
        requireNonNull(diary);
        toAdd = diary;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (model.hasDiary(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DIARY);
        }

        model.addDiary(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandResult.DisplayedPage.DIARY, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDiaryCommand // instanceof handles nulls
                && toAdd.equals(((AddDiaryCommand) other).toAdd));
    }
}

