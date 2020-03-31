package tatracker.model.session;

import java.util.function.Predicate;

import tatracker.commons.util.StringUtil;

/**
 * Tests that a {@code ModuleCode} matches the keyword given.
 */
public class DoneSessionPredicate implements Predicate<Session> {

    private final String moduleCode;

    public DoneSessionPredicate(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    @Override
    public boolean test(Session session) {
        System.out.println(moduleCode);
        return StringUtil.containsWordIgnoreCase(moduleCode, session.getModuleCode());
    }
}
