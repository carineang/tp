package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class ViewNoteCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_unfilteredList_success() {

        Person personToView = model.getFilteredPersonList().get(0);

        ViewNoteCommand viewNoteCommand = new ViewNoteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewNoteCommand.MESSAGE_VIEW_NOTE_PERSON_SUCCESS,
                personToView.getNote().toString());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(viewNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewNoteCommand viewNoteCommand = new ViewNoteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewNoteCommand.MESSAGE_VIEW_NOTE_PERSON_SUCCESS,
                personToView.getNote().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(viewNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewNoteCommand viewNoteCommand = new ViewNoteCommand(outOfBoundIndex);

        assertCommandFailure(viewNoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Change note of a person in filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ViewNoteCommand viewNoteCommand = new ViewNoteCommand(outOfBoundIndex);

        assertCommandFailure(viewNoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_undoCommand_success() {
        Model blankModel = new ModelManager();

        // simulate an add command
        blankModel.addPerson(BENSON);
        blankModel.commitAddressBook();

        ViewNoteCommand viewNoteCommand = new ViewNoteCommand(INDEX_FIRST_PERSON);
        // should only undo viewNoteCommand
        assertDoesNotThrow(() -> viewNoteCommand.execute(blankModel));
        blankModel.undoAddressBook();
        // make sure the added person is still there
        assertDoesNotThrow(() -> viewNoteCommand.execute(blankModel));
        blankModel.undoAddressBook();

        // remove the add command
        assertDoesNotThrow(() -> blankModel.undoAddressBook());

        // No contact at index 1 so no note can be viewed, error should be thrown
        assertThrows(CommandException.class ,() -> viewNoteCommand.execute(blankModel));
    }

    @Test
    public void equals() {
        final ViewNoteCommand standardCommand = new ViewNoteCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        ViewNoteCommand commandWithSameValues = new ViewNoteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ViewNoteCommand(INDEX_SECOND_PERSON)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ViewNoteCommand viewNoteCommand = new ViewNoteCommand(index);
        String expected = ViewNoteCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, viewNoteCommand.toString());
    }
}
