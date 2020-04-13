package seedu.address.model.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMods.CS2040C_ESSENTIALFILLED;
import static seedu.address.testutil.TypicalMods.CS2103;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.mod.exceptions.DuplicateModException;
import seedu.address.model.mod.exceptions.ModNotFoundException;
import seedu.address.testutil.ModBuilder;

public class UniqueModListTest {

    private final UniqueModList uniqueModList = new UniqueModList();

    @Test
    public void contains_nullMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.contains(null));
    }

    @Test
    public void contains_modNotInList_returnsFalse() {
        assertFalse(uniqueModList.contains(CS2103));
    }

    @Test
    public void contains_modInList_returnsTrue() {
        uniqueModList.add(CS2103);
        assertTrue(uniqueModList.contains(CS2103));
    }

    @Test
    public void contains_modWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModList.add(CS2103);
        Mod stubCs2103 = new ModBuilder(CS2103).withModName("Changed Name").build();
        assertTrue(uniqueModList.contains(stubCs2103));
    }

    @Test
    public void add_nullMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.add(null));
    }

    @Test
    public void add_duplicateMod_throwsDuplicateModException() {
        uniqueModList.add(CS2103);
        assertThrows(DuplicateModException.class, () -> uniqueModList.add(CS2103));
    }

    @Test
    public void setMod_nullTargetMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.setMod(null, CS2103));
    }

    @Test
    public void setMod_nullEditedMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.setMod(CS2103, null));
    }

    @Test
    public void setMod_targetModNotInList_throwsModNotFoundException() {
        assertThrows(ModNotFoundException.class, () -> uniqueModList.setMod(CS2103, CS2103));
    }

    @Test
    public void setMod_editedModIsSameMod_success() {
        uniqueModList.add(CS2103);
        uniqueModList.setMod(CS2103, CS2103);
        UniqueModList expectedUniqueModList = new UniqueModList();
        expectedUniqueModList.add(CS2103);
        assertEquals(expectedUniqueModList, uniqueModList);
    }

    @Test
    public void setMod_editedModHasSameIdentity_success() {
        uniqueModList.add(CS2103);
        Mod editedCs2103 = new ModBuilder(CS2103).withModName("Changed Name").build();
        uniqueModList.setMod(CS2103, editedCs2103);
        UniqueModList expectedUniqueModList = new UniqueModList();
        expectedUniqueModList.add(editedCs2103);
        assertEquals(expectedUniqueModList, uniqueModList);
    }

    @Test
    public void setMod_editedModHasDifferentIdentity_success() {
        uniqueModList.add(CS2103);
        uniqueModList.setMod(CS2103, CS2040C_ESSENTIALFILLED);
        UniqueModList expectedUniqueModList = new UniqueModList();
        expectedUniqueModList.add(CS2040C_ESSENTIALFILLED);
        assertEquals(expectedUniqueModList, uniqueModList);
    }

    @Test
    public void setMod_editedModHasNonUniqueIdentity_throwsDuplicateModException() {
        uniqueModList.add(CS2103);
        uniqueModList.add(CS2040C_ESSENTIALFILLED);
        assertThrows(DuplicateModException.class, () -> uniqueModList.setMod(CS2103, CS2040C_ESSENTIALFILLED));
    }

    @Test
    public void remove_nullMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.remove(null));
    }

    @Test
    public void remove_modDoesNotExist_throwsModNotFoundException() {
        assertThrows(ModNotFoundException.class, () -> uniqueModList.remove(CS2103));
    }

    @Test
    public void remove_existingMod_removesMod() {
        uniqueModList.add(CS2103);
        uniqueModList.remove(CS2103);
        UniqueModList expectedUniqueModList = new UniqueModList();
        assertEquals(expectedUniqueModList, uniqueModList);
    }

    @Test
    public void setMods_nullUniqueModList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.setMods((UniqueModList) null));
    }

    @Test
    public void setMods_uniqueModList_replacesOwnListWithProvidedUniqueModList() {
        uniqueModList.add(CS2103);
        UniqueModList expectedUniqueModList = new UniqueModList();
        expectedUniqueModList.add(CS2040C_ESSENTIALFILLED);
        uniqueModList.setMods(expectedUniqueModList);
        assertEquals(expectedUniqueModList, uniqueModList);
    }

    @Test
    public void setMods_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModList.setMods((List<Mod>) null));
    }

    @Test
    public void setMods_list_replacesOwnListWithProvidedList() {
        uniqueModList.add(CS2103);
        List<Mod> modsList = Collections.singletonList(CS2040C_ESSENTIALFILLED);
        uniqueModList.setMods(modsList);
        UniqueModList expectedUniqueModList = new UniqueModList();
        expectedUniqueModList.add(CS2040C_ESSENTIALFILLED);
        assertEquals(expectedUniqueModList, uniqueModList);
    }

    @Test
    public void setMods_listWithDuplicateMods_throwsDuplicateModException() {
        List<Mod> listWithDuplicateMods = Arrays.asList(CS2103, CS2103);
        assertThrows(DuplicateModException.class, () -> uniqueModList.setMods(listWithDuplicateMods));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueModList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void getAllMods() {
        uniqueModList.add(CS2103);
        ObservableList<Mod> expectedObservableList = FXCollections.singletonObservableList(CS2103);
        assertEquals(expectedObservableList, uniqueModList.getAllMods());
    }
}
