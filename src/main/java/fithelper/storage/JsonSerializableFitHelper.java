package fithelper.storage;

import static fithelper.logic.diary.AddDiaryCommand.MESSAGE_DUPLICATE_DIARY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.FitHelper;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.diary.Diary;
import fithelper.model.entry.Entry;

/**
 * An Immutable FitHelper that is serializable to JSON format.
 */
@JsonRootName(value = "fithelper")
class JsonSerializableFitHelper {

    public static final String MESSAGE_DUPLICATE_ENTRY = "Entries list contains duplicate entry(s).";

    private final List<JsonAdaptedEntry> entries = new ArrayList<>();
    private final List<JsonAdaptedDiary> diaries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFitHelper} with the given entries.
     */
    @JsonCreator
    public JsonSerializableFitHelper(@JsonProperty("entries") List<JsonAdaptedEntry> entries,
                                     @JsonProperty("diaries") List<JsonAdaptedDiary> diaries) {
        this.entries.addAll(entries);
        this.diaries.addAll(diaries);
    }

    /**
     * Converts a given {@code ReadOnlyFitHelper} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFitHelper}.
     */
    public JsonSerializableFitHelper(ReadOnlyFitHelper source) {
        entries.addAll(source.getFoodList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
        entries.addAll(source.getSportsList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
        diaries.addAll(source.getDiaryList().stream().map(JsonAdaptedDiary::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this fitHelper into the model's {@code FitHelper} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FitHelper toModelType() throws IllegalValueException {
        FitHelper fitHelper = new FitHelper();
        for (JsonAdaptedDiary jsonAdaptedOrder : diaries) {
            Diary diary = jsonAdaptedOrder.toModelType();
            if (fitHelper.hasDiary(diary)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DIARY);
            }
            fitHelper.addDiary(diary);
        }

        for (JsonAdaptedEntry jsonAdaptedEntry : entries) {
            Entry entry = jsonAdaptedEntry.toModelType();
            if (fitHelper.hasEntry(entry)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTRY);
            }
            fitHelper.addEntry(entry);
        }
        return fitHelper;
    }

}
