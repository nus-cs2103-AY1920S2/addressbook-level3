package seedu.address.logic.commands.facilitator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilitatorAtIndex;
import static seedu.address.testutil.TypicalFacilitators.IDA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.facilitator.FacilEditCommand.EditFacilitatorDescriptor;
import seedu.address.model.ModManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.testutil.EditFacilitatorDescriptorBuilder;
import seedu.address.testutil.FacilitatorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FacilEditCommand}.
 */
public class FacilEditCommandTest {

    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListWithIndex_success() {
        Facilitator editedFacilitator = new FacilitatorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withOffice(VALID_OFFICE_BOB).build();
        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(editedFacilitator).build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(FacilEditCommand.MESSAGE_EDIT_FACILITATOR_SUCCESS, editedFacilitator);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(model.getFilteredFacilitatorList().get(0), editedFacilitator);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListWithName_success() {
        Facilitator editedFacilitator = new FacilitatorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withOffice(VALID_OFFICE_BOB).build();
        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(editedFacilitator).build();
        FacilEditCommand editCommand = new FacilEditCommand(editedFacilitator.getName(), descriptor);

        String expectedMessage = String.format(FacilEditCommand.MESSAGE_EDIT_FACILITATOR_SUCCESS, editedFacilitator);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(model.getFilteredFacilitatorList().get(0), editedFacilitator);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListWithIndex_success() {
        Index indexLastFacilitator = Index.fromOneBased(model.getFilteredFacilitatorList().size());
        Facilitator lastFacilitator = model.getFilteredFacilitatorList().get(indexLastFacilitator.getZeroBased());

        FacilitatorBuilder facilitatorInList = new FacilitatorBuilder(lastFacilitator);
        Facilitator editedFacilitator = facilitatorInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2103T).build();

        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withModuleCodes(VALID_MODULE_CODE_CS2103T).build();
        FacilEditCommand editCommand = new FacilEditCommand(indexLastFacilitator, descriptor);

        String expectedMessage = String.format(FacilEditCommand.MESSAGE_EDIT_FACILITATOR_SUCCESS, editedFacilitator);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(lastFacilitator, editedFacilitator);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListWithName_success() {
        Index indexLastFacilitator = Index.fromOneBased(model.getFilteredFacilitatorList().size());
        Facilitator lastFacilitator = model.getFilteredFacilitatorList().get(indexLastFacilitator.getZeroBased());

        FacilitatorBuilder facilitatorInList = new FacilitatorBuilder(lastFacilitator);
        Facilitator editedFacilitator = facilitatorInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2103T).build();

        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withModuleCodes(VALID_MODULE_CODE_CS2103T).build();
        FacilEditCommand editCommand = new FacilEditCommand(lastFacilitator.getName(), descriptor);

        String expectedMessage = String.format(FacilEditCommand.MESSAGE_EDIT_FACILITATOR_SUCCESS, editedFacilitator);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(lastFacilitator, editedFacilitator);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFacilitatorAtIndex(model, INDEX_FIRST);

        Facilitator facilitatorInFilteredList = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST.getZeroBased());
        Facilitator editedFacilitator = new FacilitatorBuilder(facilitatorInFilteredList).withName(VALID_NAME_BOB)
                .build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST,
                new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(FacilEditCommand.MESSAGE_EDIT_FACILITATOR_SUCCESS, editedFacilitator);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(model.getFilteredFacilitatorList().get(0), editedFacilitator);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_partialName_success() {
        Name name = new Name("Alice");
        Facilitator editedFacilitator = new FacilitatorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withOffice(VALID_OFFICE_BOB).build();
        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(editedFacilitator).build();
        FacilEditCommand editCommand = new FacilEditCommand(name, descriptor);

        ArrayList<Facilitator> fetch = new ArrayList<>();
        fetch.add(model.getFilteredFacilitatorList().get(0));
        StringBuilder builder = new StringBuilder(
                String.format(Messages.MESSAGE_PARTIAL_FACILITATOR_NAME_MATCHING_FOUND, name));
        fetch.forEach(x -> builder.append("  ").append(x.getName().toString()).append('\n'));
        builder.append(Messages.MESSAGE_ASK_TO_CONFIRM_FACILITATOR);

        String expectedMessage = builder.toString();

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.PROMPTING, model);
    }

    @Test
    public void execute_invalidFacilitatorIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFacilitatorList().size() + 1);
        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        FacilEditCommand editCommand = new FacilEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of facilitator list
     */
    @Test
    public void execute_invalidFacilitatorIndexFilteredList_failure() {
        showFacilitatorAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getModManager().getFacilitatorList().size());

        FacilEditCommand editCommand = new FacilEditCommand(outOfBoundIndex,
                new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_facilitatorNameNotFound_failure() {
        Name name = new Name(VALID_NAME_AMY);
        FacilEditCommand.EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        FacilEditCommand editCommand = new FacilEditCommand(name, descriptor);

        assertCommandFailure(editCommand, model, String.format(Messages.MESSAGE_FACILITATOR_NOT_FOUND, name));
    }

    @Test
    public void execute_duplicateFacilitatorUnfilteredList_failure() {
        Facilitator firstFacilitator = model.getFilteredFacilitatorList().get(INDEX_FIRST.getZeroBased());
        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(firstFacilitator).build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, FacilEditCommand.MESSAGE_DUPLICATE_FACILITATOR);
    }

    @Test
    public void execute_duplicateFacilitatorFilteredList_failure() {
        showFacilitatorAtIndex(model, INDEX_FIRST);

        // edit facilitator in filtered list into a duplicate in mod manager
        Facilitator facilitatorInList = model.getModManager().getFacilitatorList().get(INDEX_SECOND
                .getZeroBased());
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST,
                new EditFacilitatorDescriptorBuilder(facilitatorInList).build());

        assertCommandFailure(editCommand, model, FacilEditCommand.MESSAGE_DUPLICATE_FACILITATOR);
    }

    @Test
    public void execute_allOptionalFieldsDeletedUnfilteredList_failure() {
        Facilitator editedFacilitator = new FacilitatorBuilder().build();
        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(editedFacilitator).build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST, descriptor);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(model.getFilteredFacilitatorList().get(0), editedFacilitator);

        assertCommandFailure(editCommand, model, FacilEditCommand.MESSAGE_ALL_OPTIONAL_FIELDS_DELETED);
    }

    @Test
    public void execute_allOptionalFieldsDeletedFilteredList_failure() {
        showFacilitatorAtIndex(model, INDEX_FIRST);

        Facilitator facilitatorInFilteredList = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST.getZeroBased());
        Facilitator editedFacilitator = new FacilitatorBuilder(facilitatorInFilteredList).withPhone(null)
                .withEmail(null).withOffice(null).withModuleCodes().build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST,
                new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(null)
                        .withEmail(null).withOffice(null).withModuleCodes().build());

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setFacilitator(model.getFilteredFacilitatorList().get(0), editedFacilitator);

        assertCommandFailure(editCommand, model, FacilEditCommand.MESSAGE_ALL_OPTIONAL_FIELDS_DELETED);
    }

    @Test
    public void execute_moduleDoesNotExistUnfilteredList_failure() {
        Facilitator editedFacilitator = new FacilitatorBuilder(IDA).withModuleCodes("CS1231").build();
        EditFacilitatorDescriptor descriptor = new EditFacilitatorDescriptorBuilder(editedFacilitator).build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model,
                String.format(FacilEditCommand.MESSAGE_MODULE_DOES_NOT_EXIST, "CS1231"));
    }

    @Test
    public void execute_moduleDoesNotExistFilteredList_failure() {
        showFacilitatorAtIndex(model, INDEX_FIRST);

        Facilitator editedFacilitator = new FacilitatorBuilder(IDA).withModuleCodes("CS1231").build();
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST,
                new EditFacilitatorDescriptorBuilder(editedFacilitator).build());

        assertCommandFailure(editCommand, model,
                String.format(FacilEditCommand.MESSAGE_MODULE_DOES_NOT_EXIST, "CS1231"));
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredListWithIndex_failure() {
        FacilEditCommand editCommand = new FacilEditCommand(INDEX_FIRST,
                new FacilEditCommand.EditFacilitatorDescriptor());
        String expectedMessage = Messages.MESSAGE_NOT_EDITED;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredListWithName_failure() {
        Facilitator editedFacilitator = model.getFilteredFacilitatorList().get(INDEX_FIRST.getZeroBased());
        FacilEditCommand editCommand = new FacilEditCommand(editedFacilitator.getName(),
                new FacilEditCommand.EditFacilitatorDescriptor());
        String expectedMessage = Messages.MESSAGE_NOT_EDITED;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final FacilEditCommand standardIndexCommand = new FacilEditCommand(INDEX_FIRST, DESC_AMY);
        final FacilEditCommand standardNameCommand = new FacilEditCommand(new Name(VALID_NAME_BOB), DESC_AMY);

        // same values -> returns true
        EditFacilitatorDescriptor copyDescriptor = new EditFacilitatorDescriptor(DESC_AMY);
        FacilEditCommand commandWithSameIndexValues = new FacilEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardIndexCommand.equals(commandWithSameIndexValues));
        FacilEditCommand commandWithSameNameValues = new FacilEditCommand(new Name(VALID_NAME_BOB), copyDescriptor);
        assertTrue(standardNameCommand.equals(commandWithSameNameValues));

        // same object -> returns true
        assertTrue(standardIndexCommand.equals(standardIndexCommand));
        assertTrue(standardNameCommand.equals(standardNameCommand));

        // null -> returns false
        assertFalse(standardIndexCommand.equals(null));
        assertFalse(standardNameCommand.equals(null));

        // different types -> returns false
        assertFalse(standardIndexCommand.equals(new ClearCommand()));
        assertFalse(standardNameCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardIndexCommand.equals(new FacilEditCommand(INDEX_SECOND, DESC_AMY)));

        // different name -> returns false
        assertFalse(standardIndexCommand.equals(new FacilEditCommand(new Name(VALID_NAME_AMY), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardIndexCommand.equals(new FacilEditCommand(INDEX_FIRST, DESC_BOB)));
        assertFalse(standardNameCommand.equals(new FacilEditCommand(new Name(VALID_NAME_BOB), DESC_BOB)));
    }

}
