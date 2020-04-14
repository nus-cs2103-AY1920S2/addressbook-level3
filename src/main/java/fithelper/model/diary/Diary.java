package fithelper.model.diary;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.diary.DeleteDiaryCommand;

/**
 * Represents a diary in diary list.
 */
public class Diary {

    private static final Logger logger = LogsCenter.getLogger(DeleteDiaryCommand.class);

    //Identity field
    private final DiaryId diaryId;

    //FoodCalorieDatum fields
    private DiaryDate diaryDate;
    private Content content;

    public Diary (DiaryDate diaryDate, Content content) {
        requireAllNonNull(diaryDate, content);
        this.diaryDate = diaryDate;
        this.diaryId = new DiaryId(diaryDate.toString());
        this.content = content;
    }

    public DiaryId getDiaryId() {
        return diaryId;
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

    /**
     * Pends new content to the diary.
     * @param str newly added content
     *//*
    public void addContent(String str) {
        this.content.add(str);
    }*/

    /**
     * Clears the content of the diary.
     */
    public void clearContent() {
        this.content = new Content("");
    }

    /**
     * Returns true if both Diaries are on the same date and have the same content.
     * This defines a weaker notion of equality between two entries.
     */
    public boolean isSameDiary(Diary anotherDiary) {
        logger.info("checking same diary: " + this.toString() + " == " + anotherDiary.toString());
        if (anotherDiary == this) {
            return true;
        }

        boolean isSame = anotherDiary != null
                && anotherDiary.getDiaryDate().toString().equals(getDiaryDate().toString())
                && anotherDiary.getContent().value.equals(getContent().value);
        logger.info("res: " + isSame);
        return isSame;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Date: [%s] Content: %s",
                diaryId, diaryDate, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diary diary = (Diary) o;
        return diary.getDiaryDate().equals(getDiaryDate())
                && diary.getContent().equals(getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.diaryId);
    }

}
