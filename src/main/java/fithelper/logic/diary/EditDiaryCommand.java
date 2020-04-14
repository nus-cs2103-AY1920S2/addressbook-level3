package fithelper.logic.diary;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DIARY_CONTENT;
import static fithelper.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.core.Messages;
import fithelper.commons.util.CollectionUtil;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.diary.Content;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;

/**
 * Edits the content of the diary of a specific date.
 */
public class EditDiaryCommand extends Command {

    public static final String COMMAND_WORD = "editDiary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the diary identified. \n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_DIARY_CONTENT + "DIARY CONTENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-31"
            + PREFIX_DIARY_CONTENT + "Today I exercised a lot.";

    public static final String MESSAGE_EDIT_DIARY_SUCCESS = "Edited Diary: %1$s";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in fitHelper.";

    private static final String MESSAGE_COMMIT = "Edit a diary";

    private static final Logger logger = LogsCenter.getLogger(EditDiaryCommand.class);

    private final String diaryId;
    private final EditDiaryDescriptor editDiaryDescriptor;

    /**
     * @param diaryId of the diary in the filtered diary list to edit
     * @param editDiaryDescriptor details to edit the diary with
     */
    public EditDiaryCommand(String diaryId, EditDiaryDescriptor editDiaryDescriptor) {
        requireNonNull(diaryId);
        requireNonNull(editDiaryDescriptor);

        this.diaryId = diaryId;
        this.editDiaryDescriptor = new EditDiaryDescriptor(editDiaryDescriptor);
    }

    public String getDiaryId() {
        return diaryId;
    }

    public EditDiaryDescriptor getEditDiaryDescriptor() {
        return editDiaryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Diary> lastShownList;

        lastShownList = model.getFilteredDiaryList();

        Diary editedDiary = createEditedDiary(diaryId, editDiaryDescriptor);

        if (!model.hasDiaryDate(editedDiary.getDiaryDate())) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIARY_DATE);
        }

        if (model.hasDiary(editedDiary)) {
            throw new CommandException(MESSAGE_DUPLICATE_DIARY);
        }

        DiaryDate prevDiaryDate = editedDiary.getDiaryDate();
        Diary prevDiary = getDiaryByDate(lastShownList, prevDiaryDate);

        model.setDiary(prevDiary, editedDiary);
        model.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("edited a new diary [%s]", editDiaryDescriptor.toString()));

        //model.updateFil
        return new CommandResult(String.format(MESSAGE_EDIT_DIARY_SUCCESS, editedDiary),
                CommandResult.DisplayedPage.DIARY, false);
    }

    /**
     * Creates and returns a {@code Diary} with the details of {@code diaryToEdit}
     * edited with {@code editDiaryDescriptor}.
     */
    private static Diary createEditedDiary(String diaryId, EditDiaryDescriptor editDiaryDescriptor) {
        assert diaryId != null;

        DiaryDate updatedDiaryDate = editDiaryDescriptor.getDiaryDate();
        Content updatedContent = editDiaryDescriptor.getContent();

        return new Diary(updatedDiaryDate, updatedContent);
    }

    public Diary getDiaryByDate(List<Diary> diaryList, DiaryDate diaryDate) {
        Diary tempDiary = new Diary(diaryDate, new Content(""));
        for (Diary diary : diaryList) {
            if (diary.getDiaryDate().equals(diaryDate)) {
                tempDiary.setContent(diary.getContent());
            }
        }
        return tempDiary;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDiaryCommand)) {
            return false;
        }

        // state check
        EditDiaryCommand e = (EditDiaryCommand) other;
        return diaryId.equals(e.diaryId)
                && editDiaryDescriptor.equals(e.editDiaryDescriptor);
    }

    /**
     * Stores the details to edit the diary with. Each non-empty field value will replace the
     * corresponding field value of the diary.
     */
    public static class EditDiaryDescriptor {
        private DiaryDate diaryDate;
        private Content content;

        public EditDiaryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code status} is used internally.
         */
        public EditDiaryDescriptor(EditDiaryDescriptor toCopy) {
            setDiaryDate(toCopy.diaryDate);
            setContent(toCopy.content);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(diaryDate, content);
        }

        public DiaryDate getDiaryDate() {
            return diaryDate;
        }

        public Content getContent() {
            return content;
        }

        public void setDiaryDate(DiaryDate diaryDate) {
            this.diaryDate = diaryDate;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDiaryDescriptor)) {
                return false;
            }

            // state check

            EditDiaryCommand.EditDiaryDescriptor e = (EditDiaryCommand.EditDiaryDescriptor) other;

            return diaryDate.getDiaryDate().equals(e.getDiaryDate())
                    && getContent().equals(e.getContent());
        }
    }
}
