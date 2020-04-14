package fithelper.model.diary;

import java.util.List;
import java.util.function.Predicate;

import fithelper.commons.util.StringUtil;

/**
 * Tests that a {@code Diary}'s {@code DiaryContent} matches any of the keywords given.
 */
public class DiaryContentContainsKeywordsPredicate implements Predicate<Diary> {

    private final List<String> keywords;

    public DiaryContentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Diary diary) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(diary.getContent().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryContentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DiaryContentContainsKeywordsPredicate) other).keywords)); // state check
    }

}

