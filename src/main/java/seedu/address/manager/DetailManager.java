package seedu.address.manager;

import seedu.address.commons.core.BaseManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

// TODO: Think of better name?
public class DetailManager extends BaseManager {
    private static Model model = ModelManager.getInstance();
    // Detail Manager will have ObservableLists that LogicManager will also contain.

    // Select commands will update the observable lists here
}
