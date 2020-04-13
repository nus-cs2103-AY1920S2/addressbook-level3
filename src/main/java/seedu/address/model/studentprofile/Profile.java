package seedu.address.model.studentprofile;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import seedu.address.model.ModuleBook;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.NusModule;


/**
 * Creates a profile class to hold all profile information.
 */
public class Profile {

    private static SimpleStringProperty cap;
    private static ArrayList<NusModule> modulesTaken;
    private static ArrayList<NusModule> modulesCurrentlyTaking;
    private String name;
    private Major major;
    private ObservableValue<String> majorString;

    public Profile(ModuleBook moduleBook) {

        cap = new SimpleStringProperty("" + moduleBook.getCap());

    }

    public static ArrayList<NusModule> getModulesTaken() {
        return modulesTaken;
    }

    public static ArrayList<NusModule> getModulesCurrentlyTaking() {
        return modulesCurrentlyTaking;
    }

    public SimpleStringProperty getCap() {
        return cap;
    }

    public String getName() {
        return name;
    }

    public ObservableValue<String> getMajor() {
        return majorString;
    }

    public static void setCap(String value) {
        cap.set(value);
    }


    public void setMajor(Major major) {

        this.major = major;
        majorString = new SimpleStringProperty(major.nameOfMajor);
    }


}
