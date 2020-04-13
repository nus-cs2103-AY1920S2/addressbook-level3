package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.module.Description;
import seedu.address.model.profile.course.module.ModularCredits;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.Preclusions;
import seedu.address.model.profile.course.module.PrereqTreeNode;
import seedu.address.model.profile.course.module.Prereqs;
import seedu.address.model.profile.course.module.SemesterData;
import seedu.address.model.profile.course.module.Title;

//@@author jadetayy

public class ModuleListTest extends ModuleList{

    private ModuleList moduleList = new ModuleList();
    Module module = new Module(new ModuleCode("CS2103T"), new Title(""), new Prereqs(""), new Preclusions(""),
            new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
            new PrereqTreeNode());
    ModuleCode moduleCode = new ModuleCode("CS2103T");


    @Test
    public void addModule_validModule_returnsTrue() {
        moduleList.addModule(module);
        assertTrue(moduleList.getModuleWithModuleCode(moduleCode).equals(module));
    }

    @Test
    public void addModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleList.addModule(null));
    }

    @Test
    public void hasModule_moduleInModuleList_returnsTrue() {
        moduleList.addModule(module);
        assertTrue(moduleList.hasModule(module));
    }

    @Test
    public void hasModule_moduleNotInModuleList_returnsFalse() {
        assertFalse(moduleList.hasModule(module));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleList.hasModule(null));
    }

    @Test
    public void hasModule_moduleDeletedFromModuleList_returnsFalse() throws ParseException {
        moduleList.addModule(module);
        moduleList.removeModuleWithModuleCode(moduleCode);
        assertFalse(moduleList.hasModule(module));
    }

    @Test
    public void hasModuleWithModuleCode_moduleCodeInModuleCodeList_returnsTrue() {
        moduleList.addModule(module);
        assertTrue(moduleList.hasModuleWithModuleCode(moduleCode));
    }

    @Test
    public void hasModuleWithModuleCode_moduleCodeNotInModuleCodeList_returnsFalse() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        moduleList.addModule(module);
        assertFalse(moduleList.hasModuleWithModuleCode(moduleCode));
    }

    @Test
    public void hasModuleWithModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleList.hasModuleWithModuleCode(null));
    }

    @Test
    public void removeModuleWithModuleCode_moduleCodeInModuleList_returnsTrue() throws ParseException {
        moduleList.addModule(module);
        moduleList.removeModuleWithModuleCode(moduleCode);
        assertFalse(moduleList.hasModuleWithModuleCode(moduleCode)); //false because module not in list anymore
    }

    @Test
    public void removeModuleWithModuleCode_moduleCodeNotInModuleList_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> moduleList.removeModuleWithModuleCode(moduleCode));
    }

    @Test
    public void removeModuleWithModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleList.removeModuleWithModuleCode(null));
    }

}
