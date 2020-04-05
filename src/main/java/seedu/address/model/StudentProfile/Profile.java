package seedu.address.model.StudentProfile;

import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.NusModule;

import java.util.ArrayList;

public class Profile {

    private static ArrayList<NusModule> modulesTaken;
    private static ArrayList<NusModule> modulesCurrentlyTaking;
    private String name;
    private String cap;
    private String targetCap;
    private Major major;

    public Profile(String name, Major major, String cap, String targetCap) {
        this.name = name;
        this.cap = cap;
        this.targetCap = targetCap;
        this.major = major;
        modulesCurrentlyTaking = new ArrayList<>();
        modulesTaken = new ArrayList<>();
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

    public String getMajor() {
        return major.toString();
    }

    public void setMajor(Major major) {
        this.major = major;
    }


}
