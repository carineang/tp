package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person} matches any of the specified keywords in the given fields.
 * The search can be performed on the person's name, phone number, email, address, or tags.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final boolean searchName;
    private final boolean searchPhone;
    private final boolean searchEmail;
    private final boolean searchAddress;
    private final boolean searchTags;

    public PersonContainsKeywordsPredicate(List<String> keywords, boolean searchName, boolean searchPhone,
                                           boolean searchEmail, boolean searchAddress, boolean searchTags) {
        this.keywords = keywords;
        this.searchName = searchName;
        this.searchPhone = searchPhone;
        this.searchEmail = searchEmail;
        this.searchAddress = searchAddress;
        this.searchTags = searchTags;
    }

    @Override
    public boolean test(Person person) {
        return (searchName && keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getName().fullName, keyword)))
                || (searchPhone && keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getPhone().value, keyword)))
                || (searchEmail && keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getEmail().value, keyword)))
                || (searchAddress && keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getAddress().value, keyword)))
                || (searchTags && person.getTags().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(tag.tagName, keyword))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords)
                && searchName == otherPredicate.searchName
                && searchPhone == otherPredicate.searchPhone
                && searchEmail == otherPredicate.searchEmail
                && searchAddress == otherPredicate.searchAddress
                && searchTags == otherPredicate.searchTags;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords)
                .add("searchName", searchName)
                .add("searchPhone", searchPhone)
                .add("searchEmail", searchEmail)
                .add("searchAddress", searchAddress)
                .add("searchTags", searchTags)
                .toString();
    }
}
