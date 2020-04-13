package seedu.address.model.module;

import java.util.Comparator;

/**
 * Compares 2 Modules based on their ModuleCodes.
 */
public class ModuleComparator implements Comparator<Module> {
    @Override
    public int compare(Module o1, Module o2) {
        ModuleCode c1 = o1.getModuleCode();
        ModuleCode c2 = o2.getModuleCode();

        return c1.toString().compareTo(c2.toString());
    }
}
