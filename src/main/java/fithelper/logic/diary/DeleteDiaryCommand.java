package fithelper.logic.diary;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.diary.Content;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;

/**
 * Deletes a entry identified using it's displayed index from the address book.
 */
public class DeleteDiaryCommand extends Command {

    public static final String COMMAND_WORD = "deleteDiary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the diary identified by the diary date string used in the displayed diary list.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-31 ";

    public static final String MESSAGE_DELETE_DIARY_SUCCESS = "Deleted Diary: %1$s";

    private static final String MESSAGE_COMMIT = "Delete a diary";

    private static final Logger logger = LogsCenter.getLogger(DeleteDiaryCommand.class);

    private final DiaryDate diaryDate;

    public DeleteDiaryCommand(DiaryDate diaryDate) {
        this.diaryDate = diaryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Diary> lastShownList;

        lastShownList = model.getFilteredDiaryList();

        if (!model.hasDiaryDate(diaryDate)) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIARY_DATE);
        }

        Diary deleteDiary = getDiaryByDate(lastShownList, diaryDate);
        logger.info("previous diary in the list: " + deleteDiary.toString());
        model.deleteDiary(deleteDiary);

        model.commit(MESSAGE_COMMIT);
        logger.info("Deleted an entry");

        return new CommandResult(String.format(MESSAGE_DELETE_DIARY_SUCCESS, diaryDate),
                CommandResult.DisplayedPage.DIARY, false);
    }

    public Diary getDiaryByDate(List<Diary> diaryList, DiaryDate diaryDate) {
        Diary tempDiary = new Diary(diaryDate, new Content("test"));

        for (Diary diary : diaryList) {
            logger.info("this diary: " + diary.toString());
            if (diary.getDiaryDate().toString().equals(diaryDate.toString())) {
                logger.info("matched diary detected: " + diary.toString());
                tempDiary.setContent(diary.getContent());
            }
        }
        return tempDiary;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDiaryCommand // instanceof handles nulls
                && diaryDate.equals(((DeleteDiaryCommand) other).diaryDate)); // state check
    }
}
