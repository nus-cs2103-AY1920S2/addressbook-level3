package fithelper.logic.diary;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DIARY_CONTENT;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.diary.Diary;

/**
 * Adds a diary to FitHelper.
 */
public class AddDiaryCommand extends Command {

    public static final String COMMAND_WORD = "addDiary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a diary to FitHelper. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_DIARY_CONTENT + "DIARY CONTENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-31"
            + PREFIX_DIARY_CONTENT + "I ate a huge birthday cake today.";

    public static final String MESSAGE_SUCCESS = "New Diary added: %1$s";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in FitHelper";

    private static final String MESSAGE_COMMIT = "Add a diary";

    private static final Logger logger = LogsCenter.getLogger(AddDiaryCommand.class);

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

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("Added a new diary [%s]", toAdd.toString()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandResult.DisplayedPage.DIARY, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDiaryCommand // instanceof handles nulls
                && toAdd.equals(((AddDiaryCommand) other).toAdd));
    }
}

