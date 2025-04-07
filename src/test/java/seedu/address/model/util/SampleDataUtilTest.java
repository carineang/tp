package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_notNullOrEmpty_success() {
        // Test the getSamplePersons method
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertTrue(samplePersons.length > 0);
    }

    @Test
    public void getSampleAddressBook_consistentDataAndNotNull_success() {
        // Prepare the expected addressbook using getSamplePersons
        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.setPersons(Arrays.asList(SampleDataUtil.getSamplePersons()));

        assertNotNull(SampleDataUtil.getSampleAddressBook());
        // get the actual book
        ReadOnlyAddressBook actualBook = SampleDataUtil.getSampleAddressBook();

        assertNotNull(actualBook);

        assertEquals(expectedAddressBook, actualBook);
    }

    @Test
    public void getTagSet() {
        // Test the getTagSet method
        String[] tags = {"tag1", "tag2", "tag3"};
        Set<Tag> tagSet = SampleDataUtil.getTagSet(tags);

        // Check that the tag set is not null and has the expected length
        assertNotNull(tagSet);
        assertEquals(tags.length, tagSet.size());

        Set<String> tagNames = tagSet.stream().map(tag -> tag.tagName).collect(Collectors.toSet());
        // Check that the tag set contains the expected tags by name
        assertEquals(Set.of(tags), tagNames);

        // Valid empty tag -> empty set
        Set<Tag> actualEmptyTag = SampleDataUtil.getTagSet();
        assertEquals(actualEmptyTag, Set.of());

        // Invalid null input will cause a varargs warning during compilation. This test case can be skipped
    }

}
