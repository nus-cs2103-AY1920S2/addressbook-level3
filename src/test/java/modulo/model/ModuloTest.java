package modulo.model;

import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_CS2103;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static modulo.testutil.module.TypicalModules.CS2103;
import static modulo.testutil.module.TypicalModules.getTypicalModulo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modulo.model.event.Event;
import modulo.model.module.Module;
import modulo.model.module.exceptions.DuplicateModuleException;
import modulo.testutil.module.ModuleBuilder;

/**
 * Test class to test the functionality of the Modulo.
 */
public class ModuloTest {

    private final Modulo modulo = new Modulo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), modulo.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulo.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModulo_replacesData() {
        Modulo newData = getTypicalModulo();
        modulo.resetData(newData);
        assertEquals(newData, modulo);
    }

    @Test
    public void resetData_withDuplicateModule_throwsDuplicateModuleException() {
        // Two persons with the same identity fields
        Module editedCS2103 = new ModuleBuilder(CS2103).withModuleCode(VALID_CODE_CS2103)
                .build();
        List<Module> newModules = Arrays.asList(CS2103, editedCS2103);
        ModuloStub newData = new ModuloStub(newModules);

        assertThrows(DuplicateModuleException.class, () -> modulo.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulo.hasModule(null, null));
    }

    @Test
    public void hasModule_moduleNotInModulo_returnsFalse() {
        assertFalse(modulo.hasModule(CS2103.getModuleCode(), CS2103.getAcademicYear()));
    }

    @Test
    public void hasModule_moduleInModulo_returnsTrue() {
        modulo.addModuleFromStorage(CS2103);
        assertTrue(modulo.hasModule(CS2103.getModuleCode(), CS2103.getAcademicYear()));
    }

    @Test
    public void addModuleFromStorage_moduleWithEvents_eventsAdded() {
        Module duplicateCs2103 = new ModuleBuilder(CS2103).build();
        duplicateCs2103.addEvent(TUTORIAL_1);
        modulo.addModuleFromStorage(duplicateCs2103);
        Module newCs2103 = modulo.getModule(CS2103.getModuleCode(), CS2103.getAcademicYear()).orElseThrow();
        assertEquals(newCs2103.getEvents().size(), 1);
        Event newTutorial = newCs2103.getEvents().get(0);
        assertEquals(TUTORIAL_1, newTutorial);
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInModulo_returnsTrue() {
        modulo.addModuleFromStorage(CS2103);
        Module editedModule = new ModuleBuilder(CS2103).withModuleCode(VALID_CODE_CS2103)
                .withModuleName(VALID_NAME_CS2103)
                .build();
        assertTrue(modulo.hasModule(editedModule.getModuleCode(), editedModule.getAcademicYear()));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modulo.getModuleList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class ModuloStub implements ReadOnlyModulo {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        ModuloStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }

}
