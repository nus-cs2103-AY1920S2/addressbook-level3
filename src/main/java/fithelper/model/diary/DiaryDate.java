package fithelper.model.diary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * Represents ID of a diary.
 */
public class DiaryDate {
    public static final String MESSAGE_ERROR = "Date conversion from strings has failed.";
    private static final Logger logger = LogsCenter.getLogger(DiaryDate.class);

    public final String value;
    private LocalDate diaryDate;

    /**
     * Constructs a {@code DiaryDate}.
     */
    public DiaryDate() {
        this.diaryDate = LocalDate.now();
        this.value = this.diaryDate.toString();
    }

    /**
     * Constructs a {@code DiaryDate}.
     *
     * @param diaryDate diary Date.
     */
    public DiaryDate(LocalDate diaryDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.diaryDate = diaryDate;
        this.value = diaryDate.format(formatter);
    }

    /**
     * Constructs a {@code DiaryDate}.
     *
     * @param dateStr a string representation of date
     */
    public DiaryDate(String dateStr) throws ParseException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            this.diaryDate = date;
            this.value = diaryDate.format(formatter);
        } catch (Exception e) {
            throw new ParseException(MESSAGE_ERROR);
        }
    }

    /**
     * Checks whether a date string is valid for LocalDate conversion.
     * @param dateStr
     * @return
     */
    public static boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            logger.info(String.valueOf(date));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LocalDate getDiaryDate() {
        return diaryDate;
    }

    public String getValue() {
        return value;
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
        return diaryDate.toString().equalsIgnoreCase(diaryDate.diaryDate.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaryDate);
    }
}
