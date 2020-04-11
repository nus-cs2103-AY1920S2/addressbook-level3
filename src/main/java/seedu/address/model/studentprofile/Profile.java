package seedu.address.model.studentprofile;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.model.ModuleBook;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.NusModule;

/**
 * Creates a profile class to hold all profile information.
 */
public class Profile {

    private String name;
    private String cap;
    private String targetCap;
    private Major major;

    public Profile(ModuleBook moduleBook) {

//       cap = "" + moduleBook.getCap();
    }


    public String getCap() {
        return cap;
    }

    public String getTargetCap() {
        return targetCap;
    }

    public String getName() {
        return name;
    }

    public void setMajor(Major major) {

        this.major = major;
    }

}
