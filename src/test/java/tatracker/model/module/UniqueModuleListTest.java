//@@author aakanksha-rai

package tatracker.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.module.TypicalModules.CS2103T;
import static tatracker.testutil.module.TypicalModules.CS2103T_COPY;
import static tatracker.testutil.module.TypicalModules.CS3243;
import static tatracker.testutil.module.TypicalModules.NO_GROUPS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tatracker.model.module.exception.DuplicateModuleException;
import tatracker.model.module.exception.ModuleNotFoundException;

public class UniqueModuleListTest {

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(CS2103T));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(CS2103T);
        assertTrue(uniqueModuleList.contains(CS2103T));
    }

    @Test
    public void contains_moduleWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleList.add(CS2103T);
        assertTrue(uniqueModuleList.contains(CS2103T_COPY));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.add(null));
    }

    @Test
    public void add_duplicateModule_throwsDuplicateModuleException() {
        uniqueModuleList.add(CS2103T);
        assertThrows(DuplicateModuleException.class, () ->
                uniqueModuleList.add(CS2103T));
    }

    @Test
    public void setGroup_nullTargetModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueModuleList.setModule(null, CS2103T));
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueModuleList.setModule(CS2103T, null));
    }

    @Test
    public void setModule_targetModuleNotInList_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () ->
                uniqueModuleList.setModule(CS3243, CS3243));
    }

    @Test
    public void setModule_editedModuleIsSameModule_success() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModule(CS2103T, CS2103T);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS2103T);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasSameIdentity_success() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModule(CS2103T, CS2103T_COPY);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS2103T_COPY);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.setModule(CS2103T, CS3243);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS3243);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.remove(null));
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.remove(CS2103T));
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(CS3243);
        uniqueModuleList.remove(CS3243);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_nullUniqueModuleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(null, CS2103T));
    }

    @Test
    public void setModules_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(CS2103T);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS3243);
        uniqueModuleList.setModules(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueModuleList.setModules((List<Module>) null));
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(CS3243);
        List<Module> groupList = Collections.singletonList(CS2103T);
        uniqueModuleList.setModules(groupList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS2103T);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_listWithDuplicateModules_throwsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(CS2103T, CS2103T);
        assertThrows(DuplicateModuleException.class, () ->
                uniqueModuleList.setModules(listWithDuplicateModules));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueModuleList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void size() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.add(CS3243);
        uniqueModuleList.add(NO_GROUPS);
        assertEquals(3, uniqueModuleList.size());
    }

    @Test
    public void get() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.add(CS3243);
        uniqueModuleList.add(NO_GROUPS);
        assertEquals(uniqueModuleList.get(0), CS2103T);
        assertEquals(uniqueModuleList.get(1), CS3243);
        assertEquals(uniqueModuleList.get(2), NO_GROUPS);
    }

    @Test
    public void getUsingGroupId() {
        uniqueModuleList.add(CS2103T);
        uniqueModuleList.add(CS3243);
        uniqueModuleList.add(NO_GROUPS);
        assertEquals(uniqueModuleList.getModule("CS2103T"), CS2103T);
        assertEquals(uniqueModuleList.getModule("CS3243"), CS3243);
        assertEquals(uniqueModuleList.getModule("CS2100"), NO_GROUPS);
    }

}
