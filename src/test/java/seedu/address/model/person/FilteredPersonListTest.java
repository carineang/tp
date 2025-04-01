package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.testutil.TypicalPersons;

public class FilteredPersonListTest {

    private ObservableList<Person> personList;
    private FilteredPersonList filteredPersonList;

    @BeforeEach
    public void setUp() {
        personList = FXCollections.observableArrayList(TypicalPersons.ALICE, TypicalPersons.DANIEL,
                TypicalPersons.BENSON, TypicalPersons.CARL);
        filteredPersonList = new FilteredPersonList(personList);
    }

    @Test
    public void sortByName_sortedList_sortedByName() {
        filteredPersonList.sortByFilteredList("n/");
        assertEquals("Alice Pauline", filteredPersonList.getSortedList().get(0).getName().toString());
        assertEquals("Benson Meier", filteredPersonList.getSortedList().get(1).getName().toString());
        assertEquals("Carl Kurz", filteredPersonList.getSortedList().get(2).getName().toString());
        assertEquals("Daniel Meier", filteredPersonList.getSortedList().get(3).getName().toString());
    }

    @Test
    public void sortByPhoneNumber_sortedList_sortedByPhoneNumber() {
        filteredPersonList.sortByFilteredList("p/");
        assertEquals("87652533", filteredPersonList.getSortedList().get(0).getPhone().toString());
        assertEquals("94351253", filteredPersonList.getSortedList().get(1).getPhone().toString());
        assertEquals("95352563", filteredPersonList.getSortedList().get(2).getPhone().toString());
        assertEquals("98765432", filteredPersonList.getSortedList().get(3).getPhone().toString());
    }

    @Test
    public void sortByEmailAddress_sortedList_sortedByEmailAddress() {
        filteredPersonList.sortByFilteredList("e/");
        assertEquals("alice@example.com", filteredPersonList.getSortedList().get(0).getEmail().toString());
        assertEquals("cornelia@example.com", filteredPersonList.getSortedList().get(1).getEmail().toString());
        assertEquals("heinz@example.com", filteredPersonList.getSortedList().get(2).getEmail().toString());
        assertEquals("johnd@example.com", filteredPersonList.getSortedList().get(3).getEmail().toString());
    }

    @Test
    public void sortByAddress_sortedList_sortedByAddress() {
        filteredPersonList.sortByFilteredList("a/");
        assertEquals("10th street", filteredPersonList.getSortedList().get(0).getAddress().toString());
        assertEquals("123, Jurong West Ave 6, #08-111", filteredPersonList.getSortedList().get(1).getAddress()
                .toString());
        assertEquals("311, Clementi Ave 2, #02-25", filteredPersonList.getSortedList().get(2).getAddress()
                .toString());
        assertEquals("wall street", filteredPersonList.getSortedList().get(3).getAddress().toString());
    }

    @Test
    public void sortByTags_sortedList_sortedByTags() {
        filteredPersonList.sortByFilteredList("t/");
        assertEquals("Alice Pauline", filteredPersonList.getSortedList().get(0).getName().toString());
        assertEquals("Daniel Meier", filteredPersonList.getSortedList().get(1).getName().toString());
        assertEquals("Benson Meier", filteredPersonList.getSortedList().get(2).getName().toString());
        assertEquals("Carl Kurz", filteredPersonList.getSortedList().get(3).getName().toString());
    }

    @Test
    public void sortByTagsThenName_sortedList_sortedByTagsThenName() {
        filteredPersonList.sortByFilteredList("t/", "n/");
        assertEquals("Alice Pauline", filteredPersonList.getSortedList().get(0).getName().toString());
        assertEquals("Daniel Meier", filteredPersonList.getSortedList().get(1).getName().toString());
        assertEquals("Benson Meier", filteredPersonList.getSortedList().get(2).getName().toString());
        assertEquals("Carl Kurz", filteredPersonList.getSortedList().get(3).getName().toString());
    }

    @Test
    public void sortByTagsThenPhone_sortedList_sortedByTagsThenPhone() {
        filteredPersonList.sortByFilteredList("t/", "p/");
        assertEquals("87652533", filteredPersonList.getSortedList().get(0).getPhone().toString());
        assertEquals("94351253", filteredPersonList.getSortedList().get(1).getPhone().toString());
        assertEquals("98765432", filteredPersonList.getSortedList().get(2).getPhone().toString());
        assertEquals("95352563", filteredPersonList.getSortedList().get(3).getPhone().toString());
    }

    @Test
    public void sortByTagsThenEmail_sortedList_sortedByTagsThenEmail() {
        filteredPersonList.sortByFilteredList("t/", "e/");
        assertEquals("alice@example.com", filteredPersonList.getSortedList().get(0).getEmail().toString());
        assertEquals("cornelia@example.com", filteredPersonList.getSortedList().get(1).getEmail().toString());
        assertEquals("johnd@example.com", filteredPersonList.getSortedList().get(2).getEmail().toString());
        assertEquals("heinz@example.com", filteredPersonList.getSortedList().get(3).getEmail().toString());
    }

    @Test
    public void sortByTagsThenAddress_sortedList_sortedByTagsThenAddress() {
        filteredPersonList.sortByFilteredList("t/", "a/");
        assertEquals("10th street", filteredPersonList.getSortedList().get(0).getAddress().toString());
        assertEquals("123, Jurong West Ave 6, #08-111", filteredPersonList.getSortedList().get(1).getAddress()
                .toString());
        assertEquals("311, Clementi Ave 2, #02-25", filteredPersonList.getSortedList().get(2).getAddress()
                .toString());
        assertEquals("wall street", filteredPersonList.getSortedList().get(3).getAddress().toString());
    }

    @Test
    public void sortBy_emptyList_sortedListEmpty() {
        ObservableList<Person> emptyList = FXCollections.observableArrayList();
        filteredPersonList = new FilteredPersonList(emptyList);
        filteredPersonList.sortByFilteredList("n/");
        assertTrue(filteredPersonList.getSortedList().isEmpty());
    }

    @Test
    public void sortBy_invalidCombinationOfPrefixes_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> filteredPersonList.sortByFilteredList("x/"));
        assertThrows(IllegalArgumentException.class, () -> filteredPersonList.sortByFilteredList("t/", "x/"));
        assertThrows(IllegalArgumentException.class, () -> filteredPersonList.sortByFilteredList("p/", "e/"));
        assertThrows(IllegalArgumentException.class, () -> filteredPersonList.sortByFilteredList("a/", "n/"));
        assertThrows(IllegalArgumentException.class, () -> filteredPersonList.sortByFilteredList("t/", "a/",
                "p/"));
    }

    @Test
    public void sortBy_invalidPrefix_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> filteredPersonList.sortByFilteredList("z/"));
    }
}
