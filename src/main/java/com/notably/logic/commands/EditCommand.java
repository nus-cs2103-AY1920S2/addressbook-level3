//package com.notably.logic.commands;
//
//import static com.notably.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static com.notably.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static com.notably.logic.parser.CliSyntax.PREFIX_NAME;
//import static com.notably.logic.parser.CliSyntax.PREFIX_PHONE;
//import static com.notably.logic.parser.CliSyntax.PREFIX_TAG;
//import static java.util.Objects.requireNonNull;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import com.notably.commons.core.index.Index;
//import com.notably.logic.commands.exceptions.CommandException;
//import com.notably.model.Model;
//import com.notably.model.tag.Tag;
//
///**
// * Edits the details of an existing person in the address book.
// */
//public class EditCommand extends Command {
//
//    public static final String COMMAND_WORD = "edit";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
//            + "by the index number used in the displayed person list. "
//            + "Existing values will be overwritten by the input values.\n"
//            + "Parameters: INDEX (must be a positive integer) "
//            + "[" + PREFIX_NAME + "NAME] "
//            + "[" + PREFIX_PHONE + "PHONE] "
//            + "[" + PREFIX_EMAIL + "EMAIL] "
//            + "[" + PREFIX_ADDRESS + "ADDRESS] "
//            + "[" + PREFIX_TAG + "TAG]...\n"
//            + "Example: " + COMMAND_WORD + " 1 "
//            + PREFIX_PHONE + "91234567 "
//            + PREFIX_EMAIL + "johndoe@example.com";
//
//    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
//    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
//    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
//
//    private final Index index;
//    private final EditPersonDescriptor editPersonDescriptor;
//
//    /**
//     * @param index of the person in the filtered person list to edit
//     * @param editPersonDescriptor details to edit the person with
//     */
//    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
//        requireNonNull(index);
//        requireNonNull(editPersonDescriptor);
//
//        this.index = index;
//        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
//    }
//
//    @Override
//    public void execute(Model model) throws CommandException {
//        requireNonNull(model);
//        List<Object> lastShownList = model.getFilteredPersonList();
//
//        if (index.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException("MESSAGE_INVALID_PERSON_DISPLAYED_INDEX");
//        }
//
//    }
//
//    /**
//     * Creates and returns a {@code Person} with the details of {@code personToEdit}
//     * edited with {@code editPersonDescriptor}.
//     */
//    private static void createEditedPerson(Object personToEdit, EditPersonDescriptor editPersonDescriptor) {
//        assert personToEdit != null;
//
//
//
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        // short circuit if same object
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof EditCommand)) {
//            return false;
//        }
//
//        // state check
//        EditCommand e = (EditCommand) other;
//        return index.equals(e.index)
//                && editPersonDescriptor.equals(e.editPersonDescriptor);
//    }
//
//    /**
//     * Stores the details to edit the person with. Each non-empty field value will replace the
//     * corresponding field value of the person.
//     */
//    public static class EditPersonDescriptor {
//
//        private Set<Tag> tags;
//
//        public EditPersonDescriptor() {}
//
//        /**
//         * Copy constructor.
//         * A defensive copy of {@code tags} is used internally.
//         */
//        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
//
//            setTags(toCopy.tags);
//        }
//
//        /**
//         * Returns true if at least one field is edited.
//         */
//        public void isAnyFieldEdited() {
//
//        }
//
//        public void setName(Object name) {
//        }
//
//        public void getName() {
//        }
//
//        public void setPhone(Object phone) {
//        }
//
//        public void getPhone() {
//        }
//
//        public void setEmail(Object email) {
//        }
//
//        public void getEmail() {
//        }
//
//        public void setAddress(Object address) {
//        }
//
//        public void getAddress() {
//        }
//
//        /**
//         * Sets {@code tags} to this object's {@code tags}.
//         * A defensive copy of {@code tags} is used internally.
//         */
//        public void setTags(Set<Tag> tags) {
//            this.tags = (tags != null) ? new HashSet<>(tags) : null;
//        }
//
//        /**
//         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
//         * if modification is attempted.
//         * Returns {@code Optional#empty()} if {@code tags} is null.
//         */
//        public Optional<Set<Tag>> getTags() {
//            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
//        }
//
//        @Override
//        public boolean equals(Object other) {
//            // short circuit if same object
//            if (other == this) {
//            }
//
//            // instanceof handles nulls
//            if (!(other instanceof EditPersonDescriptor)) {
//            }
//
//            // state check
//            EditPersonDescriptor e = (EditPersonDescriptor) other;
//            return true;
//
//
//        }
//    }
//}
