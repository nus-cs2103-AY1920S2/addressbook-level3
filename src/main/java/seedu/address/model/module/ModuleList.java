package seedu.address.model.module;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.module.exceptions.ModuleCodeException;

/**
 * List of Modules. Checks for duplicates
 */
public class ModuleList {
    private ObservableList<Module> internalList = FXCollections.observableArrayList();

    public ModuleList() {

    }

    /**
     * To be executed every time whenever a module is added.
     * @param module
     * @return if there is a duplicate
     */
    public boolean contains(Module module) {
        final Module moduleForPredicate = module;
        FilteredList<Module> filteredList = internalList.filtered((x) -> x.equals(moduleForPredicate));
        if(filteredList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList<Module> getInternalList() {
        return internalList;
    }

    /**
     * checks for duplicate modules first, then add into the moduleList.
     * @param module to be added to the ModuleList
     */
    public void add(Module module) throws ModuleCodeException {
        if(this.contains(module)) {
            throw new ModuleCodeException("Duplicate modules");
        } else {
            internalList.add(module);
        }
    }

    /**
     * Removes module from the list.
     * @param module
     * @throws ModuleCodeException
     */
    public void remove(Module module) throws ModuleCodeException{
        if(this.contains(module)) {
            internalList.remove(module);
        } else {
            throw new ModuleCodeException("Module does not exist");
        }
    }

    /**
     * Retrieves a module with the original module name inside the moduleList.
     * @param moduleCode
     * @return
     * @throws ModuleCodeException
     */

    public Module get(String moduleCode) throws ModuleCodeException {
        Module moduleToFind = new Module(moduleCode);
        if(this.contains(moduleToFind)) {
            int index = this.internalList.indexOf(moduleToFind);
            return this.internalList.get(index);
        } else {
            throw new ModuleCodeException("Module Not Found");
        }
    }
}
