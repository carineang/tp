package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




/**
 * Don't need many test cases as many of the things to test is already in ModelManagerTest
 */
public class VersionedAddressBookTest {

    private VersionedAddressBook versionedAddressBook;
    private VersionedAddressBook emptyVersionedAddressBook;

    @BeforeEach
    public void setUp() {
        versionedAddressBook = new VersionedAddressBook(getTypicalAddressBook());
        emptyVersionedAddressBook = new VersionedAddressBook();
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), emptyVersionedAddressBook.getPersonList());

        assertEquals(getTypicalAddressBook().getPersonList(), versionedAddressBook.getPersonList());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // same values -> returns true
        VersionedAddressBook versionedAddressBookCopy = new VersionedAddressBook(getTypicalAddressBook());
        assertTrue(versionedAddressBook.equals(versionedAddressBookCopy));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different state lists -> returns false
        assertFalse(versionedAddressBook.equals(emptyVersionedAddressBook));

        // different state pointer -> returns false
        VersionedAddressBook emptyversionedAddressBookCopy = new VersionedAddressBook();
        emptyversionedAddressBookCopy.addPerson(ALICE);
        emptyversionedAddressBookCopy.commit();
        emptyversionedAddressBookCopy.undo();

        emptyVersionedAddressBook.addPerson(ALICE);
        emptyVersionedAddressBook.commit();

        assertFalse(emptyVersionedAddressBook.equals(emptyversionedAddressBookCopy));

    }


}
