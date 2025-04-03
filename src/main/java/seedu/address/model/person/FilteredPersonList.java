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
     * Sorts the list of persons based on the specified prefix such as name, phone number, email address, address, tags.
     *
     * @param prefixes The prefix indicates the sorting criteria.
     */
    public void sortByFilteredList(String... prefixes) {
        if (prefixes.length == 1) {
            sortBySinglePrefix(prefixes[0]);
        } else if (prefixes.length == 2 && prefixes[0].equals("t/")) {
            sortByTagsCombination(prefixes[1]);
        } else if (prefixes.length > 2) {
            throw new IllegalArgumentException("Invalid number of prefixes.");
        } else {
            throw new IllegalArgumentException("Invalid combination of prefixes.");
        }
    }

    /**
     * Sorts the list based on a single prefix.
     *
     * @param prefix The prefix indicating the sorting criteria.
     * @throws IllegalArgumentException If the provided prefix is invalid or unrecognized.
     */
    private void sortBySinglePrefix(String prefix) {
        switch (prefix) {
        case "t/":
            sortByTags();
            break;
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
        default:
            throw new IllegalArgumentException("Invalid sort prefix.");
        }
    }

    /**
     * Sorts the list by a specified attribute within the tag grouping. The first prefix is "t/" to indicate
     * sorting by tags, and the second prefix indicates the attribute to sort within each tag group.
     *
     * @param secondPrefix The second prefix indicating the sorting criteria within the tags.
     * @throws IllegalArgumentException If the combination of prefixes is invalid or unrecognized.
     */
    private void sortByTagsCombination(String secondPrefix) {
        switch (secondPrefix) {
        case "n/":
            sortByNameWithinTagsFiltered();
            break;
        case "p/":
            sortByPhoneNumberWithinTagsFiltered();
            break;
        case "e/":
            sortByEmailAddressWithinTagsFiltered();
            break;
        case "a/":
            sortByAddressWithinTagsFiltered();
            break;
        default:
            throw new IllegalArgumentException("Invalid combination of prefixes.");
        }
    }

    /**
     * Sort the list by name.
     */
    public void sortByName() {
        sortedList.setComparator(Comparator.comparing(p -> p.getName().toString()));
    }

    /**
     * Sort the list by phone number.
     */
    public void sortByPhoneNumber() {
        sortedList.setComparator(Comparator.comparing(p -> new BigInteger(p.getPhone().toString())));
    }

    /**
     * Sort the list by email address.
     */
    public void sortByEmailAddress() {
        sortedList.setComparator(Comparator.comparing(p -> p.getEmail().toString()));
    }

    /**
     * Sort the list by address.
     */
    public void sortByAddress() {
        sortedList.setComparator(Comparator.comparing(p -> p.getAddress().toString()));
    }

    /**
     * Sort the list by tags.
     */
    public void sortByTags() {
        sortedList.setComparator(Comparator.comparing(p -> p.getTags().toString()));
    }

    /**
     * Sort the list first by tags, then by name within each tag group.
     */
    private void sortByNameWithinTagsFiltered() {
        sortedList.setComparator(Comparator.comparing((Person p) -> p.getTags().toString())
                .thenComparing(p -> p.getName().toString()));
    }

    /**
     * Sort the list first by tags, then by phone number within each tag group.
     */
    private void sortByPhoneNumberWithinTagsFiltered() {
        sortedList.setComparator(Comparator.comparing((Person p) -> p.getTags().toString())
                .thenComparing(p -> new BigInteger(p.getPhone().toString())));
    }

    /**
     * Sort the list first by tags, then by email address within each tag group.
     */
    private void sortByEmailAddressWithinTagsFiltered() {
        sortedList.setComparator(Comparator.comparing((Person p) -> p.getTags().toString())
                .thenComparing(p -> p.getEmail().toString()));
    }

    /**
     * Sort the list first by tags, then by address within each tag group.
     */
    private void sortByAddressWithinTagsFiltered() {
        sortedList.setComparator(Comparator.comparing((Person p) -> p.getTags().toString())
                .thenComparing(p -> p.getAddress().toString()));
    }
}
