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
    private final boolean isSearchByName;
    private final boolean isSearchByPhone;
    private final boolean isSearchByEmail;
    private final boolean isSearchByAddress;
    private final boolean isSearchByTags;

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate} with the specified search parameters.
     *
     * @param keywords A list of keywords to search for.
     * @param isSearchByName {@code true} if the search should match names, {@code false} otherwise.
     * @param isSearchByPhone {@code true} if the search should match phone numbers, {@code false} otherwise.
     * @param isSearchByEmail {@code true} if the search should match emails, {@code false} otherwise.
     * @param isSearchByAddress {@code true} if the search should match addresses, {@code false} otherwise.
     * @param isSearchByTags {@code true} if the search should match tags, {@code false} otherwise.
     */
    public PersonContainsKeywordsPredicate(List<String> keywords, boolean isSearchByName, boolean isSearchByPhone,
                                           boolean isSearchByEmail, boolean isSearchByAddress, boolean isSearchByTags) {
        this.keywords = keywords;
        this.isSearchByName = isSearchByName;
        this.isSearchByPhone = isSearchByPhone;
        this.isSearchByEmail = isSearchByEmail;
        this.isSearchByAddress = isSearchByAddress;
        this.isSearchByTags = isSearchByTags;
    }

    @Override
    public boolean test(Person person) {
        return (isSearchByName && matches(person.getName().fullName))
                || (isSearchByPhone && keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getPhone().value, keyword)))
                || (isSearchByEmail && matches(person.getEmail().value))
                || (isSearchByAddress && matches(person.getAddress().value))
                || (isSearchByTags && person.getTags().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(tag.tagName, keyword))));
    }

    /**
     * Checks if the given field value matches any of the keywords using either exact phrase matching
     * (ignoring case) or approximate matching using Levenshtein distance.
     *
     * @param fieldValue The string value of the field to be checked.
     * @return {@code true} if any keyword matches the field value either exactly (ignoring case)
     *         or approximately within a Levenshtein distance threshold of 2, otherwise {@code false}.
     */
    private boolean matches(String fieldValue) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsPhraseIgnoreCase(fieldValue, keyword) || StringUtil.isSimilar(fieldValue, keyword, 2)
        );
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
                && isSearchByName == otherPredicate.isSearchByName
                && isSearchByPhone == otherPredicate.isSearchByPhone
                && isSearchByEmail == otherPredicate.isSearchByEmail
                && isSearchByAddress == otherPredicate.isSearchByAddress
                && isSearchByTags == otherPredicate.isSearchByTags;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords)
                .add("searchName", isSearchByName)
                .add("searchPhone", isSearchByPhone)
                .add("searchEmail", isSearchByEmail)
                .add("searchAddress", isSearchByAddress)
                .add("searchTags", isSearchByTags)
                .toString();
    }
}
