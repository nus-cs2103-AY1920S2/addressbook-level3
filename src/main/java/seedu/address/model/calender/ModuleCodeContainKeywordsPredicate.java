package seedu.address.model.calender;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.nusmodule.ModuleTask;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ModuleCodeContainKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public ModuleCodeContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        if (task instanceof ModuleTask) {
            return keywords.stream()
                    .anyMatch(keyword ->
                            StringUtil.containsWordIgnoreCase
                                    (((ModuleTask) task).getModuleRelated().toString(), keyword));
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCodeContainKeywordsPredicate); // state check
    }

}
