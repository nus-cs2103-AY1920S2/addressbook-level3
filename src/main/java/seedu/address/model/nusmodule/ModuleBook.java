package seedu.address.model.nusmodule;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleBook {
    private List<NusModule> modules;

    public ModuleBook() {
        this.modules = new ArrayList<>();
    }

    public ModuleBook(List<NusModule> modules) {
        this.modules = modules;
    }

    public void addModule(NusModule module) {
        this.modules.add(module);
    }

    public void deleteModule(ModuleCode moduleCode) {
        int index = -1;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleCode().equals(moduleCode)) {
                index = i;
            }
        }
        modules.remove(index);
    }

    public boolean hasModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        for (NusModule module: modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public double getCap() {
        Capulator capulator = new Capulator(modules);
        return capulator.calculateCap();
    }
}
