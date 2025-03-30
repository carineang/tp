package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds a past command input to the list of past command inputs.
     *
     * @param pastCommandInput The command input to be added.
     */
    void addPastCommandInput(String pastCommandInput);

    /**
     * Returns an unmodifiable ordered view of the list of past command inputs.
     * The most recently added input is at the front of the list.
     */
    ObservableList<String> getCommandInputHistoryList();

    /**
     * Pins a person in the address book to the top of the list.
     *
     * @param personToPin The person to be pinned in the address book.
     */
    void pinPerson(Person personToPin);

    /**
     * Unpins a person in the address book that was previously pinned.
     *
     * @param personToUnpin The person to be unpinned in the address book.
     */
    void unpinPerson(Person personToUnpin);

    /**
     * Updates the filtered and sorted list of persons based on the specified prefix attribute
     *
     * @param prefixes The prefix indicating the attribute to sort by.
     */
    void updateSortedFilteredPersonList(String... prefixes);

    /**
     * Saves the state of the address book
     * It is up to the user of this method to decide when to commit the address book
     */
    void commitAddressBook();

    /**
     * Restores the state of the address book to the last saved state
     */
    void undoAddressBook();

    /**
     * Restores the state of the address book to the last saved undone state
     */
    void redoAddressBook();

    /**
     * Checks if the address book has a state to undo
     */
    boolean addressBookHasUndo();

    /**
     * Checks if the address book has an undone state to redo
     */
    boolean addressBookHasRedo();
}
