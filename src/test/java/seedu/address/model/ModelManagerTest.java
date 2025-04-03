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
        modelManager.commit();
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void undo_addPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.undo();
    }

    @Test
    public void undo_deletePerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.deletePerson(ALICE);
        modelManager.commit();
        modelManager.undo();
    }

    @Test
    public void undo_setPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Person editedAPerson = new PersonBuilder(ALICE).withName("a l i c e").build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.setPerson(ALICE, editedAPerson);
        modelManager.commit();
        modelManager.undo();
    }

    @Test
    public void undo_pinPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.pinPerson(ALICE);
        modelManager.commit();
        modelManager.undo();
    }

    @Test
    public void redo_addPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.undo();
        modelManager.redo();
    }

    @Test
    public void redo_deletePerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.deletePerson(ALICE);
        modelManager.commit();
        modelManager.undo();
        modelManager.redo();
    }

    @Test
    public void redo_setPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Person editedAPerson = new PersonBuilder(ALICE).withName("a l i c e").build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.setPerson(ALICE, editedAPerson);
        modelManager.commit();
        modelManager.undo();
        modelManager.redo();
    }

    @Test
    public void redo_pinPerson_success() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.pinPerson(ALICE);
        modelManager.commit();
        modelManager.undo();
        modelManager.redo();
    }

    @Test
    public void updateFilteredPersonList_undo_successs() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.commit();
        modelManager.undo();
    }

    @Test
    public void undo_noPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.undo());
    }

    @Test
    public void undo_onePreviousCommand_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.undo();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.undo());
    }

    @Test
    public void undo_manyPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.deletePerson(CARL);
        modelManager.commit();
        modelManager.addPerson(BENSON);
        modelManager.commit();
        modelManager.undo();
        modelManager.undo();
        modelManager.undo();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.undo());
    }

    @Test
    public void redo_noPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.redo());
    }

    @Test
    public void redo_onePreviousCommand_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.undo();
        modelManager.redo();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.redo());
    }

    @Test
    public void redo_manyPreviousCommands_failure() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.deletePerson(CARL);
        modelManager.commit();
        modelManager.addPerson(BENSON);
        modelManager.commit();
        modelManager.undo();
        modelManager.undo();
        modelManager.undo();
        modelManager.redo();
        modelManager.redo();
        modelManager.redo();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.redo());
    }

    @Test
    public void hasUndo_hasUndo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        assertTrue(modelManager.hasUndo());
    }

    @Test
    public void hasUndo_hasNoUndo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertFalse(modelManager.hasUndo());

        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.undo();
        assertFalse(modelManager.hasUndo());

    }

    @Test
    public void hasRedo_hasRedo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.addPerson(CARL);
        modelManager.commit();
        modelManager.undo();
        assertTrue(modelManager.hasRedo());
    }

    @Test
    public void hasRedo_hasNoRedo_success() {
        AddressBook addressBook = new AddressBookBuilder().build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        assertFalse(modelManager.hasRedo());

        modelManager.addPerson(CARL);
        modelManager.commit();
        assertFalse(modelManager.hasRedo());

        modelManager.undo();
        modelManager.redo();
        assertFalse(modelManager.hasRedo());

        // test if removed ahead
        modelManager.addPerson(ALICE);
        modelManager.commit();
        modelManager.addPerson(DANIEL);
        modelManager.commit();
        modelManager.undo();
        modelManager.undo();
        modelManager.addPerson(BENSON);
        modelManager.commit();
        assertFalse(modelManager.hasRedo());


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
        modelManager.commit();
        ObservableList<Person> filteredList = modelManager.getFilteredPersonList();
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(ALICE));
        assertTrue(filteredList.contains(BENSON));
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

        // different predicate -> returns false
        ModelManager modelManagerCopy2 = new ModelManager(addressBook, userPrefs);
        modelManagerCopy2.updateFilteredPersonList(p -> false);
        assertFalse(modelManager.equals(modelManagerCopy2));

        // different state pointer -> returns false
        ModelManager modelManagerCopy3 = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy4 = new ModelManager(addressBook, userPrefs);
        modelManagerCopy3.commit();
        modelManagerCopy3.undo();
        modelManagerCopy4.commit();
        assertFalse(modelManagerCopy4.equals(modelManagerCopy3));

        // different state history -> returns false
        ModelManager modelManagerCopy5 = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy6 = new ModelManager(addressBook, userPrefs);
        modelManagerCopy6.commit();
        assertFalse(modelManagerCopy5.equals(modelManagerCopy6));
        modelManagerCopy6.addPerson(DANIEL);
        modelManagerCopy6.commit();
        modelManagerCopy5.commit();
        modelManagerCopy5.commit();
        assertFalse(modelManagerCopy5.equals(modelManagerCopy6));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        modelManager.commit();
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.commit();

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
