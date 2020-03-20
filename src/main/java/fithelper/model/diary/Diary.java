package fithelper.model.diary;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a diary in diary list.
 */
public class Diary {

    //Identity field
    private final DiaryId diaryId;

    //Data fields
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

    /**
     * Pends new content to the diary.
     * @param str newly added content
     */
    public void addContent(String str) {
        this.content.add(str);
    }

    /**
     * Clears the content of the diary.
     */
    public void clearContent() {
        this.content = new Content("");
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
        return diaryId.equals(diary.getDiaryId().value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.diaryId);
    }

}
