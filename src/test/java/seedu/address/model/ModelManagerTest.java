package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new VersionedAddressBook(), new VersionedAddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.commitAddressBook();
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void undoAddressBook_addPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
    }

    @Test
    public void undoAddressBook_deletePerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.deletePerson(ALICE);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
    }

    @Test
    public void undoAddressBook_setPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Person editedAPerson = new PersonBuilder(ALICE).withName("a l i c e").build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.setPerson(ALICE, editedAPerson);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
    }

    @Test
    public void undoAddressBook_pinPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.pinPerson(ALICE);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
    }

    @Test
    public void redoAddressBook_addPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
    }

    @Test
    public void redoAddressBook_deletePerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.deletePerson(ALICE);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
    }

    @Test
    public void redoAddressBook_setPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Person editedAPerson = new PersonBuilder(ALICE).withName("a l i c e").build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.setPerson(ALICE, editedAPerson);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
    }

    @Test
    public void redoAddressBook_pinPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.pinPerson(ALICE);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
    }

    @Test
    public void updateFilteredPersonList_undo_successs() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
    }

    @Test
    public void undoAddressBook_noPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.undoAddressBook());
    }

    @Test
    public void undoAddressBook_onePreviousCommand_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.undoAddressBook());
    }

    @Test
    public void undoAddressBook_manyPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.deletePerson(CARL);
        modelManager.commitAddressBook();
        modelManager.addPerson(BENSON);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.undoAddressBook();
        modelManager.undoAddressBook();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.undoAddressBook());
    }

    @Test
    public void redoAddressBook_noPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.redoAddressBook());
    }

    @Test
    public void redoAddressBook_onePreviousCommand_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.redoAddressBook());
    }

    @Test
    public void redoAddressBook_manyPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.deletePerson(CARL);
        modelManager.commitAddressBook();
        modelManager.addPerson(BENSON);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.undoAddressBook();
        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
        modelManager.redoAddressBook();
        modelManager.redoAddressBook();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.redoAddressBook());
    }

    @Test
    public void addressBookHasUndo_hasUndo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        assertTrue(modelManager.addressBookHasUndo());
    }

    @Test
    public void addressBookHasUndo_hasNoUndo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertFalse(modelManager.addressBookHasUndo());

        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        assertFalse(modelManager.addressBookHasUndo());

    }

    @Test
    public void addressBookHasRedo_hasRedo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        assertTrue(modelManager.addressBookHasRedo());
    }

    @Test
    public void addressBookHasRedo_hasNoRedo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertFalse(modelManager.addressBookHasRedo());

        modelManager.addPerson(CARL);
        modelManager.commitAddressBook();
        assertFalse(modelManager.addressBookHasRedo());

        modelManager.undoAddressBook();
        modelManager.redoAddressBook();
        assertFalse(modelManager.addressBookHasRedo());

        // test if removed ahead
        modelManager.addPerson(ALICE);
        modelManager.commitAddressBook();
        modelManager.addPerson(DANIEL);
        modelManager.commitAddressBook();
        modelManager.undoAddressBook();
        modelManager.undoAddressBook();
        modelManager.addPerson(BENSON);
        modelManager.commitAddressBook();
        assertFalse(modelManager.addressBookHasRedo());


    }

    @Test
    public void getFilteredPersonList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void updateSortedPersonList_validPrefix_updatesListCorrectly() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.updateSortedPersonList("n/");
        modelManager.commitAddressBook();
        ObservableList<Person> filteredList = modelManager.getFilteredPersonList();
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(ALICE));
        assertTrue(filteredList.contains(BENSON));
    }

    @Test
    public void updateSortedFilteredPersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateSortedFilteredPersonList(null));
    }

    @Test
    public void updateSortedFilteredPersonList_emptyList_noEffect() {
        AddressBook emptyAddressBook = new AddressBook();
        modelManager = new ModelManager(emptyAddressBook, new UserPrefs());
        modelManager.updateSortedFilteredPersonList("n/");
        ObservableList<Person> filteredList = modelManager.getFilteredPersonList();
        assertTrue(filteredList.isEmpty());
    }

    @Test
    public void updateSortedFilteredPersonList_multiplePersonsWithSamePrefix() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.updateSortedFilteredPersonList("n/");
        ObservableList<Person> filteredList = modelManager.getFilteredPersonList();
        assertTrue(filteredList.contains(ALICE));
        assertTrue(filteredList.contains(BENSON));
        assertEquals(ALICE, filteredList.get(0));
        assertEquals(BENSON, filteredList.get(1));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        modelManager.commitAddressBook();
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.commitAddressBook();

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
