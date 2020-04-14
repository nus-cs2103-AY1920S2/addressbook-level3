package fithelper.model.diary;

import java.util.Objects;

/**
 * Represents ID of a diary.
 */
public class DiaryId {
    public final String value;

    /**
     * Constructs a {@code DiaryId}.
     *
     * @param value diary id.
     */
    public DiaryId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiaryId diaryId = (DiaryId) o;
        return value == diaryId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
