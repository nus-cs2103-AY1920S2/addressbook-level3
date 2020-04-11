package seedu.address.model.studentprofile;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.NusModule;

/**
 * Creates a profile class to hold all profile information.
 */
public class Profile {

    private static ArrayList<NusModule> modulesTaken;
    private static ArrayList<NusModule> modulesCurrentlyTaking;
    private String name;
    private String cap;
    private String targetCap;
    private Major major;
    private ObservableValue<String> majorString;
    private ObservableValue<String> observableName;
    private ObservableValue<String> observableCap;
    private ObservableValue<String> observableTargetCap;

    public Profile(String name, Major major, String cap, String targetCap) {
        this.name = name;
        this.cap = cap;
        this.targetCap = targetCap;
        this.major = major;
        modulesCurrentlyTaking = new ArrayList<>();
        modulesTaken = new ArrayList<>();
        majorString = new SimpleStringProperty(major.nameOfMajor);

    }

    public Profile(ObservableValue<String> name, ObservableValue<String> major,
                   ObservableValue<String> cap, ObservableValue<String> targetCap) {

        this.observableName = name;
        this.observableCap = cap;
        this.observableTargetCap = targetCap;
        this.majorString = major;

    }


    public Profile() {

    }

    public static ArrayList<NusModule> getModulesTaken() {
        return modulesTaken;
    }

    public static ArrayList<NusModule> getModulesCurrentlyTaking() {
        return modulesCurrentlyTaking;
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

    public ObservableValue<String> getMajor() {
        return majorString;
    }

    public void setMajor(Major major) {

        this.major = major;
        majorString = new SimpleStringProperty(major.nameOfMajor);
    }


    public ObservableValue<String> getObservableName() {
        return observableName;
    }

    public ObservableValue<String> getObservableCap() {
        return observableCap;
    }

    public ObservableValue<String> getObservableTargetCap() {
        return observableTargetCap;
    }
}
