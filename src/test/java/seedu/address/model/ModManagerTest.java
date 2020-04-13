package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.ALICE;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;
import static seedu.address.testutil.TypicalModules.CS2103T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.UniqueFacilitatorList;
import seedu.address.model.facilitator.exceptions.DuplicateFacilitatorException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.task.Task;
import seedu.address.testutil.FacilitatorBuilder;
import seedu.address.testutil.ModuleBuilder;

public class ModManagerTest {

    private final ModManager modManager = new ModManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), modManager.getFacilitatorList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModManager_replacesData() {
        ModManager newData = getTypicalModManager();
        modManager.resetData(newData);
        assertEquals(newData, modManager);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two modules with the same identity fields
        Module otherModule = new ModuleBuilder(CS2103T).withDescription(VALID_DESCRIPTION_CS2101).build();
        List<Module> newModules = Arrays.asList(CS2103T, otherModule);
        ModManagerStub newData = new ModManagerStub(newModules, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());

        assertThrows(DuplicateModuleException.class, () -> modManager.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateFacilitators_throwsDuplicateFacilitatorException() {
        // Two facilitators with the same identity fields
        Facilitator editedAlice = new FacilitatorBuilder(ALICE).withOffice(VALID_OFFICE_BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2103T).build();
        List<Facilitator> newFacilitators = Arrays.asList(ALICE, editedAlice);
        ModManagerStub newData = new ModManagerStub(new ArrayList<>(), newFacilitators, new ArrayList<>(),
                new ArrayList<>());

        assertThrows(DuplicateFacilitatorException.class, () -> modManager.resetData(newData));
    }

    @Test
    public void hasModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modManager.hasModuleCode(null));
    }

    @Test
    public void hasModuleCode_moduleCodeNotInModManager_returnsFalse() {
        assertFalse(modManager.hasModuleCode("CS2103T"));
    }

    @Test
    public void hasModuleCode_moduleCodeInModManager_returnsTrue() {
        modManager.addModule(CS2103T);
        assertTrue(modManager.hasModuleCode("CS2103T"));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInModManager_returnsFalse() {
        assertFalse(modManager.hasModule(CS2103T));
    }

    @Test
    public void hasModule_moduleInModManager_returnsTrue() {
        modManager.addModule(CS2103T);
        assertTrue(modManager.hasModule(CS2103T));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInModManager_returnsTrue() {
        modManager.addModule(CS2103T);
        Module editedModule = new ModuleBuilder().withDescription(VALID_DESCRIPTION_CS2101).build();
        assertTrue(modManager.hasModule(editedModule));
    }

    @Test
    public void findModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modManager.findModule(null));
    }

    @Test
    public void findModule_moduleNotInModManager_returnsOptionalEmpty() {
        assertEquals(Optional.empty(), modManager.findModule(new ModuleCode("CS2103T")));
    }

    @Test
    public void findModule_moduleInModManager_returnsModule() {
        modManager.addModule(CS2103T);
        assertEquals(Optional.of(CS2103T), modManager.findModule(new ModuleCode("CS2103T")));
    }

    @Test
    public void getModules_emptyList_success() {
        UniqueModuleList modules = new UniqueModuleList();
        assertEquals(modules.getModuleList(), modManager.getModules());
    }

    @Test
    public void getModules_nonEmptyList_success() {
        modManager.addModule(CS2103T);
        UniqueModuleList modules = new UniqueModuleList();
        modules.add(CS2103T);
        assertEquals(modules.getModuleList(), modManager.getModules());
    }

    @Test
    public void hasFacilitator_nullFacilitator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modManager.hasFacilitator(null));
    }

    @Test
    public void hasFacilitator_facilitatorNotInModManager_returnsFalse() {
        assertFalse(modManager.hasFacilitator(ALICE));
    }

    @Test
    public void hasFacilitator_facilitatorInModManager_returnsTrue() {
        modManager.addFacilitator(ALICE);
        assertTrue(modManager.hasFacilitator(ALICE));
    }

    @Test
    public void hasFacilitator_facilitatorWithSameIdentityFieldsInModManager_returnsTrue() {
        modManager.addFacilitator(ALICE);
        Facilitator editedAlice = new FacilitatorBuilder(ALICE).withOffice(VALID_OFFICE_BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2103T).build();
        assertTrue(modManager.hasFacilitator(editedAlice));
    }

    @Test
    public void getFacilitators_emptyList_success() {
        UniqueFacilitatorList facilitators = new UniqueFacilitatorList();
        assertEquals(facilitators.getFacilitatorList(), modManager.getFacilitators());
    }

    @Test
    public void getFacilitators_nonEmptyList_success() {
        modManager.addFacilitator(ALICE);
        UniqueFacilitatorList facilitators = new UniqueFacilitatorList();
        facilitators.add(ALICE);
        assertEquals(facilitators.getFacilitatorList(), modManager.getFacilitators());
    }

    @Test
    public void getModManager_emptyModManager_success() {
        assertEquals(new ModManager(), modManager);
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modManager.getModuleList().remove(0));
    }

    @Test
    public void getFacilitatorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modManager.getFacilitatorList().remove(0));
    }

    /**
     * A stub ReadOnlyModManager whose modules, facilitators, tasks and lessons list can violate interface constraints.
     */
    private static class ModManagerStub implements ReadOnlyModManager {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<Facilitator> facilitators = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private List<Lesson> lessons = new ArrayList<>();

        ModManagerStub(Collection<Module> modules, Collection<Facilitator> facilitators, Collection<Task> tasks,
                       List<Lesson> lessons) {
            this.modules.setAll(modules);
            this.facilitators.setAll(facilitators);
            this.tasks.setAll(tasks);
            this.lessons = lessons;
        }

        @Override
        public ObservableList<Facilitator> getFacilitatorList() {
            return facilitators;
        }

        /**
         * Returns an unmodifiable view of the tasks list.
         * This list will not contain any duplicate tasks.
         */
        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public List<Lesson> getLessonList() {
            return lessons;
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }

}
