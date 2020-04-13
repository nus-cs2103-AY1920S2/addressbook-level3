package nasa.model;

import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_NAME_CS2030;
import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalDeadlines.CS2103T_DEADLINE;
import static nasa.testutil.TypicalEvents.CORRECT_EVENT;
import static nasa.testutil.TypicalModules.CS2103T;
import static nasa.testutil.TypicalModules.CS2106;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;
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

import nasa.model.module.Module;
import nasa.model.module.UniqueModuleList;
import nasa.model.module.exceptions.DuplicateModuleException;
import nasa.testutil.ModuleBuilder;


class NasaBookTest {
    private final NasaBook nasaBook = new NasaBook();
    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), nasaBook.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nasaBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNasaBook_replacesData() {
        NasaBook newData = getTypicalNasaBook();
        nasaBook.resetData(newData);
        assertEquals(newData, nasaBook);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two Modules with the same identity fields"
        Module editedcs2103t = new ModuleBuilder().withCode("CS2103T").withName("SOFTWARE ENGINEERING")
                .build();
        List<Module> newModules = Arrays.asList(CS2103T, editedcs2103t);
        NasaBookStub newData = new NasaBookStub(newModules);

        assertThrows(DuplicateModuleException.class, () -> nasaBook.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> nasaBook.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInNasaBook_returnsFalse() {
        assertFalse(nasaBook.hasModule(CS2103T.getModuleCode()));
    }

    @Test
    public void hasModule_moduleInNasaBook_returnsTrue() {
        nasaBook.addModule(CS2103T);
        assertTrue(nasaBook.hasModule(CS2103T.getModuleCode()));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInNasaBook_returnsTrue() {
        nasaBook.addModule(CS2103T);
        Module editedcs2103t = new ModuleBuilder(CS2103T).withName(VALID_MODULE_NAME_CS2030)
                .build();
        assertTrue(nasaBook.hasModule(editedcs2103t.getModuleCode()));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> nasaBook.getModuleList().remove(0));
    }

    @Test
    void addModule() {
        nasaBook.addModule(CS2103T);
        assertTrue(nasaBook.hasModule(CS2103T.getModuleCode()));
    }

    @Test
    void addEvent() {
        nasaBook.addModule(CS2103T);
        nasaBook.addEvent(CS2103T.getModuleCode(), CORRECT_EVENT);
        assertTrue(nasaBook.hasActivity(CS2103T.getModuleCode(), CORRECT_EVENT));
    }

    @Test
    void addDeadline() {
        nasaBook.addModule(CS2103T);
        nasaBook.addDeadline(CS2103T.getModuleCode(), CS2103T_DEADLINE);
        assertTrue(nasaBook.hasActivity(CS2103T.getModuleCode(), CS2103T_DEADLINE));
    }

    @Test
    void removeDeadline() {
        nasaBook.addModule(CS2103T);
        nasaBook.addDeadline(CS2103T.getModuleCode(), CS2103T_DEADLINE);

        nasaBook.removeDeadline(CS2103T.getModuleCode(), CS2103T_DEADLINE);
        assertFalse(nasaBook.hasActivity(CS2103T.getModuleCode(), CS2103T_DEADLINE));
    }

    @Test
    void removeEvent() {
        NasaBook book = new NasaBook();
        book.addModule(CS2106);
        book.addEvent(CS2106.getModuleCode(), CORRECT_EVENT);
        book.removeEvent(CS2106.getModuleCode(), CORRECT_EVENT);
        assertFalse(book.hasActivity(CS2106.getModuleCode(), CORRECT_EVENT));
    }

    /**
     * A stub ReadOnlyNasaBook whose Modules list can violate interface constraints.
     */
    private static class NasaBookStub implements ReadOnlyNasaBook {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();

        NasaBookStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override
        public UniqueModuleList getUniqueModuleList() { // stub
            return new UniqueModuleList();
        }

        @Override
        public ObservableList<Module> getDeepCopyList() {
            return modules;
        }
    }

}
