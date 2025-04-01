package seedu.address.model.person;

import java.math.BigInteger;
import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Represents a filtered and sorted list of Persons, provides methods to filter and sort a list of persons
 * based on various attributes.
 */
public class FilteredPersonList {

    private FilteredList<Person> filteredList;

    private SortedList<Person> sortedList;

    /**
     * Initializes a FilteredPersonList with the given source list of persons.
     *
     * @param source The source list of persons to be filtered and sorted.
     */
    public FilteredPersonList(ObservableList<Person> source) {
        this.filteredList = new FilteredList<>(source);
        this.sortedList = new SortedList<>(filteredList);
    }

    /**
     * Get the sorted list.
     *
     * @return the sorted list.
     */
    public SortedList<Person> getSortedList() {
        return sortedList;
    }

    /**
     * Sorts the filtered list based on the given prefix.
     *
     * @param prefix The prefix used to determine the sorting attribute.
     */
    public void sortByFilteredList(String prefix) {
        switch (prefix) {
        case "n/":
            sortByName();
            break;
        case "p/":
            sortByPhoneNumber();
            break;
        case "e/":
            sortByEmailAddress();
            break;
        case "a/":
            sortByAddress();
            break;
        case "t/":
            sortByTags();
            break;
        default:
            break;
        }
    }
    /**
     * Sets the comparator to sort the list by name.
     */
    public void sortByName() {
        sortedList.setComparator(Comparator.comparing(p -> p.getName().toString()));
    }

    /**
     * Sets the comparator to sort the list by phone number.
     */
    public void sortByPhoneNumber() {
        sortedList.setComparator(Comparator.comparing(p -> new BigInteger(p.getPhone().toString())));
    }

    /**
     * Sets the comparator to sort the list by email address.
     */
    public void sortByEmailAddress() {
        sortedList.setComparator(Comparator.comparing(p -> p.getEmail().toString()));
    }

    /**
     * Sets the comparator to sort the list by address.
     */
    public void sortByAddress() {
        sortedList.setComparator(Comparator.comparing(p -> p.getAddress().toString()));
    }

    /**
     * Sets the comparator to sort the list by tags.
     */
    public void sortByTags() {
        sortedList.setComparator(Comparator.comparing(p -> p.getTags().toString()));
    }
}
