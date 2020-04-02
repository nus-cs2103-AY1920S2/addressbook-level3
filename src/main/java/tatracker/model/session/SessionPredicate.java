package tatracker.model.session;

import java.util.List;
import java.util.function.Predicate;

import tatracker.commons.util.StringUtil;


/**
 * Tests that a {@code Session}'s arguments matches any of the keywords given.
 */
public class SessionPredicate implements Predicate<Session> {

    private final List<String> keywords;

    public SessionPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        String s = "";
        for (int i = 0; i < keywords.size(); i++) {
            s += keywords.get(i) + " ";
        }
        return s;
    }

    @Override
    public boolean test(Session session) {

        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(keyword, session.getDate().toString()))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(keyword, session.getModuleCode()))
                || keywords.stream()
               .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(keyword, session.getSessionType().toString())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof tatracker.model.session.SessionPredicate // instanceof handles nulls
                && keywords.equals(((tatracker.model.session.SessionPredicate) other).keywords)); // state check
    }

}
