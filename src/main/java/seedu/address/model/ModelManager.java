package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final InputHistory pastCommands;
    private final ObservableList<Person> personList;
    private final SortedList<Person> sortedFilteredPersons;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        pastCommands = new InputHistory();
        this.personList = addressBook.getPersonList();
        sortedFilteredPersons = new SortedList<>(filteredPersons);
    }

    /**
     * Initializes a ModelManager with an empty address book and user preferences.
     */
    public ModelManager() {
        this(new VersionedAddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    /**
     * Sets the user preferences to the specified userPrefs.
     *
     * @param userPrefs The new user preferences.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    /**
     * Returns the current user preferences.
     *
     * @return The current user preferences.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    /**
     * Returns the current GUI settings.
     *
     * @return The current GUI settings.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    /**
     * Sets the GUI settings to the specified gui settings.
     *
     * @param guiSettings The new GUI settings.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    /**
     * Returns the file path to the address book file.
     *
     * @return The address book file path.
     */
    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    /**
     * Sets the file path for the address book file.
     *
     * @param addressBookFilePath The new file path.
     */
    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    /**
     * Sets the address book to the specified AddressBook.
     *
     * @param addressBook The address book to set.
     */
    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    /**
     * Returns the current address book.
     *
     * @return The current address book.
     */
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * Returns true if a person with the same identity as person exists in the address book.
     *
     * @param person The person to check for.
     * @return True if the person exists, false otherwise.
     */
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    /**
     * Deletes the specified target person from the address book.
     *
     * @param target The person to delete.
     */
    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    /**
     * Adds the specified person to the address book.
     *
     * @param person The person to add.
     */
    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Replaces the target person with the edited person.
     *
     * @param target The person to be replaced.
     * @param editedPerson The person to replace with.
     */
    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void pinPerson(Person person) {
        addressBook.pinPerson(person);
    }

    @Override
    public void unpinPerson(Person person) {
        addressBook.unpinPerson(person);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedFilteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void addPastCommandInput(String rawCommandInput) {
        pastCommands.addInput(rawCommandInput);
    }

    @Override
    public ObservableList<String> getCommandInputHistoryList() {
        return pastCommands.getPastCommands();
    }

    //=========== Sorted Person List Accessors =============================================================

    /**
     * Updates the sorted and filtered person list based on the given prefix.The prefix is used to
     * filter the list of persons in the address book and sort the resulting filtered list.
     *
     * @param prefixes The string prefix used to filter and sort the person list.
     * @throws NullPointerException if prefix is null.
     */
    @Override
    public void updateSortedFilteredPersonList(String... prefixes) {
        requireNonNull(prefixes);
        addressBook.updateSortedList(prefixes);
    }

    @Override
    public void commitAddressBook() {
        addressBook.commit();
    }

    @Override
    public void undoAddressBook() {
        addressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        addressBook.redo();
    }

    @Override
    public boolean addressBookHasUndo() {
        return addressBook.hasUndo();
    }

    @Override
    public boolean addressBookHasRedo() {
        return addressBook.hasRedo();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                //&& filteredPersons.equals(otherModelManager.filteredPersons);
                && sortedFilteredPersons.equals(otherModelManager.sortedFilteredPersons);
    }
}
