package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.model.person.Person;


/**
 * State of the model that contains the state of the
 * address book and the predicate of the filtered list in the model
 */
public class ModelState {
    private final ReadOnlyAddressBook addressBookState;
    private final Predicate<Person> predicateState;

    /**
     * Creates a ModelState using an address book and a predicate
     */
    public ModelState(ReadOnlyAddressBook addressBook, Predicate<Person> predicate) {
        requireAllNonNull(addressBook, predicate);

        addressBookState = addressBook;
        predicateState = predicate;
    }

    public ReadOnlyAddressBook getAddressBookState() {
        return addressBookState;
    }

    public Predicate<Person> getPredicate() {
        return predicateState;
    }

    @Override
    public boolean equals(Object other) {

        // check if both objects are the same
        if (other == this) {
            return true;
        }

        // check typing first to convert
        if (!(other instanceof ModelState)) {
            return false;
        }

        ModelState otherModelState = (ModelState) other;

        // check if every other field is the same
        return addressBookState.equals(otherModelState.addressBookState)
                && predicateState.equals(otherModelState.predicateState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookState, predicateState);
    }
}
