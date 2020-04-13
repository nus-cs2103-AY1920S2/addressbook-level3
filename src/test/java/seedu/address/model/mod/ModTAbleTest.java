package seedu.address.model.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMods.CS2040C_ESSENTIALFILLED;
import static seedu.address.testutil.TypicalMods.CS2103;
import static seedu.address.testutil.TypicalMods.getTypicalModTAble;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.mod.exceptions.DuplicateModException;
import seedu.address.model.mod.exceptions.ModNotFoundException;
import seedu.address.testutil.ModBuilder;

public class ModTAbleTest {

    private final ModTAble modTAble = new ModTAble();

    @Test
    public void constructor_noModsInModTAble_emptyList() {
        assertEquals(Collections.emptyList(), modTAble.getAllMods());
    }

    @Test
    public void constructor_list_sameList() {
        ModTAble modTAble1 = new ModTAble(Collections.singletonList(CS2103));
        assertEquals(Collections.singletonList(CS2103), modTAble1.getAllMods());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modTAble.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModTAble_replacesData() {
        ModTAble newData = getTypicalModTAble();
        modTAble.resetData(newData);
        assertEquals(newData, modTAble);
    }

    @Test
    public void resetData_withDuplicateMods_throwsDuplicateModException() {
        // Two mods with the same identity fields
        Mod editedCs2103 = new ModBuilder(CS2103).withModNote("New Note").build();
        List<Mod> newMods = Arrays.asList(CS2103, editedCs2103);
        ModStub newData = new ModStub(newMods);

        assertThrows(DuplicateModException.class, () -> modTAble.resetData(newData));
    }

    @Test
    public void hasMod_nullMod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modTAble.hasMod(null));
    }

    @Test
    public void hasMod_modNotInModTAble_returnsFalse() {
        assertFalse(modTAble.hasMod(CS2103));
    }

    @Test
    public void hasMod_modInModTAble_returnsTrue() {
        modTAble.addMod(CS2103);
        assertTrue(modTAble.hasMod(CS2103));
    }

    @Test
    public void hasMod_modWithSameIdentityFieldsInModTAble_returnsTrue() {
        modTAble.addMod(CS2103);
        Mod editedCs2103 = new ModBuilder(CS2103).withModNote("Changed Note").build();
        assertTrue(modTAble.hasMod(editedCs2103));
    }

    @Test
    public void removeMod_modInModTAble() {
        Mod emptyMod = new Mod(new Mod(new ModCode("TA8135"), "TAble"),
            "Use the viewModInfo command on a module to view it!");

        modTAble.addMod(CS2103);
        modTAble.setViewedModSingletonList(CS2103);
        modTAble.removeMod(CS2103);
        ModTAble expectedModTAble = new ModTAble();
        assertEquals(expectedModTAble, modTAble);
        assertEquals(emptyMod, modTAble.getViewedModSingletonList().get(0));
    }

    @Test
    public void setMod_modNotInModTAble() {
        assertThrows(ModNotFoundException.class, () -> modTAble.setMod(CS2103, CS2103));
    }

    @Test
    public void setMod_modInModTAble_success() {
        modTAble.addMod(CS2103);
        modTAble.setMod(CS2103, CS2040C_ESSENTIALFILLED);
        assertTrue(modTAble.hasMod(CS2040C_ESSENTIALFILLED));
        assertFalse(modTAble.hasMod(CS2103));
    }

    @Test
    public void setMod_changeModInModTAble_success() {
        modTAble.addMod(CS2103);
        modTAble.setViewedModSingletonList(CS2103);
        Mod expectedMod = new ModBuilder(CS2103).withModNote("Changed Note").build();
        modTAble.setMod(CS2103, expectedMod);
        assertTrue(modTAble.hasMod(CS2103));
        assertEquals(expectedMod, modTAble.getViewedModSingletonList().get(0));
    }

    @Test
    public void setViewedModSingletonList_modNotInModTAble_error() {
        assertThrows(AssertionError.class, () -> modTAble.setViewedModSingletonList(CS2103));
    }

    @Test
    public void setViewedModSingletonList_similarModInModTAble_displayModInModTAble() {
        modTAble.addMod(CS2103);
        Mod editedMod = new ModBuilder(CS2103).withModNote("Changed Mod").build();
        modTAble.setViewedModSingletonList(editedMod);
        ObservableList<Mod> expectedModSingletonList = FXCollections.singletonObservableList(CS2103);
        assertEquals(expectedModSingletonList, modTAble.getViewedModSingletonList());
    }

    @Test
    public void getViewedModSingletonList_noModSet_blankMod() {
        Mod emptyMod = new Mod(new Mod(new ModCode("TA8135"), "TAble"),
            "Use the viewModInfo command on a module to view it!");
        ObservableList<Mod> expectedModSingletonList = FXCollections.singletonObservableList(emptyMod);
        assertEquals(expectedModSingletonList, modTAble.getViewedModSingletonList());
    }

    @Test
    public void getViewedModSingletonList_oneModSet_correctMod() {
        modTAble.addMod(CS2103);
        modTAble.setViewedModSingletonList(CS2103);
        ObservableList<Mod> expectedModSingletonList = FXCollections.singletonObservableList(CS2103);
        assertEquals(expectedModSingletonList, modTAble.getViewedModSingletonList());
    }

    @Test
    public void getModList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modTAble.getAllMods().remove(0));
    }

    /**
     * A stub ReadOnlyStudent whose students list can violate interface constraints.
     */
    private static class ModStub implements ReadOnlyMod {
        private final ObservableList<Mod> mods = FXCollections.observableArrayList();

        ModStub(Collection<Mod> mods) {
            this.mods.setAll(mods);
        }

        @Override
        public ObservableList<Mod> getAllMods() {
            return mods;
        }
    }

}
