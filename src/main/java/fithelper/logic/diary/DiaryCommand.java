package fithelper.logic.diary;

import static fithelper.logic.commands.CommandResult.DisplayedPage.DIARY;
import static fithelper.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static java.util.Objects.requireNonNull;

import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class DiaryCommand extends Command {

    public static final String COMMAND_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays diary page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Diary Page ~";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);

        return new CommandResult(String.format(MESSAGE_SUCCESS), DIARY, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryCommand);
    }
}
