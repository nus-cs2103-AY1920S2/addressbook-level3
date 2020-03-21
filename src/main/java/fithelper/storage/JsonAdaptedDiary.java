package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.diary.Content;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;

/**
 * Jackson-friendly version of {@link fithelper.model.diary.Diary}.
 */
public class JsonAdaptedDiary {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary's %s field is missing!";

    private String diaryId;

    private String diaryDate;
    private String content;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiary(@JsonProperty("diaryId") String diaryId,
                            @JsonProperty("diaryDate") String diaryDate,
                            @JsonProperty("content") String content) {
        this.diaryId = diaryId;
        this.diaryDate = diaryDate;
        this.content = content;
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedDiary(Diary source) {
        this.diaryId = source.getDiaryId().value;
        this.diaryDate = source.getDiaryDate().value;
        this.content = source.getContent().getValue();
    }

    /**
     * Build {@code DiaryDate} based on Json file string.
     *
     * @return Attribute diaryDate.
     * @throws IllegalValueException Invalid value for diaryDate.
     */
    public DiaryDate buildDiaryDate() throws IllegalValueException {
        if (diaryDate == null || diaryId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DiaryDate.class.getSimpleName()));
        }
        return new DiaryDate(diaryDate);
    }

    /**
     * Build {@code Content} based on Json file string.
     *
     * @return Attribute content.
     * @throws IllegalValueException Invalid value for content.
     */
    public Content buildDiaryContent() throws IllegalValueException {
        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        return new Content(content);
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     */
    public Diary toModelType() throws IllegalValueException {
        final DiaryDate modelDate = buildDiaryDate();
        final Content modelContent = buildDiaryContent();
        return new Diary(modelDate, modelContent);
    }
}
