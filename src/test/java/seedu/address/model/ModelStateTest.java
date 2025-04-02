package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

public class ModelStateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // predicate null
        assertThrows(NullPointerException.class, () -> new ModelState(new AddressBook(), null));
        // address book null
        assertThrows(NullPointerException.class, () -> new ModelState(null, PREDICATE_SHOW_ALL_PERSONS));
    }

    @Test
    public void equals() {
        ModelState modelState1 = new ModelState(new AddressBook(), PREDICATE_SHOW_ALL_PERSONS);

        // same values -> returns true
        ModelState modelState2 = new ModelState(new AddressBook(), PREDICATE_SHOW_ALL_PERSONS);
        assertTrue(modelState1.equals(modelState2));

        // same object -> returns true
        assertTrue(modelState1.equals(modelState1));

        // null -> returns false
        assertFalse(modelState1.equals(null));

        // different types -> returns false
        assertFalse(modelState1.equals(42));

        // different address book -> returns false
        ModelState modelState3 = new ModelState(getTypicalAddressBook(), PREDICATE_SHOW_ALL_PERSONS);
        assertFalse(modelState1.equals(modelState3));

        // different predicate -> returns false
        ModelState modelState4 = new ModelState(new AddressBook(), p -> false);
        assertFalse(modelState1.equals(modelState4));
    }
}
