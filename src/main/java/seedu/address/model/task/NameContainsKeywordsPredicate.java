package seedu.address.model.task;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/** Tests that a {@code Task}'s {@code Name} matches any of the keywords given. */
public class NameContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;
    private final Set<Tag> tags;
    private final int threshold = 1;

    public NameContainsKeywordsPredicate(
            List<String> keywords, Set<Tag> tags) { // if second list is available
        this.keywords = keywords;
        this.tags = tags;
    }

    public NameContainsKeywordsPredicate(List<String> keywords) { // if second list is available
        this.keywords = keywords;
        this.tags = new HashSet();
    }

    /**
     * Score always starts of as threshold + 1 as anything above the threshold is not shown. We then
     * decrement the score whenever a name match occurs/edit distance < 2 or tag match is found.
     * This ensures that any task with tag matching or name matching will be displayed. The score
     * will then also provide a relevance order.
     *
     * <p>This is how the score is calculated: name score - total tag match count
     */
    @Override
    public boolean test(Task task) { // change test to return an int value as the edit distance
        int score = getEditDistance(task);
        boolean hasTag = false;
        for (Tag t : tags) {
            if (task.hasTag(t)) {
                hasTag = true;
                break;
            }
        }
        return score <= threshold || hasTag;
    }

    public Comparator<Task> getSearchOrderComparator() {
        return new Comparator<>() {
            @Override
            public int compare(Task task1, Task task2) {
                int score1 = getEditDistance(task1) - countTag(task1);
                int score2 = getEditDistance(task2) - countTag(task2);
                if (score1 == score2) {
                    return 0;
                }
                return score1 < score2 ? -1 : 1;
            }
        };
    }

    private int countTag(Task task) {
        int count = 0;
        for (Tag t : tags) {
            if (task.hasTag(t)) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Predicate has been enhanced to return true if the final score < 2. A chunk is a subsequence
     * of the taskname that has as many words as in the search phrase. we go through all chunks of
     * the task name and calculate a score for each chunk and take the minimum of all scores. This
     * minimum is defined as the name score of a task.
     *
     * <p>Score is calculated by: 1. A partial name match where input matches start of chunk => 1 2.
     * A match where chunk and input have edit distance < 2 => 1 3. A full chunk match => 0
     *
     * <p>The edit distance threshold is set at 2 so that phrases that are too dissimilar will not
     * show up.
     */
    private int getEditDistance(Task task) {
        if (keywords.size() == 0) {
            return threshold + 1;
        }

        int score = threshold + 1;
        String joinnedKeywords = String.join(" ", keywords).toLowerCase();
        String taskName = task.getName().fullName.toLowerCase();
        String[] splitTaskName = taskName.split("\\s+");

        for (int i = 0; i < splitTaskName.length; i++) {
            String joinnedPhrase =
                    String.join(" ", Arrays.copyOfRange(splitTaskName, i, i + keywords.size()));

            if (StringUtil.keywordMatchStartOfPhrase(joinnedKeywords, joinnedPhrase)) {
                score = Math.min(1, score);
            }

            if (joinnedKeywords.equals(joinnedPhrase)) {
                score = Math.min(0, score);
            }

            if (joinnedKeywords.length() > 2) {
                int currScore =
                        StringUtil.levenshteinDistanceCompare(
                                joinnedPhrase, joinnedKeywords, threshold);
                if (currScore >= 0) {
                    score = Math.min(score, currScore);
                }
            }
        }
        return score;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                        && keywords.equals(
                                ((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
