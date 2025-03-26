package seedu.address.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This uses the proposed implementation of undo/redo
 * given in @see <a href="https://se-education.org/addressbook-level3/DeveloperGuide.html">AB3 Develop Guide</a>
 */
public class VersionedAddressBook extends AddressBook {

    // points to the state in the list
    private int currentStatePointer;
    // Stores the state of the AddressBook as a list
    private ArrayList<ReadOnlyAddressBook> addressBookStateList;

    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);

        addressBookStateList = new ArrayList<>();
        // add the initial state
        addressBookStateList.add(toBeCopied);

        // current state pointer points to the inital state
        currentStatePointer = 0;

    }

    public VersionedAddressBook() {
        this(new AddressBook());
    }

    public void commit() {

        // remove all states after the current
        removeAheadCurrent();

        // adds the non versioned Address book that contains the person list
        addressBookStateList.add(new AddressBook(this));

        // increment the pointer
        currentStatePointer += 1;
    }

    public void undo() throws IndexOutOfBoundsException {
        // restores last state

        // must have a last state to be undoable
        // this is the responsibility of the person using this function
        // throw unchecked error if not ensured
        if (currentStatePointer - 1 < 0) {
            throw new IndexOutOfBoundsException();
        }

        // decrement pointer
        currentStatePointer -= 1;

        // get the last address book and set the current address book to the last one
        ReadOnlyAddressBook lastBook = addressBookStateList.get(currentStatePointer);
        resetData(lastBook);
    }

    public void redo() throws IndexOutOfBoundsException {
        // restores the state ahead

        // must have a undone state to be able to be redone
        // this is the responsibility of the person using this function
        // throw unchecked error if not ensured
        if (currentStatePointer + 1 >= addressBookStateList.size()) {
            throw new IndexOutOfBoundsException();
        }

        // increment state pointer
        currentStatePointer += 1;

        // get the last undone state and redo it
        ReadOnlyAddressBook undoneBook = addressBookStateList.get(currentStatePointer);
        resetData(undoneBook);
    }

    public boolean hasUndo() {
        return currentStatePointer - 1 >= 0;
    }

    public boolean hasRedo() {
        return currentStatePointer + 1 < addressBookStateList.size();
    }

    private void removeAheadCurrent() {
        int curSize = addressBookStateList.size();

        // remove AddressBooks ahead of the current book until none left
        for (int i = currentStatePointer + 1; i < curSize; i++) {
            addressBookStateList.remove(currentStatePointer + 1);
        }

        assert currentStatePointer == addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {

        // check if both objects are the same
        if (other == this) {
            return true;
        }

        // check typing first to convert
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherAddressBook = (VersionedAddressBook) other;

        // check if every other field is the same
        return super.equals(otherAddressBook)
                && currentStatePointer == otherAddressBook.currentStatePointer
                && addressBookStateList.equals(otherAddressBook.addressBookStateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentStatePointer, addressBookStateList);
    }

}
