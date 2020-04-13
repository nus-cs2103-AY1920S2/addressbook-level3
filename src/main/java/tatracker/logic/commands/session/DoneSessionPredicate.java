// @@author Chuayijing

package tatracker.logic.commands.session;

import java.util.function.Predicate;

import tatracker.commons.util.StringUtil;
import tatracker.model.session.Session;

/**
 * Tests that a {@code ModuleCode} matches the keyword given.
 */
public class DoneSessionPredicate implements Predicate<Session> {

    private final String moduleCode;

    public DoneSessionPredicate(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    @Override
    public boolean test(Session session) {
        return StringUtil.containsWordIgnoreCase(moduleCode, session.getModuleCode());
    }
}
