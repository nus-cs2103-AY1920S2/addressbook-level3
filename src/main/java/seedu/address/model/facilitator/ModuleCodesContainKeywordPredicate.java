package seedu.address.model.facilitator;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Facilitator}'s {@code Module Code} matches the keyword given.
 */
public class ModuleCodesContainKeywordPredicate implements Predicate<Facilitator> {
    private final String keyword;

    public ModuleCodesContainKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Facilitator facilitator) {
        return facilitator.getModuleCodes().stream()
                .anyMatch(moduleCode -> StringUtil.containsWordIgnoreCase(moduleCode.value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCodesContainKeywordPredicate // instanceof handles nulls
                && keyword.equals(((ModuleCodesContainKeywordPredicate) other).keyword)); // state check
    }
}
