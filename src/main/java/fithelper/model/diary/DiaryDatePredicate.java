package fithelper.model.diary;

import java.util.function.Predicate;

/**
 * Tests that a {@code Diary}'s {@code DiaryContent} matches any of the diaryDate given.
 */
public class DiaryDatePredicate implements Predicate<Diary> {

    private final DiaryDate diaryDate;

    public DiaryDatePredicate(DiaryDate diaryDate) {
        this.diaryDate = diaryDate;
    }

    @Override
    public boolean test(Diary diary) {
        return diary.getDiaryDate().toString().equalsIgnoreCase(diaryDate.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryDatePredicate // instanceof handles nulls
                && diaryDate.equals(((DiaryDatePredicate) other).diaryDate)); // state check
    }

}


