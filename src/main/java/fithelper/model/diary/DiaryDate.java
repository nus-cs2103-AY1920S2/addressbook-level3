package fithelper.model.diary;

import fithelper.logic.parser.exceptions.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Represents ID of a diary.
 */
public class DiaryDate {

    public static final String MESSAGE_ERROR =
            "Date conversion from strings has failed.";

    public LocalDate diaryDate;

    /**
     * Constructs a {@code DiaryDate}.
     */
    public DiaryDate() {
        this.diaryDate = LocalDate.now();
    }

    /**
     * Constructs a {@code DiaryDate}.
     *
     * @param diaryDate diary Date.
     */
    public DiaryDate(LocalDate diaryDate) {
        this.diaryDate = diaryDate;
    }

    /**
     * Constructs a {@code DiaryDate}.
     *
     * @param dateStr a string representation of date
     */
    public DiaryDate(String dateStr) throws ParseException {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            this.diaryDate = date;
        } catch (Exception e) {
            throw new ParseException(MESSAGE_ERROR);
        }

    }

    public LocalDate getDiaryDate() {
        return diaryDate;
    }

    @Override
    public String toString() {
        return this.diaryDate.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiaryDate diaryDate = (DiaryDate) o;
        return diaryDate.equals(diaryDate.diaryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaryDate);
    }
}
