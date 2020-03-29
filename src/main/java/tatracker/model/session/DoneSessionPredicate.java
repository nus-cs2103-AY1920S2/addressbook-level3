package tatracker.model.session;

import tatracker.commons.util.StringUtil;

import java.util.function.Predicate;

/**
 * Tests that a {@code ModuleCode} matches the keyword given.
 */
public class DoneSessionPredicate implements Predicate<Session> {

    private final String moduleCode;

    public DoneSessionPredicate(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public boolean test(Session session) {
        System.out.println(moduleCode);
        return StringUtil.containsWordIgnoreCase(moduleCode, session.getModuleCode());
    }
}
