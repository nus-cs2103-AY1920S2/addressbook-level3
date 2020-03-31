package seedu.address.searcher;

import java.util.ArrayList;

/**<h1>Mod Planner Logic Class</h1>
 * just simple logic and functions for mod planing
 */
public class ModulePlanner {
    private ArrayList<Module> myMods;

    public ModulePlanner() {
        myMods = new ArrayList<>();
    }

    public void addModule(String moduleCode) {
        myMods.add(Search.findModule(moduleCode));
    }

    public void removeModule(String moduleCode) {

    }
}
